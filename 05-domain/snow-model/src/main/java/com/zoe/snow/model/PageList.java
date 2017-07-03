/**
 *
 */
package com.zoe.snow.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.json.DJson;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component("snow.model.page-list")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PageList<T extends Model> {

    @Value("${commons.dao.sql.page-size.max:100}")
    protected int maxPageSize;
    protected List<T> list = new ArrayList<>();


    protected int count;
    protected int size;
    protected int page;
    protected int number;
   /* protected T model;*/

    /**
     * 设置分页信息。
     *
     * @param count 记录总数。
     * @param size  每页显示记录数。
     * @param page  当前显示页码数。
     */
    public void setPage(int count, int size, int page) {
        this.count = Math.max(0, count);
        this.size = Math.max(1, size);
        this.number = this.count / this.size + (this.count % this.size == 0 ? 0 : 1);
        this.number = Math.max(1, this.number);
        //this.number = (int) Math.ceil(this.count / this.size);
        this.page = page;
    }

    /**
     * 获取记录总数。
     *
     * @return 记录总数。
     */
    public int getCount() {
        return count;
    }

    /**
     * 获取每页最大显示记录数。
     *
     * @return 每页最大显示记录数。
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取当前显示页数。
     *
     * @return 当前显示页数。
     */
    public int getPage() {
        return page;
    }

    public int getNumber() {
        return number;
    }

    /**
     * 获取数据集。
     *
     * @return 数据集。
     *//*
    public T getData() {
        return model;
    }

    *//**
     * 设置数据集。
     *
     * @param model
     *            数据集。
     *//*
    public void setData(T model) {
        this.model = model;
    }*/

    /**
     * 转化为JSON格式的数据。
     *
     * @return JSON格式的数据。
     * public JSONObject toJson() {
     * JSONObject object = new JSONObject();
     * object.put("count", count);
     * object.put("size", size);
     * object.put("page", page);
     * JSONArray array = new JSONArray();
     *//*
         * for (T model : list) array.add(modelHelper.toJson(model));
         *//*
        array.add(JSONArray.fromObject(this.model));
        object.put("list", array);

        return object;
    }*/
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    /*@Override
    public T getData() {
        return null;
    }*/

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("total", count);
        object.put("size", size);
        object.put("page", page);
        object.put("number", this.number);
        JSONArray array = new JSONArray();
        for (T model : list)
            array.add(DJson.toJson(model));
        object.put("rows", array);

        return object;
    }

    public PageList toList(Class<?> modelClass) {
        //if(modelClass== this.getList())
        PageList pageList = BeanFactory.getBean(PageList.class);
        List list = new ArrayList<>();
        if (this.getList().size() == 0)
            return pageList;

        if (modelClass == this.getList().get(0).getClass()) {
            return this;
        }

        try {
            this.getList().forEach(c -> {
                // TODO: 2016/11/9 对相对应的字段进行赋值
            });

            if (list.size() == this.getList().size()) {
                pageList.setList(list);
            } else {
                //modelTables 一定不为会空
                ModelTables modelTables = BeanFactory.getBean(ModelTables.class);
                ModelTable modelTable = modelTables.get(this.getList().get(0).getClass());
                if (modelTable != null) {
                    if (modelTable.getMethods() != null) {
                        modelTable.getMethods().forEach(c -> {
                            Method method = null;
                            if (c.getReturnType().getName().equals(modelClass)) {
                                method = c;
                                this.getList().forEach(l -> {
                                    try {
                                        list.add(l.getClass().getMethod(c.getName()).invoke(l));
                                    } catch (Exception e) {
                                    }
                                });
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
        }
        return pageList;
    }
}
