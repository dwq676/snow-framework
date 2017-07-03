package com.zoe.snow.service.cxf;

import com.zoe.snow.json.DJson;
import com.zoe.snow.service.cxf.user.Dept;
import net.sf.json.JSONObject;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import com.zoe.snow.model.ModelHelper;
import com.zoe.snow.service.cxf.user.Role;
import com.zoe.snow.service.cxf.user.Team;
import com.zoe.snow.service.cxf.user.UserModel;

import java.util.*;

/**
 * AuthorInterceptor
 *
 * @author Dai Wenqing
 * @date 2015/12/30
 */
public class AuthorInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private SAAJInInterceptor saa = new SAAJInInterceptor();

    public AuthorInterceptor() {
        super(Phase.PRE_PROTOCOL);
        getAfter().add(SAAJInInterceptor.class.getName());
    }

    public void handleMessage(SoapMessage message) throws Fault {
        test();
          /* System.out.println("come in ServicesAuthorInterceptor ");
        SOAPMessage mess = message.getContent(SOAPMessage.class);
        if (mess == null) {
            saa.handleMessage(message);
            mess = message.getContent(SOAPMessage.class);
        }
        SOAPHeader head = null;
        try {
            head = mess.getSOAPHeader();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (head == null) {
            return;
        }
        NodeList nodes = head.getElementsByTagName("tns:spId");
        NodeList nodepass = head.getElementsByTagName("tns:spPassword");

        //String sql = "select count(v.viewSpaceId) from ViewSpace v";

      if("zhong".equals(nodes.item(0).getTextContent())&&("guo".equals(nodepass.item(0).getTextContent()))){
            System.out.println("认证成功");
        }else {
            SOAPException soapExc = new SOAPException("认证错误");
            throw new Fault(soapExc);
        }
        System.out.println("6");*/

        //// TODO: 2015/12/30 权限过滤


    }

    public void test() {
        UserModel userModel = new UserModel();
        // userModel.field
        userModel.setPassword("1234");
        userModel.setSort(0);
        userModel.setValidFlag(0);
        userModel.setAnonymous(2);
        userModel.setEmail("dwq@66.com");
        userModel.setId("dwq");
        userModel.setPhone("1386595925");
        userModel.setUsername("dwq7878");
        userModel.setNickname("嘎嘎");
        userModel.setCreatedAt(new Date());

        Role role = new Role();
        role.setCreatedAt(new Date());
        role.setName("111yyy");
        role.setCreatedAt(new Date());
        role.setDomain("1");

        Team team = new Team();
        team.setCreatedAt(new Date());
        team.setCode("team001");

        Dept dept = new Dept();
        dept.setName("研发中心");
        Map<String, Team> map = new LinkedHashMap<>();
        map.put("myDept", team);
        //dept.setMap(map);

        role.setDept(dept);

        userModel.setTeamMap(map);
        userModel.setRole(role);
        List<Role> roles = new ArrayList<>();
        userModel.setRoles(roles);
        userModel.getRoles().add(role);

        //ModelHelper modelHelper = BeanFactory.getBean(ModelHelper.class);

        //{"type":"0","expiration":"0","anonymous":"2","role":{"name":"111yyy","dept":{"name":"研发中心","userModelMap":"myDept=UserModel@32bd31eb","validFlag":"0"},"domain":"1","validFlag":"0","createTime":"Mon Jan 11 18:59:14 CST 2016"},"sort":"0","email":"dwq@66.com","roles":[{"name":"111yyy","dept":{"name":"研发中心","userModelMap":"myDept=UserModel@32bd31eb","validFlag":"0"},"domain":"1","validFlag":"0","createTime":"Mon Jan 11 18:59:14 CST 2016"}],"username":"dwq7878","multi":"0","sex":"0","phone":"1234455566","identityType":"0","nickname":"时时","validFlag":"0","createTime":"Mon Jan 11 18:59:14 CST 2016","id":"dwq"}
 /*       {
            "type":"0", "expiration":"0", "role":{
            "name":"111yyy", "dept":{
                "name":"研发中心", "map":{
                    "myDept":{
                        "code":"team001", "createTime":"Mon Jan 11 19:23:20 CST 2016", "validFlag":"0"
                    }
                },"validFlag":"0"
            },"domain":"1", "createTime":"Mon Jan 11 19:23:20 CST 2016", "validFlag":"0"
        },"roles":[{
            "name":"111yyy", "dept":{
                "name":"研发中心", "map":{
                    "myDept":{
                        "code":"team001", "createTime":"Mon Jan 11 19:23:20 CST 2016", "validFlag":"0"
                    }
                },"validFlag":"0"
            },"domain":"1", "createTime":"Mon Jan 11 19:23:20 CST 2016", "validFlag":"0"
        }],"email":"dwq@66.com", "anonymous":"2", "username":"dwq7878", "sort":"0", "identityType":"0", "phone":"1386595925", "multi":"0", "nickname":"嘎嘎", "sex":"0", "createTime":"Mon Jan 11 19:23:20 CST 2016", "validFlag":"0", "id":"dwq"
        }*/
        String json = DJson.toJson(userModel).toString();
        /*JSONObject jsonObject = new JSONObject();
        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("roles", role.class);
        jsonObject.toBean(JSONObject.fromObject(json), UserModel.class, classMap);
        System.out.printf(json);*/
        DJson.parseJson(json, UserModel.class);
    }
}