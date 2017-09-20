package com.zoe.snow.crud;

import com.zoe.snow.Global;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.conf.CoreConfig;
import com.zoe.snow.crud.service.ModelTableNullException;
import com.zoe.snow.dao.orm.context.OrmContext;
import com.zoe.snow.dao.orm.query.Query;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Datasource;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.support.Domain;
import com.zoe.snow.model.support.ValidFlag;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * CrudServiceHelper
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/2/27
 */
public class CrudServiceHelper {
    public static <T extends Model> void doMustNotNull(Class<T> classZ) {
        if (classZ == null) {
            throw new ModelTableNullException("ModelClass不能为空！");
        }
    }

    public static void initQuery(Query query) {
        if (Validator.isEmpty(query))
            return;
        OrmContext ormContext = OrmContext.class.cast(query);
        /*CrudServiceHelper.doMustNotNull(ormContext.getFrom());
        String ds = "";
        Datasource datasource = ormContext.getFrom().getAnnotation(Datasource.class);
        if (!Validator.isEmpty(datasource))
            ds = datasource.value();*/
        query.datasource(getDatasource(ormContext.getFrom()));
    }

    /**
     * 设置有效无效数据
     *
     * @param query
     * @param queryFlag 查询标记类型 （-1 = 只查询无效的数据,0=只查询有效数据,1=查询全部数据）
     * @return
     */
    public static Query setValidFlag(Query query, Global.QueryFlag queryFlag) {
        if (query == null)
            return null;
        // 测定实现实现了BaseModel
        OrmContext ormContext = OrmContext.class.cast(query);
        Model model = null;
        try {
            model = BeanFactory.getBean(ormContext.getFrom());
        } catch (Exception ex) {
            Logger.debug("未指定类型的查询");
            return query;
        }
        if (!Validator.isEmpty(model)) {
            if (model instanceof ValidFlag) {
                ValidFlag validFlag = (ValidFlag) model;
                if (queryFlag.getType() < 1) {
                    // criterionMap.put("validFlag", Criterion.Equals);
                    if (queryFlag == Global.QueryFlag.Valid)
                        ormContext.getQueryContext().put(ormContext.getTableNameAlias().get(ormContext.getFrom())
                                + "." + validFlag.getValidFlagName(), Global.ValidFlag.Valid.getType());
                    else {
                        ormContext.getQueryContext().put(ormContext.getTableNameAlias().get(ormContext.getFrom())
                                + "." + validFlag.getValidFlagName(), Global.ValidFlag.Delete.getType());
                    }
                }
            }
        }
        return query;
    }

    /**
     * 根据用户过滤权限数据
     *
     * @param query
     * @return
     */
    /*public static Query authFilter(Query query) {
        BaseUserModel user = BaseUserModel.class.cast(Global.user.get());
        if (user == null)
            return query;
        else if (user.getIsAdmin())
            return query;

        OrmContext ormContext = OrmContext.class.cast(query);
        if (ormContext != null) {
            Auth auth = ormContext.getFrom().getAnnotation(Auth.class);
            if (auth != null) {
                //ormContext.setOriginFromModelClass(query.);
                query.join(ormContext.getFrom(), JoinType.Inner);
                query.from(auth.value());
            }
        }
        return query;
    }*/
    public static Query setQueryCondition(Query query, boolean excludeDomain, Global.QueryFlag queryFlag) {
        setDomain(query, excludeDomain);
        // 获取有效的数据
        setValidFlag(query, queryFlag);
        // 细粒度，数据级别的过滤
        //authFilter(query);
        return query;
    }

    public static Query setDomain(Query query, boolean excludeDomain) {
        if (query == null)
            return null;
        OrmContext ormContext = OrmContext.class.cast(query);
        Model model = null;
        try {
            model = BeanFactory.getBean(ormContext.getFrom());
        } catch (Exception ex) {
            Logger.debug("未指定类型的查询");
            return query;
        }
        if (!Validator.isEmpty(model)) {
            if (model instanceof Domain) {
                // ormContext.getQueryContext().put("t0.validFlag", 1);
                UserHelper userHelper = BeanFactory.getBean(UserHelper.class);
                if (!excludeDomain) {
                    if (userHelper != null) {
                        ormContext.getQueryContext().put(ormContext.getTableNameAlias().get(ormContext.getFrom()) +
                                "." + ((Domain) model).getDomainName(), userHelper.getDomain());
                    } else {
                        throw new NotImplementedException("no implement the userHelper interface,please check again");
                    }
                }
            }
        }
        return query;
    }

    public static Query mapToQuery(Class<? extends Model> classZ, Map<String, Criterion> criterionMap
            , int page, int size, Object... args) {
        QueryManager queryManager = BeanFactory.getBean(QueryManager.class);
        Query query = queryManager.getQuery();
        if (criterionMap == null)
            return query;
        List<Object> argList = Converter.toList(args);
        query.from(classZ);
        query.paging(page, size);
        int ndx = 0;
        for (String key : criterionMap.keySet()) {
            if (criterionMap.get(key) == Criterion.Between) {
                query.where(key, criterionMap.get(key), new Object[]{argList.get(ndx), argList.get(++ndx)});
            } else {
                query.where(key, criterionMap.get(key), argList.get(ndx));
            }
            ndx++;
        }
        return query;
    }

    public static <T extends Model> String getDatasource(Class<T> modelClass) {
        Datasource datasource = modelClass.getAnnotation(Datasource.class);
        if (!Validator.isEmpty(datasource)) {
            if (!Validator.isEmpty(datasource.value())) {
                if (datasource.value().startsWith("$")) {
                    Object conf = CoreConfig.getContextProperty(datasource.value().substring(1));
                    if (conf == null)
                        return datasource.value();
                    else
                        return conf.toString();
                }
            }
        }
        return "";
    }

}

