package com.zoe.snow.crud;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.fun.Callable;
import com.zoe.snow.validator.exception.*;
import com.zoe.snow.log.Logger;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.ModelHelper;
import com.zoe.snow.model.PageList;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.zoe.snow.message.MessageTool;
import org.hibernate.SessionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回的结果集
 *
 * @author Dai Wenqing
 * @date 2016/3/4
 */
@Service("snow.crud.result")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Result<T> implements Serializable {
    private T data;
    // @JsonProperty
    private String message;
    // @JsonProperty
    private String code;
    private boolean success = true;

    public Result(T data, Message message) {
        this.data = data;
        this.message = message.getType();
    }

    public Result() {
    }

    /**
     * 结果响应：
     * 1、对可执行的方法进行封装， 最后返回统一的格式；
     * 2、对异常进行统一处理；
     * 3、对重复数据进行判断，但需要在实体字段上增加注释@Unique
     * 4、对返回的结果及消息进行封装格式如果下：
     * {code:'结果代码',message:'结果消息',data'结果数据'}
     *
     * @param callable 可执行方法
     * @param args
     * @param <T>
     * @return
     */
    public static <T> Result reply(Callable<T> callable, String... args) {
        T data = null;
        Result result = BeanFactory.getBean(Result.class);
        try {
            data = callable.call();
        } catch (ExistsException e) {
            // 数据已经存在
            return result.setResult(null, false, Message.Exist, e.getMessage());
        } catch (ExistsInRecycleBinException e) {
            return result.setResult(null, false, Message.ExistsRecycle, e.getMessage());
        } catch (ListExistsException e) {
            return result.setResult(null, false, Message.Exist, e.getMessage());
        } catch (NotEmptyException | OverLengthException
                | IllegalStateException | SessionException e) {
            return result.setResult(null, false, Message.Error, e.getMessage());
        } catch (Exception e) {
            Logger.error(e, "执行服务时发生了异常!");
            return result.setResult(null, false, Message.ServiceError, args);
        }
        if (Validator.isEmpty(data)) {
            result.setResult(data, true, Message.NoExist, args);
        } else {
            if (data instanceof Model) {
                result.setResult(data, true, Message.Success, args);
            } else if (data instanceof PageList) {
                if (PageList.class.cast(data).getList().size() > 0)
                    result.setResult(data, true, Message.Success, args);
                else
                    result.setResult(data, true, Message.SelectNoAnyRecord, args);
            } else if (data instanceof JSONArray) {
                if (JSONArray.class.cast(data).size() > 0)
                    result.setResult(data, true, Message.Success, args);
                else
                    result.setResult(data, true, Message.SelectNoAnyRecord, args);
            } else if (data instanceof Message) {
                Message message = Message.class.cast(data);
                if (!Validator.isEmpty(message.getArgs()))
                    result.setResult(null, false, message, message.getArgs());
                else
                    result.setResult(null, false, message, args);
            } else {
                if (data.toString().toLowerCase().equals("true") || data.toString().toLowerCase().equals("false")) {
                    boolean r = Boolean.valueOf(data.toString());
                    if (r)
                        result.setResult(true, true, Message.Success, args);
                    else
                        result.setResult(false, false, Message.Error, args);
                } else
                    result.setResult(data, true, Message.Success, args);
            }
        }
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /*
     * public void setData(T data) { this.data = data; }
     */

    public T getData() {
        return data;
    }

    /*
     * public void setMessage(String message) { this.message = message; }
     */

    public String getMessage() {
        return message;
    }

    /*
     * public void setCode(String code) { this.code = code; }
     */

    public String getCode() {
        return code;
    }

                /*try {
                // 数据已经存在收回站，系统尝试自动回收数据
                CrudService crudService = BeanFactory.getBean(CrudService.class);
                crudService.execute().recycle((Class<Model>) data.getClass(), Model.class.cast(data).getId());
            } catch (Exception ex) {
                result.setResult(data, Message.ExistsRecycleFailure, args);
            }*/

    public Object toJson() {
        return toJsonObject();
    }

    private JSONObject toJsonObject() {
        if (data instanceof Model || data == null || data instanceof PageList) {
            //Map<String, Object> map = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            if (data instanceof Model) {
                jsonObject.put("data", ModelHelper.toJson((Model) data));
            } else if (data instanceof PageList) {
                PageList pageList = (PageList) data;
                jsonObject.put("data", pageList.toJson());
            }
            jsonObject.put("message", this.getMessage());
            jsonObject.put("code", this.getCode());
            return jsonObject;
        }
        return JSONObject.fromObject(this);
    }

    public String toJsonString() {
        return toJsonObject().toString();
    }

    private Result setResult(T data, String message) {
        this.data = data;
        this.message = message;
        return this;
    }

    public Result setResult(T data, boolean isSuccess, Message code, String... args) {
        MessageTool messageTool = BeanFactory.getBean(MessageTool.class);

        Class<?> classZ = null;
        if (!Validator.isEmpty(data)) {
            classZ = data.getClass();
        }
        this.success = isSuccess;
        String packageName;
        if (classZ == null)
            packageName = this.getClass().getGenericSuperclass().getTypeName();
        else
            packageName = classZ.getTypeName();

        String[] packages = packageName.split("\\.");

        List<String> lastPackageNameList = new ArrayList<>();

        for (int i = 0; i < packages.length; i++) {
            if (i > 1 && i < packages.length - 1)
                lastPackageNameList.add(packages[i]);
        }

        String lastPackageName = String.join(".", lastPackageNameList);
        String objName = messageTool.get(lastPackageName);
        List<String> lastArgs = new ArrayList<>();
        if (!objName.equals(lastPackageName))
            lastArgs.add(objName);
        if (!Validator.isEmpty(args)) {
            if (args.length > 0) {
                for (String reason : args) {
                    lastArgs.add(messageTool.get(reason));
                }
            }
        }
        String msg = "";
        if (!Validator.isEmpty(code))
            msg = messageTool.get(code.getType(), lastArgs.toArray());
        this.data = data;
        String msgCode = code.getType();
        if (msgCode.startsWith("msg"))
            msgCode = msgCode.substring(4);
        if (!Validator.isEmpty(msgCode))
            this.code = msgCode;
        this.message = msg;
        return this;
    }

    /*
     * public void setResult(T data, Message code, String... reasons) {
     * setResult(data, code, null, reasons); }
     */
}
