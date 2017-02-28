package com.zoe.snow.test.support;

import com.zoe.snow.Global;
import com.zoe.snow.auth.TokenProcessor;
import com.zoe.snow.crud.CrudService;
import com.zoe.snow.crud.Result;
import com.zoe.snow.crud.service.proxy.QueryProxy;
import com.zoe.snow.dao.Closable;
import com.zoe.snow.dao.Transaction;
import com.zoe.snow.dao.sql.Sql;
import com.zoe.snow.model.ModelHelper;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.Criterion;
import com.zoe.snow.model.enums.JoinType;
import com.zoe.snow.model.enums.Operator;
import com.zoe.snow.model.enums.OrderBy;
import com.zoe.snow.model.mapper.ModelTables;
import com.zoe.snow.message.MessageTool;
import com.zoe.snow.test.School;
import com.zoe.snow.test.Student;
import com.zoe.snow.test.UserModel;
import com.zoe.snow.test.UserResultModel;
import com.zoe.snow.util.Generator;
import com.zoe.snow.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by on 2016/3/24.
 */

@ContextConfiguration(locations = {"/configuration.spring.xml"})
@Service
public class TestngSupportImpl extends AbstractTestNGSpringContextTests implements TestngSupport, Runnable {

    int count = 10;
    CountDownLatch downLatch = new CountDownLatch(count);
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Autowired
    private CrudService crudService;
    @Autowired
    private Sql sql;
    @Autowired(required = false)
    private Set<Closable> cloneableSet;
    @Autowired(required = false)
    private Set<Transaction> transactionSet;
    @Autowired
    private ModelTables modelTables;
    @Autowired
    private MessageTool messageTool;

    public static void Main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:*.spring.xml");
        // ApplicationContext applicationContext
    }

    @Transactional
    public void myTest() {

    }

    @Test
    public void elasticTest() {
       /*JSONArray jsonArray= crudService.elastic("mip").from("info").paging(0, 20)
                //.where("USER_INFO.CONTENT.DE01_001_002", Criterion.Like, "吴学凤")
                //.where("USER_INFO.CONTENT.DE01_003_003", Criterion.GreaterThan, 40)
                .group("USER_INFO.CONTENT.DE01_003_003").asJsonObject();

        JSONArray jsonObject = crudService.elastic("mip")
                .from("info")
                .paging(0, 20)
                .where("MODEL_CODE", Criterion.Equals, "ffe9311e-855c-457a-942a-4f21a44e60a0")
                .where("USER_ID", Criterion.Equals, "4a6e196f-0db8-482b-b3b7-90c22333445a")
                .asJsonObject();*/

        String[] firstName = new String[]{"赵", "钱", "申", "齐", "雷", "付", "孙", "廖", "田", "李", "王", "吕", "洪", "杜", "陈", "黄", "袁", "郭", "沈", "周", "张", "赖", "徐", "白", "蔡", "江", "林", "连", "吴", "谢"};
        String[] midName = new String[]{"平", "森", "书", "伟", "达", "锦", "晓", "海", "才", "秀", "宇", "雅", "允", "敏", "聪", "观", "杰", "启", "兰", "源", "思", "正", "娴", "锋", "峰", "健", "佳", "福", "健", "龙", "忠", "旺", "子", "坚", "瑞", "雪", "超", "永", "珠", "美", "英", "东", "强", "阳", "清", "德", "信", "凤", "龙", "顺", "铭", "文", "艺", "", "乐", "东", "以", "华", "丽", "恒", "真", "茜", "真", "学", "俊", "志", "秋"};
        String[] normal = new String[]{"正常", "正常"};

        String[] blood = new String[]{"O", "A", "B", "AB"};
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            idList.add(Generator.uuid());
        }

        for (int i = 0; i < 100000; i++) {
            String json = "{\n" +
                    "    \"model_code\": \"" +
                    idList.get(new Random().nextInt(1000)) + "\",\n" +
                    "    \"patient_id\": " + "\"" +
                    Generator.uuid()
                    + "\"" + ",\n" +
                    "    \"USER_INFO\": {\n" +
                    "        \"ROWS\": 1,\n" +
                    "        \"CONTENT\": [\n" +
                    "            {\n" +
                    "                \"DE01_001_001\": \"A0320994829232-132\",\n" +
                    "                \"DE01_001_002\": \"" +
                    firstName[new Random().nextInt(firstName.length - 1)]
                    + midName[new Random().nextInt(midName.length - 1)]
                    + midName[new Random().nextInt(midName.length - 1)] + "\",\n" +
                    "                \"DE01_001_003\": \"1\",\n" +
                    "                \"DE01_001_004\": \"350781198608120200\",\n" +
                    "                \"DE01_002_001\": 27,\n" +
                    "                \"DE01_002_009\": \"" +
                    normal[new Random().nextInt(1)] + "\",\n" +
                    "                \"DE01_003_003\": " +
                    new Random().nextInt(50)
                    + ",\n" +
                    "                \"DE01_003_005\": 64,\n" +
                    "                \"DE01_001_005\": 18030123707,\n" +
                    "                \"DE01_001_006\": \"" +
                    blood[new Random().nextInt(3)]
                    + "\",\n" +
                    "                \"DE01_003_007\": \"2016-04-12\",\n" +
                    "                \"DE01_003_009\": \"2016.12.09\",\n" +
                    "                \"DE01_003_008\": \"2016.09.01\"\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"USER_HEALTH\": {\n" +
                    "            \"ROWS\": 1,\n" +
                    "            \"CONTENT\": [\n" +
                    "                {\n" +
                    "                    \"ROW_NUMBER\": 1,\n" +
                    "                    \"DE01_004_002\": [\n" +
                    "                        {\n" +
                    "                            \"1\": \"神经病\",\n" +
                    "                            \"2\": \"高血压\",\n" +
                    "                            \"3\": \"糖尿病\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"DE01_004_003\": [\n" +
                    "                        {\n" +
                    "                            \"1\": \"糖尿病\",\n" +
                    "                            \"2\": \"盆骨畸形\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"DE01_004_004\": \"无\",\n" +
                    "                    \"DE01_004_005\": 5,\n" +
                    "                    \"DE01_004_006\": [\n" +
                    "                        {\n" +
                    "                            \"1\": \"早教音乐\",\n" +
                    "                            \"2\": \"早教书籍\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"DE01_004_007\": [\n" +
                    "                        {\n" +
                    "                            \"1\": \"喜酸\",\n" +
                    "                            \"2\": \"喜肉\",\n" +
                    "                            \"3\": \"海鲜\"\n" +
                    "                        }\n" +
                    "                    ],\n" +
                    "                    \"DE01_004_008\": \"青霉素\",\n" +
                    "                    \"DE01_004_009\": \"2016.09.01\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"USER_DRUG_INFO\": {\n" +
                    "                \"ROWS\": 3,\n" +
                    "                \"CONTENT\": [\n" +
                    "                    {\n" +
                    "                        \"ROW_NUMBER\": 1,\n" +
                    "                        \"DE01_007_002\": \"YP0029102\",\n" +
                    "                        \"DE01_007_003\": \"宝宝孕保\",\n" +
                    "                        \"DE01_007_004\": \"10ML/10支\",\n" +
                    "                        \"DE01_007_005\": 5,\n" +
                    "                        \"DE01_007_006\": 1,\n" +
                    "                        \"DE01_007_007\": \"TID\",\n" +
                    "                        \"DE01_007_008\": \"2016.06.03\",\n" +
                    "                        \"DE01_007_009\": \"2016.06.08\",\n" +
                    "                        \"DE01_007_010\": 30,\n" +
                    "                        \"DE01_004_009\": \"2016.09.01\"\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"ROW_NUMBER\": 2,\n" +
                    "                        \"DE01_007_002\": \"YP0029109\",\n" +
                    "                        \"DE01_007_003\": \"叶酸片\",\n" +
                    "                        \"DE01_007_004\": \"100粒/瓶\",\n" +
                    "                        \"DE01_007_005\": 30,\n" +
                    "                        \"DE01_007_006\": 1,\n" +
                    "                        \"DE01_007_007\": \"TID\",\n" +
                    "                        \"DE01_007_008\": \"2016.06.03\",\n" +
                    "                        \"DE01_007_009\": \"2016.06.08\",\n" +
                    "                        \"DE01_007_010\": 90,\n" +
                    "                        \"DE01_004_009\": \"2016.09.01\"\n" +
                    "                    },\n" +
                    "                    {\n" +
                    "                        \"ROW_NUMBER\": 3,\n" +
                    "                        \"DE01_007_002\": \"YP8019009\",\n" +
                    "                        \"DE01_007_003\": \"钙片\",\n" +
                    "                        \"DE01_007_004\": \"100粒/瓶\",\n" +
                    "                        \"DE01_007_005\": 30,\n" +
                    "                        \"DE01_007_006\": 1,\n" +
                    "                        \"DE01_007_007\": \"TID\",\n" +
                    "                        \"DE01_007_008\": \"2016.06.03\",\n" +
                    "                        \"DE01_007_009\": \"2016.06.08\",\n" +
                    "                        \"DE01_007_010\": 90,\n" +
                    "                        \"DE01_004_009\": \"2016.09.01\"\n" +
                    "                    }\n" +
                    "                ]\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n";

            String json1 = "{\n" +
                    "  \"model_code\": 1111,\n" +
                    "  \"patient_id\": 2222,\n" +
                    "  \"BUSINESE_NODE1\": [\n" +
                    "    {\n" +
                    "      \"busineseCode\": \"fff\",\n" +
                    "      \"elemenNode\": {\n" +
                    "        \"DE_0001\": \"111\",\n" +
                    "        \"DE_0002\": \"222\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"busineseCode\": \"ggg\",\n" +
                    "      \"elemenNode\": {\n" +
                    "        \"DE_0002\": \"111\",\n" +
                    "        \"DE_0003\": \"222\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";

            boolean result = crudService.elastic("mip").save("YCF001", json1);
            System.out.println(i + ":" + result);
            //crudService.elastic("mip").update("info", "AVenHXpaI-m3JO1iRAbu", json);
        }
    }

    @Transactional
    @Test
    public void multiThread() {
        for (int i = 0; i < count; i++) {
            new Thread(this).start();
        }
        try {
            downLatch.await();
        } catch (Exception e) {

        }
    }

    @Override
    @Test
    public void run() {
        startTime.set(System.currentTimeMillis());
        transactionSet.forEach(Transaction::beginTransaction);
        for (int j = 0; j < 1000; j++) {
            /*String json1 = "{\n" +
                    "  \"model_code\": 1111,\n" +
                    "  \"patient_id\": 2222,\n" +
                    "  \"BUSINESE_NODE1\": [\n" +
                    "    {\n" +
                    "      \"busineseCode\": \"fff\",\n" +
                    "      \"elemenNode\": {\n" +
                    "        \"DE_0001\": \"111\",\n" +
                    "        \"DE_0002\": \"222\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"busineseCode\": \"ggg\",\n" +
                    "      \"elemenNode\": {\n" +
                    "        \"DE_0002\": \"111\",\n" +
                    "        \"DE_0003\": \"222\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}\n";


            boolean result = crudService.elastic("me").save("test", json1);
            System.out.println(Thread.currentThread().getName() + ":" + result);*/
            UserModel userModel = new UserModel();
            userModel.setPassword("1");
            userModel.setUserName(Thread.currentThread().getName() + "_" + j);
            School school = new School();
            school.setId("1");
            //userModel.setStudent(school);
            crudService.save(userModel);

        }
        transactionSet.forEach(Transaction::commit);
        cloneableSet.forEach(Closable::close);
        System.out.println(Thread.currentThread().getName() + "总共运行时间：" + (System.currentTimeMillis() - startTime.get()));
        downLatch.countDown();
    }

    @Test
    public void testSave() {

        Result<PageList> pageListResult = Result.reply(() -> crudService.query().from(UserModel.class)
                .where("userName", Criterion.In, "jack_28,jack_32").paging(1, 10).list());

        pageListResult.getData().getList();

        transactionSet.forEach(Transaction::beginTransaction);
        UserModel userModel = new UserModel();
        userModel.setId("8a80815957fff6350157fff641b90000");
        userModel.setPassword("##765");
        userModel.setUserName("john_" + Thread.currentThread().getName() + "_" + 0);
        School school = new School();
        school.setId("1");
        school.setSchoolName("diwf");
        //userModel.setStudent(school);
        crudService.save(userModel);
        crudService.update(school);
        transactionSet.forEach(Transaction::commit);
        cloneableSet.forEach(Closable::close);
    }

    @Test
    public void testUpdate() {

        TokenProcessor.getInstance().generateToken("niwokkdkek", true);

        crudService.execute().update(UserModel.class)
                .set("validFlag", Global.ValidFlag.Delete.getType())
                .where("id", "8a80815957fb1f800157fb1f88f20000").invoke();
    }

    @Override
    @Test
    @Transactional
    public void test() {
        String pin = PinYinUtil.generatePinYin("呃呃");

        UserModel userModel = new UserModel();
       /* userModel = new UserModel();
        userModel.setUserName("myTest");
        userModel.setPassword("1111111");
        userModel.setId("2");
        crudService.update(userModel);*/

        /**/


        // 查询结果返回单个对象
        // UserModel userModel = crudService.execute().sql(UserModel.class,
        // "sql语句 ").one();

        // 查询结果返回列表
        // PageList<UserModel> userModelPageList
        // =crudService.execute().sql(UserModel.class,"sql语句").list(-1,-1);

        // 查询结果返回json数组的
        // JSONArray jsonArray = crudService.execute().sql("sql语句").asJsonObject(-1,
        // -1);

        // 执行操作，不返回查询结果的
        // crudService.execute().sql("UPDATE crip_user t set t.user_name=
        // ROUND(rand()*1000 ) where t.id= '1'").invoke();

        // crudService.execute().sql("CREATE TABLE `INDICATOR_TABLE_RECORD4`
        // (`ID` varchar(2) NOT NULL COMMENT 'ID', `NAME` varchar(2) NOT NULL
        // COMMENT '指标名称') ").invoke();
        // transactionSet.forEach(Transaction::commit);
        //UserModel userModel= crudService.execute()
        transactionSet.forEach(Transaction::beginTransaction);

        userModel = crudService.query().from(UserModel.class).join(Student.class, JoinType.Inner)
                .join(School.class, JoinType.Inner)
                .where("id", Criterion.Equals, "1").one();

        ModelHelper.fromJson(ModelHelper.toJson(userModel).toString(), UserModel.class);

        crudService.sql("select * fRom crip_user t where  t.id= ?").asJson("1");

        userModel = new UserModel();

        for (int i = 1140; i < 1150; i++) {
            userModel = new UserModel();
            userModel.setPassword("1");
            userModel.setUserName("john_" + i);
            School school = new School();
            school.setId("1");
            //userModel.setStudent(school);
            crudService.save(userModel);
        }

        Map<String, Criterion> map = new LinkedHashMap<>();
        map.put("id", Criterion.Like);
        map.put("userName", Criterion.Like);

        List<Object> args = new ArrayList<>();
        args.add("8a80811");
        args.add("jack_3");

        /*crudService.findById(UserModel.class, "1");
        *//*
        * 1、会做数据重复性检查，只需在需要判断重复性字上增加注解：@Unique
        * 2、能进行级联保存操作
        * 3、根据ID值是否为空进行更新或插入判断
        * 4、全字段更新或插入
        * *//*
        crudService.save(userModel);
        *//*
           1、局部字段更新
           2、只能意表进行更新
        * *//*
        crudService.update(userModel);
        crudService.map(UserModel.class).put("id", Criterion.Like)
                .put("userName", Criterion.Like).list("8a80811", "jack_3");
        crudService.query().select("userName").from(UserModel.class)
                .to(UserResultModel.class).where("id", "1").one();
        crudService.query().from(UserModel.class).where("userName", Criterion.Like, "john")
                .where("userName", Criterion.Like, "lily")
                .where("(and id like ? and remark like ?)")
                .group("userName").order("sort").paging(1, 10)
                .list("1", "a");
        crudService.execute().update(UserModel.class)
                .set("userName", "jackson_10000")
                .set("password", "%1ty1")
                .where("id", "1").invoke();
        //简易的多表关联，无须表明on条件
        crudService.query().from(UserModel.class).join(School.class, JoinType.Inner).getSql();*/

        crudService.deleteById(UserModel.class, "1");
        //将id值为2的数据彻底删除
        crudService.execute().remove(UserModel.class, "2");

        PageList<UserModel> tt = crudService.map(UserModel.class).put("id", Criterion.Like)
                .put("userName", Criterion.Like, "8a80811", "jack_3").list();
        PageList<UserModel> r1 = null;
        //String sql = crudService.query().from(UserModel.class).join(School.class, JoinType.Inner).getSql();
        //PageList<UserResultModel> re = crudService.query().list();
        PageList<UserModel> re1 = crudService.query().from(UserModel.class)
                .where("userName", Criterion.In, "jack_28,jack_32").paging(1, 10).list();
        //将有效无效的数据都能查询出来
        QueryProxy queryProxy = crudService.query().from(UserModel.class).to(UserResultModel.class)
                .where("id", Criterion.Like, "8a80811")
                .where("userName", Criterion.Like, "%", Operator.And)
                .where("userName", Criterion.Like, "_")
                .order("id", OrderBy.Asc);

        crudService.sql(queryProxy.getSql()).list(queryProxy.getArgs());


        //crudService.sql("select from # where").asJsonObject("aaa");
        // crudService.delete(UserModel.class,crudService.query().where())

        UserResultModel userResultModel = crudService.query().select("id,userName").from(UserModel.class).to(UserResultModel.class)
                .where(() -> "(id like ? and userName like ?)", new Object[]{"%8a80811%", "%jack_3%"})
                .order("id", OrderBy.Asc).one();

        //crudService.execute().recycle(UserModel.class).where("password", "c").invoke();
        transactionSet.forEach(Transaction::commit);
        cloneableSet.forEach(Closable::close);

    }

    @Test
    public void testHibernate() {
        /*HibernateOrmImpl hibernateOrm = new HibernateOrmImpl();
        Session session = BeanFactory.getBean(SessionManage.class).get(Mode.Write, "");
        HibernateQuery hibernateQuery = new HibernateQuery();
        hibernateQuery.from(UserModel.class).to(UserResultModel.class)
                .where("id", Criterion.Like, "8a80811")
                .where("userName", Criterion.Like, "_");
        Query query = session.createQuery(hibernateOrm.replaceArgs(hibernateOrm.getQuerySql(hibernateQuery, false)));
        hibernateOrm.setParameters(hibernateQuery, query);
        List<UserModel> userModels1 = query.list();
        session.disconnect();


        hibernateQuery = new HibernateQuery();
        hibernateQuery.select("userName").from(UserModel.class)
                .to(UserResultModel.class).where("id", "1");
        query = session.createQuery(hibernateOrm.replaceArgs(hibernateOrm.getQuerySql(hibernateQuery, false)));
        hibernateOrm.setParameters(hibernateQuery, query);
        List<UserModel> userModels2 = query.list();*/


    }

}
