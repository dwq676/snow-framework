package com.zoe.snow.phoenix.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PhoenixClient
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/3/23
 */
public class PhoenixClient {
    static {
        /*try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) throws IOException, SQLException, InterruptedException {
        //Connection cc = null;
        //final String url = "jdbc:phoenix:" + "10.8.208.104" + ":" + 50000;
        String classPath = PhoenixClient.class.getResource("/").getPath().substring(1);
        //System.setProperty("hadoop.home.dir", classPath + "hadoop");
        System.out.println(classPath + "krb5.conf");
        System.setProperty("java.security.krb5.conf", classPath + "krb5.conf");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hadoop.security.authentication", "Kerberos");
        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
        // 这样我们就不需要交互式输入密码了
        conf.set("keytab.file", classPath + "hbase.keytab");
        //conf.set("hbase.zookeeper.quorum", "s4");
        //conf.set("hbase.zookeeper.property.clientPort", "50000");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation ugi = null;
        try {
            UserGroupInformation.loginUserFromKeytab("hbase/s4", classPath + "hbase.keytab");
            //ugi = UserGroupInformation.getCurrentUser();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        HTable table = new HTable(conf, "rdc_rawdata_mping");
        System.out.println("tablename:" + new String(table.getTableName()));
        Scan s = new Scan();
        ResultScanner scanner = table.getScanner(s);

        final int MAX_VERSIONS = 2;
        Get get1 = new Get(Bytes.toBytes("46.31.178.2_207.226.247.1"));
        Get get2 = new Get(Bytes.toBytes("180.180.48.1_70.39.191.245"));
        List<Get> gets = new ArrayList<>();
        gets.add(get1);
        gets.add(get2);


        get1.setMaxVersions(MAX_VERSIONS);

        Result[] results = table.get(gets);
        for (Result result : results) {
            List<Cell> values = result.getColumnCells(Bytes.toBytes("raw"), Bytes.toBytes("dat"));
            for (Cell cell : values) {
                System.out.println(Bytes.toString(CellUtil.cloneValue(cell)) + "####" + Bytes.toString(result.getRow()));
            }
        }
        /*for (Result r : scanner) {
            System.out.println(r.toString());
            KeyValue[] kv = r.raw();
            for (int i = 0; i < kv.length; i++) {
                System.out.print(new String(kv[i].getRow()) + "");
                System.out.print(new String(kv[i].getFamily()) + ":");
                System.out.print(new String(kv[i].getQualifier()) + "");
                System.out.print(kv[i].getTimestamp() + "");
                System.out.println(new String(kv[i].getValue()));
            }
        }*/

        //Connection connection = DriverManager.getConnection(url, "root", "router");
        /*Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PreparedStatement ps = null;


        try {
            // Connect to the database
            connection = ugi.doAs(new PrivilegedExceptionAction<Connection>() {
                public Connection run() {
                    Connection tcon = null;
                    try {
                        Properties props = new Properties();
                        // 设置PhoenixRuntime.CURRENT_SCN_ATTRIB的值
                        props.setProperty(PhoenixRuntime.CURRENT_SCN_ATTRIB, Long.toString(1491039724990L));
                        tcon = DriverManager.getConnection(url, props);
                    } catch (SQLException e) {
                    }
                    return tcon;
                }
            });

            // Query for table
            ps = connection.prepareStatement("select * from \"rdc_rawdata_mping\"");
            rs = ps.executeQuery();
            System.out.println("Table Values");
            while (rs.next()) {
                String myKey = rs.getString(2);

                System.out.println("\tRow: " + myKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }*/
    }

}
