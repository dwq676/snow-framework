package com.zoe.snow.model.support;

import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.annotation.SupperClass;
import com.zoe.snow.model.RawModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by lpw on 15-8-12.
 */
@MappedSuperclass()
@SupperClass(value = "snow.model.ex.base")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BaseModelSupport extends BaseModelExSupport implements Sort, BaseModel, Automation {

    protected int sort = Integer.MIN_VALUE;

    @Override
    public int getSort() {
        return this.sort;
    }

    @Override
    public void setSort(int sort) {
        this.sort = sort;
    }
}
