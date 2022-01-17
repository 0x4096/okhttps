package com.ejlchina.okhttps;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 下载助手，用户解析下载路径与文件名
 */
public class DownloadHelper {

    /**
     * 解析下载文件名
     * @param result HTTP 响应结果
     * @return 文件名
     */
    public String resolveFileName(HttpResult result) {
        String fileName = result.getHeader("Content-Disposition");
        // 通过 Content-Disposition 获取文件名，这点跟服务器有关，需要灵活变通
        if (fileName == null || fileName.length() < 1) {
            fileName = result.getTask().getUrl();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        } else {
            fileName = URLDecoder.decode(fileName.substring(
                    fileName.indexOf("filename=") + 9), StandardCharsets.UTF_8);
            // 有些文件名会被包含在""里面，所以要去掉，不然无法读取文件后缀
            fileName = fileName.replaceAll("\"", "");
        }
        return fileName;
    }

    /**
     * 解析文件路径
     * @param dirPath 目录
     * @param fileName 文件名
     * @return 文件路径
     */
    public String resolveFilePath(String dirPath, String fileName) {
        if (dirPath.endsWith("\\") || dirPath.endsWith("/")) {
            return dirPath + fileName;
        }
        return dirPath + File.separator + fileName;
    }

    /**
     * 当文件已存在时，根据该方法生成一个新的文件名
     * @param fileName 原文件名
     * @param index 重复次数
     * @return 新的文件名
     */
    public String indexFileName(String fileName, int index) {
        int i = fileName.lastIndexOf('.');
        if (i < 0) {
            return fileName + "(" + index + ")";
        }
        String ext = fileName.substring(i);
        if (i > 0) {
            String name = fileName.substring(0, i);
            return name + "(" + index + ")" + ext;
        }
        return "(" + index + ")" + ext;
    }

}
