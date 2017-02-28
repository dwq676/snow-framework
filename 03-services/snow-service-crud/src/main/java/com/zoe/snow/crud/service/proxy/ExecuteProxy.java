package com.zoe.snow.crud.service.proxy;

import com.zoe.snow.crud.QueryManager;
import com.zoe.snow.crud.enums.DeleteType;
import com.zoe.snow.crud.service.ExecuteService;
import com.zoe.snow.crud.service.QueryService;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * ExecuteProxy
 *
 * @author Dai Wenqing
 * @date 2016/5/4
 */
@Component("snow.crud.service.proxy.execute")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExecuteProxy extends ProxySupport{
    //private CrudService crudService;
    private DeleteType deleteType;
    private ActionType actionType;

    /*public ExecuteProxy(Query query, ExecuteService deleteService) {
        this.query = query;
        this.executeService = deleteService;
        //this.crudService = CrudService.class.cast(executeService);
    }*/

    public ExecuteProxy update(Class<? extends Model> classZ) {
        this.actionType = ActionType.Update;
        getQuery().from(classZ);
        return this;
    }

    public ExecuteProxy set(String column, Object value) {
        getQuery().set(column, value);
        return this;
    }

    public ExecuteProxy delete(Class<? extends Model> classZ) {
        this.deleteType = DeleteType.Delete;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        return this;
    }

    public ExecuteProxy recycle(Class<? extends Model> classZ) {
        this.deleteType = DeleteType.Recycle;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        return this;
    }

    public ExecuteProxy remove(Class<? extends Model> classZ) {
        this.deleteType = DeleteType.Remove;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        return this;
    }

    /**
     * 根据id进行回收数据
     *
     * @param classZ
     * @param id
     * @return
     */
    public boolean delete(Class<? extends Model> classZ, String id) {
        this.deleteType = DeleteType.Delete;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        getQuery().where("id", id);
        return executeService.deleteOrRecycle(getQuery(), DeleteType.Delete);
    }

    public boolean recycle(Class<? extends Model> classZ, String id) {
        this.deleteType = DeleteType.Recycle;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        getQuery().where("id", id);
        return executeService.deleteOrRecycle(getQuery(), DeleteType.Recycle);
    }

    public boolean remove(Class<? extends Model> classZ, String id) {
        this.deleteType = DeleteType.Remove;
        this.actionType = ActionType.Delete;
        getQuery().from(classZ);
        getQuery().where("id", id);
        return executeService.deleteOrRecycle(getQuery(), DeleteType.Remove);
    }

    public ExecuteProxy where(String column, Criterion criterion, Object value, Operator... operator) {
        getQuery().where(column, criterion, value, operator);
        return this;
    }

    public ExecuteProxy where(String column, Object value, Operator... operator) {
        where(column, Criterion.Equals, value, operator);
        return this;
    }

    public ExecuteProxy where(Supplier<String> where) {
        getQuery().where(where);
        return this;
    }

    public ExecuteProxy where(Supplier<String> where, Object value, Operator... operator) {
        getQuery().where(where, value, operator);
        return this;
    }

    public boolean invoke() {
        if (actionType == ActionType.Delete)
            return executeService.deleteOrRecycle(getQuery(), deleteType);
        else if (actionType == ActionType.Update)
            return executeService.update(getQuery());
        return false;
    }

    /*
     * public <T extends Model> PageList<T> list(int page, int size, Object...
     * args) { Sql sqlOrm = BeanFactory.getBean(Sql.class); return
     * sqlOrm.getList(this.classZ, this.sql, size, page, args); }
     */

    public enum ActionType {
        Delete, Update
    }

}
