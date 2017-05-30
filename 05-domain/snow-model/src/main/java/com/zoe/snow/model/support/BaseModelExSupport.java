package com.zoe.snow.model.support;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zoe.snow.model.CustomDateDeSerializer;
import com.zoe.snow.model.CustomDateSerializer;
import com.zoe.snow.model.RawModel;
import com.zoe.snow.model.annotation.Jsonable;
import com.zoe.snow.model.annotation.Property;
import com.zoe.snow.model.annotation.SupperClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * BaseModelExSupport
 *
 * @author Dai Wenqing
 * @date 2016/5/23
 */
@MappedSuperclass()
@SupperClass(value = "snow.model.base")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BaseModelExSupport extends RawModel implements BaseModel, Automation {
    protected int validFlag = Integer.MIN_VALUE;
    protected Date createTime;
    protected String createUser;
    protected Date modifyTime;
    protected String modifyUser;
    protected String remark;
    protected String domain;

/*
    public void initBaseField(RawModel baseModel) {
        this.validFlag = baseModel.getValidFlag();
        this.createTime = baseModel.getCreatedAt();
        this.createUser = baseModel.getCreatedBy();
        this.modifyTime = baseModel.getUpdatedAt();
        this.modifyUser = baseModel.getUpdatedBy();
        this.remark = baseModel.getRemark();
        this.domain = baseModel.getDomain();
    }
*/

    @Override
    @Column(name = "valid_flag")
    @Jsonable
    @Property(name = "有效标记")
    public int getValidFlag() {
        return validFlag;
    }

    @Override
    public void setValidFlag(int validFlag) {
        this.validFlag = validFlag;
    }

    @Override
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeSerializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    @Jsonable
    @Property(name = "创建时间")
    public Date getCreatedAt() {
        return createTime;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createTime = createdAt;
    }

    @Override
    @Column(name = "create_user")
    @Jsonable
    @Property(name = "创建者")
    public String getCreatedBy() {
        return createUser;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createUser = createdBy;
    }

    @Override
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeSerializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    @Jsonable
    @Property(name = "修改时间")
    public Date getUpdatedAt() {
        return modifyTime;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.modifyTime = updatedAt;
    }

    @Override
    @Column(name = "modify_user")
    @Jsonable
    @Property(name = "修改者")
    public String getUpdatedBy() {
        return modifyUser;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.modifyUser = updatedBy;
    }

    @Override
    @Column(name = "remark")
    @Jsonable
    @Property(name = "备注")
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    @Column(name = "domain")
    @Jsonable
    @Property(name = "域")
    public String getDomain() {
        return domain;
    }

    @Override
    public void setDomain(String domain) {
        this.domain = domain;
    }
}
