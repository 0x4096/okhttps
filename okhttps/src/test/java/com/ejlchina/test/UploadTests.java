package com.ejlchina.test;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.Process;
import org.junit.Test;

public class UploadTests extends BaseTest {

    @Test
    public void testUpload() {
        String data = "0123456789abcdefghijklmnopqrstuvwsyz0123456789abcdefghijklmnopqrstuvwsyz0123456789abcdefghijklmnopqrstuvwsyz0123456789abcdefghijklmnopqrstuvwsyz0123456789abcdefghijklmnopqrstuvwsyz";

        HTTP http = HTTP.builder().build();

        long t0 = System.currentTimeMillis();

        String res = http.sync("http://localhost:8080/test/index")
                .addBodyPara("data", data)
                .addFilePara("file", "D:\\WorkSpace\\download\\CocosDashboard-v1.0.1-win32-031816.exe")
                .stepRate(0.01)
                .setOnProcess((Process process) -> {
                    println(t0, "上传：" + process.getDoneBytes() + "/" + process.getTotalBytes() + "\t" + process.getRate());
                })
                .post()
                .getBody()
                .stepBytes(5)
                .setOnProcess((Process process) -> {
                    println(t0, "下载：" + process.getDoneBytes() + "/" + process.getTotalBytes() + "\t" + process.getRate());
                })
                .toString();

        println("响应：" + res);
    }



    @Test
    public void test() {
        HttpResult res = OkHttps.sync("http://305cb8a0.cpolar.io/biz/location/impor")
            .addBodyPara("type", "MACHINE")
            .addFilePara("excel", "H:\\课程.xlsx")
            .stepBytes(1024)
            .setOnProcess((process -> {
                long doneBytes = process.getDoneBytes();   // 已发送字节数
                println("已发送字节数: " + doneBytes);
                long totalBytes = process.getTotalBytes(); // 总共的字节数
                println("总字节数: " + totalBytes);
                double rate = process.getRate();           // 已发送的比例
                println("已发送的比例: " + rate);
                boolean isDone = process.isDone();
                println("是否发送完成: " + isDone);
            }))
            .post();
        System.out.println(res);
        System.out.println(res.getBody().toString());
    }

}
