/**
 *
 */
package com.zoe.snow.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.json.DJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author lpw
 */
@Component("snow.model.page-list")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PageList<T> extends ArrayList<T> {

    /*@Value("${commons.dao.sql.page-size.max:100}")
    protected int maxPageSize;


    protected int count;
    protected int size;
    protected int page;
    protected int number;*/
    //protected List<T> list = new ArrayList<>();

    @JSONField(name = "paging")
    protected Page page = new Page();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setList(List<T> list) {
        this.addAll(list);
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
    }
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }*/

    /*@Override
    public T getData() {
        return null;
    }*/
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("paging", page);
        JSONArray array = new JSONArray();
        for (T model : this)
            array.add(DJson.toJson(model));
        object.put("rows", array);

        return object;
    }

    /*public PageList toList(Class<?> modelClass) {
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
    }*/
}
