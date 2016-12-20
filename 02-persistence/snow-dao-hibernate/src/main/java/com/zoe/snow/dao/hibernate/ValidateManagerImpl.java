package com.zoe.snow.dao.hibernate;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.dao.DataSourceBean;
import com.zoe.snow.dao.DataSourceManager;
import com.zoe.snow.dao.dialect.Columns;
import com.zoe.snow.dao.sql.Sql;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.message.MessageTool;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import com.zoe.snow.validator.Dao;
import com.zoe.snow.validator.exception.ValidatorException;
import com.zoe.snow.validator.rule.NotEmptyCheckerImpl;
import com.zoe.snow.validator.rule.OverLengthCheckerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ValidateManagerImpl
 *
 * @author Dai Wenqing
 * @date 2016/10/19
 */
@Component("snow.dao.hibernate.validator")
public class ValidateManagerImpl implements ValidateManager {
    @Autowired
    private Set<Checker> checkers;
    private String message;
    @Autowired
    private MessageTool messageTool;
    @Autowired
    private ModelTables modelTables;
    @Autowired
    private Sql sqlQuery;
    @Autowired
    private SessionManage sessionManage;

    @Override
    public void validate(Class<? extends Model> classZ, String fieldName, Object value, Object[] parameters, String... datasource) {
        if (!Validator.isEmpty(checkers)) {
            Map<String, Columns> columnsMap = getColumns(classZ, datasource);
            //为空不进行验证
            if (Validator.isEmpty(columnsMap))
                return;
            for (Checker checker : checkers) {
                Dao dao = checker.getClass().getAnnotation(Dao.class);
                if (!Validator.isEmpty(dao)) {
                    ModelTable modelTable = modelTables.get(classZ);
                    String columnName = modelTable.getFields().get(Converter.toFirstUpperCase(fieldName));
                    //找不到对应的列名，不进行验证
                    if (Validator.isEmpty(columnName))
                        return;
                    Columns column = columnsMap.get(columnName);

                    //非空判断必须结合数据库字段不为空
                    if (checker instanceof NotEmptyCheckerImpl) {
                        if (column.getIsNullAble().toLowerCase().equals("no")
                                || column.getIsNullAble().toLowerCase().equals("false")) {
                            if (Validator.isEmpty(value)) {
                                check(classZ, fieldName, value, parameters, checker);
                            }
                        }
                    } else if (checker instanceof OverLengthCheckerImpl) {
                        check(classZ, fieldName, value, new Object[]{column.getColumnDataLength()}, checker);
                    } else
                        check(classZ, fieldName, value, parameters, checker);
                }
            }
        }
    }

    private void check(Class<? extends Model> classZ, String fieldName, Object value, Object[] parameters, Checker checker) {
        if (!checker.validate(value, parameters)) {
            Component component = checker.getClass().getAnnotation(Component.class);
            message = messageTool.get(component.value(), fieldName);
            ValidatorException validatorException = BeanFactory.getBean("snow.exception.validator." + checker.getName());
            validatorException.setMessage(classZ, fieldName, message);
            throw RuntimeException.class.cast(validatorException);
        }
    }

    private Map<String, Columns> getColumns(Class<? extends Model> modelClass, String... datasource) {
        ModelTable modelTable = modelTables.get(modelClass);
        if (modelTable == null)
            return null;
        String tableName = modelTable.getTableName();
        DataSourceBean dataSourceBean = DataSourceManager.getDataSourceBean(datasource);
        Columns cs = BeanFactory.getBean("snow.dao.dialect.column." + dataSourceBean.getDriver());
        PageList<Columns> columnsList = sqlQuery.getList(cs.getClass(),
                sessionManage.getDialect(datasource).getColumnSql(), -1, -1, new Object[]{
                        dataSourceBean.getSchema(), dataSourceBean.getSchema(), tableName}
                , datasource);
        if (Validator.isEmpty(columnsList))
            return null;
        Map<String, Columns> columnsMap = columnsList.getList().parallelStream()
                .collect(Collectors.toMap(c -> c.getColumnName(), c -> c));
        return columnsMap;

        /*String columnName = modelTable.getFields().get(Converter.toFirstUpperCase(fieldName));
        if (Validator.isEmpty(columnName))
            return;
        Columns column = columnsMap.get(columnName);
        if (Validator.isEmpty(column))
            return;
        if (column.getIsNullAble().toLowerCase().equals("no")
                || column.getIsNullAble().toLowerCase().equals("false")) {
            if (Validator.isEmpty(o)) {
                //throw new NotEmptyException(modelClass, fieldName);
            }
        }
        if (!Validator.isEmpty(o))
            if (column.getColumnDataLength() < o.toString().length())
        //throw new OverLengthException(modelClass, fieldName);*/

    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
