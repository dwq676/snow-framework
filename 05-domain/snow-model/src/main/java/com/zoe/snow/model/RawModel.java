package com.zoe.snow.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.annotation.SupperClass;
import com.zoe.snow.model.enums.IdStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
