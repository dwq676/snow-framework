package com.zoe.snow.test.support;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;

/**
 * ElasticsearchImpl
 *
 * @author Dai Wenqing
 * @date 2016/9/13
 */
public class ElasticsearchImpl   {

    public static boolean put1(String json) {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "snow")
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        try {

            client
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.0.2.54"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.0.2.7"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.110.136"), 9300));
        } catch (Exception e) {

        }




        for (int i = 0; i < 100000000; i++) {
            json = "";


            IndexResponse response = client.prepareIndex("mip", "info")
                    //.setId(new String().valueOf(new Random().nextLong()))
                    .setSource(json)
                    .execute().actionGet();

            System.out.println(new Date().toString() + ":" + response.isCreated() + " num:" + i);
        }

        client.close();

        return false;
    }

    private static Client getClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "snow")
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        try {
            client
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.0.2.54"), 9300))
                    //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.0.2.7"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.110.136"), 9300));
        } catch (Exception e) {

        }
        return client;
    }


    public static boolean export() {
        Client client = getClient();
        String dirPath = "/tmp/data/seqs";
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();
        try {
            for (File file : files) {
                String locus = file.getName();
                File[] sourceFile = file.listFiles();
                String filePath = dirPath + "/" + locus + "/"
                        + sourceFile[0].getName();
                File _sourceFile = new File(filePath);
                FileInputStream fileInputStream = new FileInputStream(_sourceFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        fileInputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                // 循环获取并且拼接json
                while ((line = reader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                client.prepareIndex("test", "seqs", locus)
                        .setSource(stringBuffer.toString()).execute().actionGet();
                System.out.println(locus);
                fileInputStream.close();
                reader.close();
            }
        } catch (Exception e) {

        }
        return true;
    }

    public static void main(String args[]) {
        long start = System.currentTimeMillis();
        put1("");
        //export();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
