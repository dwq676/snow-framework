/**
 *
 */
package com.zoe.snow.dao.hibernate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.dao.DataSourceManager;
import com.zoe.snow.dao.Mode;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.model.PageList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author dwq
 */
@Repository("snow.dao.hibernate")
@Primary
public class HibernateOrmImpl implements HibernateOrm {
    private static final String[] ARG = {"?", ":arg", "arg"};
    @Autowired
    private SessionManage sessionManage;
    @Autowired
    private ModelTables modelTables;
    @Autowired
    private ValidateManager validateManager;

    @Override
    public String getOrmName() {
        return "hibernate";
    }

    @Override
    public <T extends Model> T findById(Class<T> modelClass, String id, String... datasource) {
        if (Validator.isEmpty(id))
            return null;

        return (T) sessionManage.get(Mode.Read, datasource).get(modelClass, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findOne(HibernateQuery query) {
        query.paging(1, 1);
        PageList<T> pageList = query(query);
        return pageList.size() > 0 ? pageList.get(0) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> PageList<T> query(HibernateQuery query) {
        PageList<T> models = BeanFactory.getBean(PageList.class);
        if (Validator.isEmpty(query))
            return models;

        query.getQueryContext().forEach((k, v) -> {
            if (!k.startsWith("$"))
                query.getArgs().add(v);
        });

        // 每页面总条件大于0，且当前不是获取第一页
        if (query.getPage() > 0) {
            models.getPage().setPage(Converter.toInt(createCountQuery(Mode.Read, query).iterate().next()), query.getPage(), query.getSize());
        }

        if (!Validator.isEmpty(query.getTo())) {
            try {
                models.setList(createCommonQuery(Mode.Read, query).setResultTransformer(Transformers.aliasToBean(query.getTo())).list());
            } catch (Exception e) {
                throw new QueryCastException("类型转换出错，转须指明To(class),且查询要加上select(ToModel全部字段，以逗号隔开)");
            }
        } else
            models.setList(createCommonQuery(Mode.Read, query).list());

        return models;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Model> Iterator<T> iterate(HibernateQuery query) {
        if (Validator.isEmpty(query.getTo()))
            return createCommonQuery(Mode.Read, query).iterate();
        return createCommonQuery(Mode.Read, query).setResultTransformer(Transformers.aliasToBean(query.getTo())).iterate();
    }

    @Override
    public int count(HibernateQuery query) {
        query.getQueryContext().forEach((k, v) -> {
            query.getArgs().add(v);
        });
        return Converter.toInt(createCountQuery(Mode.Read, query).iterate().next());
    }

    private <T extends Model> void check(T model, String... datasource) {
        if (Validator.isEmpty(model))
            return;
        ModelTable modelTable = modelTables.get(model.getClass());
        if (modelTable == null)
            return;
        modelTable.getFields().forEach((k, v) -> {
            try {
                String methodName = "get" + k;
                Object o = model.getClass().getMethod(methodName).invoke(model);
                validateManager.validate(model.getClass(), k, o, null, datasource);
            } catch (Exception e) {

            }
        });

        /*String tableName = modelTable.getTableName();
        String fieldName = modelTable.getColumns().get(c.getColumnName());
        //Id不进行验证
        if (c.getColumnName().toLowerCase().equals("id"))
            return;


         columnsList.getList().forEach(c -> {

           if (c.getIsNullAble().toLowerCase().equals("no")
                    || c.getIsNullAble().toLowerCase().equals("false")) {
                if (Validator.isEmpty(o)) {
                    throw new NotEmptyException(model.getClass(), fieldName);
                }
            }
            if (!Validator.isEmpty(o))
                if (c.getColumnDataLength() < o.toString().length())
                    throw new OverLengthException(model.getClass(), fieldName);

        });*/
    }

    @Override
    public <T extends Model> boolean save(T model, InterventionType interventionType, String... datasource) {
        if (model == null) {
            Logger.warn(null, "要保存的Model为null！");
            return false;
        }

        if (Validator.isEmpty(model.getId()))
            model.setId(null);
        Object object = null;

        /*if (DataSourceManager.getDataSourceBean(datasource).getToValidate())
            check(model, datasource);*/

        Session session = sessionManage.get(Mode.Write, datasource);
        try {
            if (interventionType == InterventionType.INSERT)
                session.save(model);
            else if (interventionType == InterventionType.UPDATE)
                session.update(model);
            else
                session.saveOrUpdate(model);
            session.flush();
        } catch (HibernateException e) {
            object = session.merge(model);
        }
        if (!Validator.isEmpty(object)) {
            if (object instanceof Model) {
                model.setId(Model.class.cast(object).getId());
            }
        }
        return true;
    }

    @Override
    public boolean update(HibernateQuery query) {
        if (Validator.isEmpty(query))
            return false;
        if (query.getSet().keySet().size() < 1)
            return false;
        StringBuilder hql = query.from(new StringBuilder().append("UPDATE "), false).append(" set ");
        int ndx = 0;

        for (String k : query.getSet().keySet()) {
            if (DataSourceManager.getDataSourceBean(query.getDatasource()).getToValidate())
                validateManager.validate(query.getFrom(), k, query.getSet().get(k), null, query.getDatasource());
            if (ndx > 0)
                hql.append(",");
            hql.append(k).append(Criterion.Equals.getType());
            ndx++;
            query.getArgs().add(query.getArgs().size() - 1, query.getSet().get(k));
        }
        /*if (!Validator.isEmpty(query.getWhere()))
            hql.append(" WHERE ").append(query.getWhere());*/
        query.where(hql);
        Query q = sessionManage.get(Mode.Write, query.getDatasource()).createQuery(replaceArgs(hql));
        setParameters(query, q);
        return q.executeUpdate() >= 0;
    }

    @Override
    public <T extends Model> boolean delete(T model, String... datasource) {
        sessionManage.get(Mode.Write, datasource).delete(model);
        return true;
    }

    @Override
    public boolean delete(HibernateQuery query) {
        StringBuilder hql = query.from(new StringBuilder().append("DELETE "), false);
        if (!Validator.isEmpty(query.getWhere()))
            hql.append(" WHERE ").append(query.getWhere());

        createCommonQuery(Mode.Write, query).executeUpdate();

        return true;
    }

    @Override
    public JSONObject getAsJsonObject(HibernateQuery query) {
        return null;
    }

    @Override
    public JSONArray getAsJsonArray(HibernateQuery query) {
        return null;
    }

    protected Query createCommonQuery(Mode mode, HibernateQuery hQuery) {
        StringBuilder hql = hQuery.getQuerySql(false);

        Query query = sessionManage.get(mode, hQuery.getDatasource()).createQuery(replaceArgs(hql));
        if (hQuery.getSize() > 0)
            query.setFirstResult(hQuery.getSize() * (hQuery.getPage() - 1)).setMaxResults(hQuery.getSize());
        setParameters(hQuery, query);
        return query;

    }

    protected Query createCountQuery(Mode mode, HibernateQuery hQuery) {
        StringBuilder hql = hQuery.from(new StringBuilder("SELECT COUNT(*) from "), true);
        hQuery.where(hql);
        if (Logger.isDebugEnable())
            Logger.debug("sql:{}", hql);
        Query query = sessionManage.get(mode, hQuery.getDatasource()).createQuery(replaceArgs(hql));
        setParameters(hQuery, query);
        return query;
    }

    protected void setParameters(HibernateQuery hQuery, Query query) {
        Object[] args = hQuery.getArgs().toArray();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null)
                query.setParameter(ARG[2] + i, args[i]);
            else if (args[i] instanceof Collection<?>)
                query.setParameterList(ARG[2] + i, (Collection<?>) args[i]);
            else if (args[i].getClass().isArray())
                query.setParameterList(ARG[2] + i, Arrays.asList(args[i]).parallelStream().collect(Collectors.toList()));
            else
                query.setParameter(ARG[2] + i, args[i]);
        }
    }

    protected String replaceArgs(StringBuilder hql) {
        for (int i = 0, position; (position = hql.indexOf(ARG[0])) > -1; i++)
            hql.replace(position, position + 1, ARG[1] + i);

        return hql.toString();
    }
}
