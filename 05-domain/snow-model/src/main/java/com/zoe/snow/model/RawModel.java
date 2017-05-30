package com.zoe.snow.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.annotation.SupperClass;
import com.zoe.snow.model.enums.IdStrategy;
import com.zoe.snow.model.mapper.ModelTable;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.util.Validator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Map;

/**
 * Model支持类，主键ID使用UUID。
 *
 * @author dwq
 */
@MappedSuperclass()
@SupperClass(value = "snow.model.raw")
public class RawModel implements Model {
    private static final String ID = "id";
    private static final String UUID = "uuid";

    private String id;

    @Jsonable
    @JSONField(name = ID)
    @Column(name = ID)
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = IdStrategy.Assigned)
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
