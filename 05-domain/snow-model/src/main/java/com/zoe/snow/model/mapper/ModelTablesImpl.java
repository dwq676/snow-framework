package com.zoe.snow.model.mapper;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.listener.ContextRefreshedListener;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.annotation.Unique;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lpw
 */
@Repository("snow.model.mapper.tables")
public class ModelTablesImpl implements ModelTables, ContextRefreshedListener {
    @Autowired(required = false)
    protected Set<Model> models;
    protected Map<Class<? extends Model>, ModelTable> map;
    protected Map<String, Model> tableNameMap;

    @Override
    public ModelTable get(Class<? extends Model> modelClass) {
        ModelTable modelTable = map.get(modelClass);
        if (modelTable == null)
            throw new NullPointerException("无法获得Model类[" + modelClass + "]对应的ModelTable实例！");

        return modelTable;
    }

    @Override
    public Model get(String tableName) {
        return tableNameMap.get(tableName);
    }

    @Override
    public int getContextRefreshedSort() {
        return 2;
    }

    @Override
    public void onContextRefreshed() {
        if (map != null )
            return;

        map = new ConcurrentHashMap<>();
        //tableNameMap = new ConcurrentHashMap<>();
        if (!Validator.isEmpty(models))
            for (Model model : models)
                parse(model);
    }

    @Override
    public String getName() {
        return "ModelTables";
    }

    protected void parse(Model model) {
        Table table = model.getClass().getAnnotation(Table.class);
        if (table == null)
            return;

        ModelTable modelTable = BeanFactory.getBean(ModelTable.class);

        if (Validator.isEmpty(modelTable))
            return;

        modelTable.setModelClass(model.getClass());
        modelTable.setTableName(table.name());

        Method[] methods = model.getClass().getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (name.length() < 3)
                continue;

            if (name.equals("getId")) {
                modelTable.setIdColumnName(method.getAnnotation(Column.class).name());

                continue;
            }

            String fieldName = name.substring(3);
            if (name.startsWith("get")) {
                modelTable.addGetMethod(model.getClass(), fieldName, method);
                Column column = method.getAnnotation(Column.class);
                if (column != null)
                    modelTable.addColumnAndProperties(column.name(), fieldName);
                Unique unique = method.getAnnotation(Unique.class);
                if (!Validator.isEmpty(unique))
                    modelTable.addUniqueColumn(Converter.toFirstLowerCase(fieldName));
                JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
                if (!Validator.isEmpty(joinColumn)) {
                    Class<? extends Model> clazz = (Class<? extends Model>) method.getReturnType();
                    modelTable.addJoinColumn(joinColumn.name(), clazz);
                    modelTable.addJoinColumnName(joinColumn.name(), fieldName);
                    modelTable.addJoinReferenceName(joinColumn.name(), joinColumn.referencedColumnName());
                    modelTable.getJoinColumn().put(clazz, joinColumn);
                }
                /*FetchWay fetch = method.getAnnotation(FetchWay.class);
                if (!Validator.isEmpty(fetch))
                    modelTable.addFetchClass((Class<? extends Model>) method.getReturnType(), fetch);*/
                continue;
            }

            if (name.startsWith("set")) {
                modelTable.addSetMethod(model.getClass(), fieldName, method);

                continue;
            }
        }

        map.put(model.getClass(), modelTable);
        //tableNameMap.put(table.name(), model);
    }
}
