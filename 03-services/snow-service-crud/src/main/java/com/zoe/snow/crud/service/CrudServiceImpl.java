package com.zoe.snow.crud.service;

import java.lang.reflect.Method;
import java.util.*;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.CrudConfiguration;
import com.zoe.snow.context.aop.annotation.LogTo;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.CrudServiceHelper;
import com.zoe.snow.crud.OrmManage;
import com.zoe.snow.crud.QueryManager;
import com.zoe.snow.crud.enums.DeleteType;
import com.zoe.snow.crud.service.proxy.*;
import com.zoe.snow.dao.orm.OrmContext;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.model.support.BaseModelSupport;
import com.zoe.snow.model.support.user.BaseModelHelper;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.support.BaseModel;
import com.zoe.snow.validator.exception.ExistsException;
import com.zoe.snow.validator.exception.ExistsInRecycleBinException;
import com.zoe.snow.validator.exception.ListExistsException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;

/**
 * 封装了一些通用的增删改查接口 同时对通用查询增加底层字段的过滤
 * 1、验证
 * 2、授权
 *
 * @author Dai Wenqing
 * @date 2015/11/6
 */
@Component("snow.crud.service")
public class CrudServiceImpl implements CrudService {
    //protected CrudDao crudDao;
    @Autowired
    protected OrmManage ormManage;
    @Autowired
    protected QueryManager queryManager;
    @Autowired
    protected QueryService queryService;
    @Autowired
    protected ExecuteService executeService;
    @Autowired(required = false)
    protected BaseModelHelper baseModelHelper;
    @Autowired
    protected CrudConfiguration crudConfiguration;
    @Autowired(required = false)
    protected UserHelper userHelper;
    @Autowired
    protected ModelTables modelTables;

    @Override
    public QueryProxy query(String... name) {
        QueryProxy queryProxy = BeanFactory.getBean(QueryProxy.class);
        if (name.length > 0)
            queryProxy.setQueryName(name[0]);
        return queryProxy;
    }

    @Override
    public ExecuteProxy execute() {
        return BeanFactory.getBean(ExecuteProxy.class);
    }

    @Override
    public SqlProxy sql(String sql) {
        return new SqlProxy(sql);
    }

    @Override
    public SqlProxy sql(String tableName, String sql) {
        return new SqlProxy(tableName, sql);
    }

    @Override
    public SqlProxy sql(Class<? extends Model> classZ, String sql) {
        return new SqlProxy(classZ, sql);
    }

    @Override
    public ElasticProxy elastic(String... index) {
        return BeanFactory.getBean(ElasticProxy.class);
    }

    @Override
    public HBaseProxy hbase() {
        return BeanFactory.getBean(HBaseProxy.class);
    }

    @Override
    public MapProxy map(Class<? extends Model> classZ) {
        MapProxy mapProxy = BeanFactory.getBean(MapProxy.class);
        mapProxy.from(classZ);
        return mapProxy;
    }

    private void setArgs(Query query, Object... args) {
        List<Object> argList = Converter.toList(args);
        if (!Validator.isEmpty(args)) {
            int ndx = 0;
            OrmContext ormContext = OrmContext.class.cast(query);
            for (Object o : args) {
                ormContext.getArgs().add(ndx, o);
                ndx++;
            }
        }
    }

    //@LogTo("crud")

    @LogTo("crud")
    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends Model> T findById(Class<T> classZ, String id) {
        CrudServiceHelper.doMustNotNull(classZ);

        if (id == null)
            return null;
        return ormManage.getOrm().findById(classZ, id);
    }

    @LogTo("crud")
    @Override
    public <T extends Model> boolean deleteById(Class<T> classZ, String id) {
        //return de;
        Query query = queryManager.getQuery();
        query.from(classZ);
        query.where("id", id);
        return executeService.deleteOrRecycle(query, DeleteType.Delete);
    }

    @LogTo("crud")
    @Override
    public <T extends Model> Boolean save(T model) {
        if (Validator.isEmpty(model))
            return false;
        if (baseModelHelper != null)
            baseModelHelper.initBaseModel((BaseModel) model);

        hasExist(model);

        return ormManage.getOrm().save(model, CrudServiceHelper.getDatasource(model.getClass()));
    }

    /**
     * 重复判断
     *
     * @param model
     * @param <T>
     */
    private <T extends Model> void hasExist(T model) {
        List<String> uniqueList = modelTables.get(model.getClass()).getUniqueColumnList();
        List<String> existList = new ArrayList<>();
        //只是一个计算器而已
        List<Object> args = new ArrayList<>();
        // Map<String, Criterion> map = new LinkedHashMap<>();
        QueryProxy queryProxy = query().from(model.getClass());
        uniqueList.forEach(c -> {
            Object object = null;
            try {
                object = queryProxy.where(c, model.getClass().getMethod("get" + Converter.toFirstUpperCase(c)).invoke(model), Operator.Or);
            } catch (Exception ex) {
                Logger.error(ex, ex.getMessage());
            }
            //不发生异常 object 一定不为空
            if (!Validator.isEmpty(object)) {
                args.add(object);
            }

        });
        PageList<T> pageList = BeanFactory.getBean(PageList.class);
        if (args.size() > 0) {
            pageList = queryProxy.list();
        }
        if (pageList.getList().size() > 0) {
            // 如果是修改，当前修改的实体的ID不能与库里有ID相等
            if ((!Validator.isEmpty(model.getId()) && !pageList.getList().get(0).getId().equals(model.getId()))
                    || Validator.isEmpty(model.getId())) {
                BaseModelSupport baseModelSupport = BaseModelSupport.class.cast(pageList.getList().get(0));
                //model.setId(baseModelSupport.getId());
                int valid = baseModelSupport.getValidFlag();
                if (valid == 1)
                    throw new ExistsException(existList.toArray()).setModelName(model.getClass().getSimpleName());

                else if (valid == 0)
                    throw new ExistsInRecycleBinException(existList.toArray()).setModelName(model.getClass().getSimpleName());
            }
        }
    }

    /**
     * 批量保存
     * <p>
     * 资源定位地址
     *
     * @param modelList 对象列表
     * @return
     */
    @Override
    public <T extends Model> Boolean saveList(List<T> modelList) {
        if (modelList == null)
            return false;

        boolean result = true;
        JSONObject jsonObject = new JSONObject();
        if (modelList.size() > 0)
            jsonObject.put("model", modelList.get(0).getClass().getSimpleName());
        else
            return true;
        int ndx = 0;
        int count = 0;
        for (Model model : modelList) {
            try {
                result = result && save(model);
                ndx++;
            } catch (ExistsException | ExistsInRecycleBinException ex) {
                JSONObject jo = new JSONObject();
                jo.put("position", ndx);
                jo.put("code", ex.getMessage());
                jsonObject.put("exist", jo);
                count++;
            }
        }
        if (count > 0)
            throw new ListExistsException(jsonObject);
        return result;
    }

    @Override
    public <T extends Model> Boolean update(T model) {
        return update(model, false);
    }

    @LogTo("crud")
    @Override
    public <T extends Model> Boolean update(T model, boolean toValidator) {
        if (Validator.isEmpty(model))
            return false;
        if (toValidator)
            hasExist(model);
        Query query = queryManager.getQuery();
        for (Method method : model.getClass().getMethods()) {
            if (BeanFactory.getBean(method.getReturnType()) instanceof Model)
                continue;
            if (!method.getName().startsWith("get"))
                continue;
            OrmContext ormContext = OrmContext.class.cast(query);
            query.from(model.getClass());
            CrudServiceHelper.initQuery(query);
            try {
                Object value = method.invoke(model);
                if (value == null)
                    continue;
                else if (method.getAnnotation(Transient.class) != null)
                    continue;
                else if (value instanceof Integer) {
                    if (value.toString().equals(Integer.MIN_VALUE + ""))
                        continue;
                } else if (value instanceof Double) {
                    if (value.toString().equals(Double.MIN_VALUE + ""))
                        continue;
                } else if (value instanceof Float) {
                    if (value.toString().equals(Float.MIN_VALUE + ""))
                        continue;
                } else if (value instanceof Long) {
                    if (value.toString().equals(Long.MIN_VALUE + ""))
                        continue;
                }
                String name = Converter.toFirstLowerCase(method.getName().substring("get".length()));
                if (name.toLowerCase().equals("class"))
                    continue;
                if (name.toLowerCase().equals("id"))
                    query.where(name, method.invoke(model));
                else
                    query.set(name, method.invoke(model));
            } catch (Exception e) {
                Logger.error(e, "更新操作，set 获取取值时出错");
            }
        }
        return ormManage.getOrm().update(query);
    }


}
