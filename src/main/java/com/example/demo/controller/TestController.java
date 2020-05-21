package com.example.demo.controller;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 */
@Slf4j
public class TestController {
    /**
     * 获取视频文件时长
     */
    private static long getVideoTime(String pathName) {
        try {
            File file = new File(pathName);
            Encoder encoder = new Encoder();
            MultimediaInfo multimediaInfo = encoder.getInfo(file);
            return multimediaInfo.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 远程文件(视频/图片)存储
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static long insertBlobRemote(String url) throws Exception {
        String ext = url.substring(url.lastIndexOf("."));
        //获取文件
        InputStream inputStream = getImgFromUrl(url);
        //获取时长
        return inputStreamToFile(inputStream, ext);
    }

    /**
     * 获取文件
     *
     * @param url
     * @return
     * @throws IOException
     */
    private static InputStream getImgFromUrl(String url) throws IOException {
        URL myUrl = new URL(url);
        HttpURLConnection con = (HttpURLConnection) myUrl.openConnection();
        con.setConnectTimeout(5 * 1000);
        return con.getInputStream();
    }

    /**
     * 读取文件信息返回时长
     *
     * @param ins
     * @param ext
     * @return
     * @throws IOException
     */
    private static long inputStreamToFile(InputStream ins, String ext) throws IOException {
        File file = new File("D:/Temp");
        if (file.mkdirs()) {
            if (file.mkdir()) {
                log.info("创建临时文件夹成功");
            }
        }
        File newFile = File.createTempFile("temp", ext, file);
        String path = newFile.getPath();
        OutputStream os = new FileOutputStream(newFile);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        long videoTime = getVideoTime(path);
        if (videoTime > 0) {
            if (newFile.delete()) {
                log.info("清理临时文件成功");
            }
        }
        return videoTime;
    }

    public static void main(String[] args) {
        try {
            long l = insertBlobRemote("http://cloud.video.taobao.com/play/u/7/p/1/e/6/t/1/d/hd/232135220612.mp4");
            System.out.println("此视频时长为:" + l + "毫秒！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
