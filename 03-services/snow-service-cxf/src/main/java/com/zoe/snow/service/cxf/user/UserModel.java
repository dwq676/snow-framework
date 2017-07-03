package com.zoe.snow.service.cxf.user;

import com.zoe.snow.json.Jsonable;
import com.zoe.snow.model.support.BaseModelSupport;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * UserModel
 *
 * @author Dai Wenqing
 * @date 2015/10/11
 */

@Component("car.user.model")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserModel extends BaseModelSupport {
    private String phone;
    private String password;
    private String sePassword;
    private String username;
    private String nickname;
    private Date birthday;
    private int identityType;
    private String identityNo;
    private String weibo;
    private String webchat;
    private String email;
    private String photo;
    private int anonymous = 0;

    private int sex;
    private String qq;
    private String maritalState;
    private int type;
    private String realName;
    private Date lastLoginTime;
    private String lastLoginIp;
    private int sort;
    private int expiration;
    private int multi;
    private Role role;

    private Map<String, Team> teamMap;
    private List<Role> roles;//=new ArrayList<>();

    @Jsonable
    public Map<String, Team> getTeamMap() {
        return teamMap;
    }

    public void setTeamMap(Map<String, Team> teamMap) {
        this.teamMap = teamMap;
    }

    @Jsonable
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Jsonable
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Jsonable
    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    @Jsonable
    public int getMulti() {
        return multi;
    }

    public void setMulti(int multi) {
        this.multi = multi;
    }


    @Jsonable
    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    @Jsonable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Jsonable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSePassword() {
        return sePassword;
    }

    public void setSePassword(String sePassword) {
        this.sePassword = sePassword;
    }


    @Jsonable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Jsonable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    @Jsonable
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Jsonable
    public int getIdentityType() {
        return identityType;
    }

    public void setIdentityType(int identityType) {
        this.identityType = identityType;
    }


    @Jsonable
    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }


    @Jsonable
    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }


    @Jsonable
    public String getWebchat() {
        return webchat;
    }

    public void setWebchat(String webchat) {
        this.webchat = webchat;
    }


    @Jsonable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Jsonable
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Jsonable
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    @Jsonable
    public String getMaritalState() {
        return maritalState;
    }

    public void setMaritalState(String maritalState) {
        this.maritalState = maritalState;
    }


    @Jsonable
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    @Jsonable
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Jsonable
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Jsonable
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Jsonable
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
