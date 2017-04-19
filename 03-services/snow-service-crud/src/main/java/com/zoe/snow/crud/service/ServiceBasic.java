package com.zoe.snow.crud.service;

import com.zoe.snow.Global;
import com.zoe.snow.auth.NoNeedVerify;
import com.zoe.snow.context.aop.annotation.LogTo;
import com.zoe.snow.crud.CrudServiceHelper;
import com.zoe.snow.crud.OrmManage;
import com.zoe.snow.crud.enums.DeleteType;
import com.zoe.snow.dao.orm.Query;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.support.BaseModel;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ServiceBasic
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/26
 */
@Component("snow.crud.service.basic")
@NoNeedVerify
public class ServiceBasic implements QueryService, ExecuteService {
    //protected CrudDao crudDao;
    @Autowired
    protected OrmManage ormManage;

    @Override
    public <T extends Model> T one(Query query, boolean excludeDomain) {
        //doMustNotNull(query
        //setArgs(query, args);
        CrudServiceHelper.initQuery(query);
        return ormManage.getOrm().findOne(CrudServiceHelper.setQueryCondition(query, excludeDomain, Global.QueryFlag.Valid));
    }

    //@LogTo("crud")
    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends Model> PageList<T> list(Query query, boolean excludeDomain) {
        //setArgs(query, args);
        CrudServiceHelper.initQuery(query);
        return ormManage.getOrm().query(CrudServiceHelper.setQueryCondition(query, excludeDomain, Global.QueryFlag.Valid));
    }

    //@LogTo("crud")
    @Override
    public <T extends Model> PageList<T> all(Query query, boolean excludeDomain) {
        //setArgs(query, args);
        CrudServiceHelper.initQuery(query);
        return ormManage.getOrm().query(CrudServiceHelper.setQueryCondition(query, excludeDomain, Global.QueryFlag.All));
    }

    @LogTo("crud")
    @Override
    public int count(Query query, boolean excludeDomain) {
        //setArgs(query, args);
        CrudServiceHelper.initQuery(query);
        return ormManage.getOrm().count(CrudServiceHelper.setQueryCondition(query, excludeDomain, Global.QueryFlag.Valid));
    }

    @LogTo("crud")
    @Override
    public JSONObject asJsonObject(Query query, String ormName) {
        if (Validator.isEmpty(ormName) || Validator.isEmpty(query))
            return new JSONObject();
        return ormManage.getOrm(ormName).getAsJsonObject(query);
    }

    @Override
    public JSONArray asJsonArray(Query query, String ormName) {
        if (Validator.isEmpty(ormName) || Validator.isEmpty(query))
            return new JSONArray();
        return ormManage.getOrm(ormName).getAsJsonArray(query);
    }

    @LogTo("crud")
    @Override
    public <T extends Model> boolean deleteOrRecycle(Query query, DeleteType deleteType) {
        if (query == null)
            return false;
        PageList<T> modelList = all(query, false);
        boolean re = true;
        for (Model model : modelList.getList()) {
            re = re && deleteOrRecycle(deleteType, model);
        }
        return re;
    }

    /**
     * 更改状态的删除，为假删除，建立了实体实际BaseModel接口，有一个有效标记字段
     *
     * @param deleteType
     * @param model
     */
    public boolean deleteOrRecycle(DeleteType deleteType, Model model) {
        if (model != null && model instanceof BaseModel) {
            switch (deleteType) {
                case Recycle:
                    // criterionMap.put("validFlag", Criterion.Equals);
                    ((BaseModel) model).setValidFlag(DeleteType.Recycle.getType());
                    // crudDao.updateById(metaData.getDataSource(),
                    // metaData.getModelClass(), model.getId());
                    break;
                case Remove:
                    ((BaseModel) model).setValidFlag(DeleteType.Remove.getType());
                    break;
                case Delete:
                    ((BaseModel) model).setValidFlag(DeleteType.Delete.getType());
                    break;
            }
            return ormManage.getOrm().save(model, InterventionType.UPDATE, CrudServiceHelper.getDatasource(model.getClass()));
        } else {
            if (deleteType == DeleteType.Remove)
                return ormManage.getOrm().delete(model);
        }
        return false;
    }


    @LogTo("crud")
    @Override
    public boolean update(Query query) {
        //setArgs(query, args);
        CrudServiceHelper.initQuery(query);
        return ormManage.getOrm().update(query);
    }
}
