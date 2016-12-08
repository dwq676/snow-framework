package com.zoe.snow.model;

import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.annotation.Property;
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
public class RawModel implements Model {
    private static final String ID = "id";
    private static final String UUID = "uuid";

    private String id;

    @Jsonable
    @Column(name = ID)
    @Id
    @GeneratedValue(generator = UUID)
    @GenericGenerator(name = UUID, strategy = "com.zoe.snow.model.IdGenerator")
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
