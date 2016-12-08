package com.zoe.snow.test.support;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * $(NAME)
 *
 * @author Zhang Chunrong
 * @date 2016/9/5.
 */
public class WordFileTest {
    // 数据库的字段信息number_id
    static int numberId;

    public static void main(String args[]) {
        Connection con = null;// 创建一个数据库连接
        PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
        ResultSet result = null;// 创建一个结果集对象

        // 文件读取
        File file = new File("D:/ETL/data/ZEMR_EMR_CONTENT_DYYY.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);// 加载Oracle驱动程序
            System.out.println("开始尝试连接数据库！");
            String url = "jdbc:oracle:" + "thin:@10.0.1.198:1521/orcl";// 数据库地址与数据库名
            String user = "zemr";// 数据库用户名
            String password = "zemr";// 数据库密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("连接成功！");
            String sql = "select id, patient_id, event_no, catalog_id, title, template_id, emr_type, create_date, creator, creator_id, modify_date, modifior, encryption, compression, sort_code, blob2clob(t.content) as content, xml, keyword, version, status, notes, full_text, modify_count, open_exclusive, content_verification_status, content_verification_time, qc_verification_status, qc_verification_time, t.xml_text.getclobval() xml_text, xml_convert_flag, archive_print_flag, image_flag, content_new, content_flag from ZEMR_EMR_CONTENT t where id='D0647E3D-DE37-4FF7-8799-23CAB02F737A' and  t.content_flag =1 and dbms_lob.getlength(t.content_new) > 0";// 预编译语句
            pre = con.prepareStatement(sql);// 实例化预编译语句
            result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
            int index = 0;

            while (result.next()) {
                if (index > 100)
                    break;

                Blob blob = result.getBlob("content_new");
                byte[] bytes = printHexString(blob.getBytes(1L, (int) blob.length()));

                /*ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
                DataInputStream dis = new DataInputStream(bin);*/

                BufferedOutputStream bos = null;
                FileOutputStream fos = null;
                String filePath = "d:/test1.html";
                try {

                    File dir = new File(filePath);
                    if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                        dir.mkdirs();
                    }
                    file = new File(filePath);
                    fos = new FileOutputStream(file);
                    bos = new BufferedOutputStream(fos);

                    //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                    //fos.
                    bos.write(bytes);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bos != null) {
                        try {
                            bos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }


                String aa = new String(bytes, "GBK");
                System.out.println(aa);
                index++;
                /*CLOB diseaseDescribe = (CLOB) result.getClob("content_new");
                String result66;
                result66 = ClobToString(diseaseDescribe);

                // 设置为GBK
                //String str =
                // 设置为UTF-8
               // System.out.println(str);
                if (diseaseDescribe == null) {
                    continue;
                }
                // 序号ID
                int numberId = result.getInt("number_id");
                WordFileTest wft = new WordFileTest();
                wft.setNumberId(numberId);
                *//* 拼接数据，将输出到txt文件中 *//*
                String appendData = "";
                // 对结果数据进行分词
                // 拼接输出的数据*/
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

    private static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    public static byte[] printHexString(byte[] b) {
        byte[] bytes = new byte[b.length - 2];
        List<Byte> byteList = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (i > 1) {
                bytes[i - 2] = b[i];
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                System.out.print(hex.toUpperCase());
                if (b[i] == 0)
                    continue;
                byteList.add(b[i]);
            }
        }
        /*byte[] lb = new byte[byteList.size()];
        for (int j = 0; j < byteList.size(); j++) {
            lb[j] = byteList.get(j);
        }
        return lb;*/

        return bytes;
    }

    public static int[] getInt(byte[] b) {
        //byte[] bytes = new byte[b.length - 2];
        List<Integer> byteList = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (i > 1) {
                if (b[i] == 0)
                    continue;
                byteList.add((int) b[i]);
            }
        }
        int[] lb = new int[byteList.size()];
        for (int j = 0; j < byteList.size(); j++) {
            lb[j] = byteList.get(j);
        }
        return lb;
    }

    private static byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;
        byte[] bytes = null;
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
            bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;

            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
        // byte[] b = null;
        // try {
        // if (blob != null) {
        // long in = 0;
        // b = blob.getBytes(in, (int) (blob.length()));
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // return b;

    }

    public static String ClobToString(Clob clob) throws SQLException, IOException {

        String reString = "";
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            s.replaceAll("\u0000", "");
            sb.append(s);
            /* for(int i=0;i<s.length();i++){ */
            /*
             * if(s.charAt(i)=='\u0000'){ s.(s.charAt(i),''); }
             */
            /* } */
            s = br.readLine();
        }
        reString = sb.toString();
        reString = reString.replaceAll("\u0000", "");
        return reString;
    }

    public int getNumberId() {
        return numberId;
    }

    public void setNumberId(int numberId) {
        this.numberId = numberId;
    }
}
