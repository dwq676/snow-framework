package com.zoe.snow.word.file;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.zoe.snow.word.WordFrequencyStatistics;
import com.zoe.snow.word.segmentation.Word;

/**
 *  将切词结果显示到文件中
 *
 * @author Zhang Chunrong
 * @date 2016/9/12.
 */
public class WordFile {

    //数据库的字段信息number_id
    static int numberId;

    public static void main(String args[]) {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象
        //文件读取
        File file=new File("D:/word/ZEMR_EMR_CONTENT_DYYY.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String driverName="oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);// 加载Oracle驱动程序
            System.out.println("开始尝试连接数据库！");
            String url = "jdbc:oracle:" + "thin:@10.0.1.198:1521/orcl";// 数据库地址与数据库名
            String user = "mip";// 数据库用户名
            String password = "mip";// 数据库密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("连接成功！");
            String sql = "select * from ZEMR_EMR_CONTENT_DYYY t where t.tmp_len>0";// 预编译语句
            pre = con.prepareStatement(sql);// 实例化预编译语句
            result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
            while (result.next()){
                //获取数据库中的疾病信息
                String diseaseDescribe =  result.getString("current_disease");
                if(diseaseDescribe==null){
                    continue;
                }
                //序号ID
                int numberId=result.getInt("number_id");
                WordFile wf=new WordFile();
                //设置numberId
                wf.setNumberId(numberId);
                /* 拼接数据，将输出到txt文件中 */
                String appendData="";
                //对结果数据进行分词
                List<Word> words= WordFrequencyStatistics.matching(diseaseDescribe);
                if(words.size()==0){
                    continue;
                }
                //组装结果并显示到txt文件中
                String symptomItem="";
                for(int j=0;j<words.size();j++){
                    int _j=j+1;
                    if(j==words.size()-1){
                        symptomItem+=numberId+" "+_j+" "+words.get(j).getText();
                    }else{
                        symptomItem+=numberId+" "+_j+" "+words.get(j).getText()+"\n";
                    }
                }
                //拼接输出的数据
                appendData=symptomItem;
                //将数据输出到文件中
                FileWriter fileWriter=new FileWriter(file,true);
                BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                bufferedWriter.write(appendData+"\n");
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("数据库连接已关闭！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }
}
