package com.chinatelecom.knowledgebase.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Denny
 * @Date 2024/4/2 9:50
 * @Description
 * @Version 1.0
 */
public class FileNameUtils
{
    //处理得到不重复文件名的逻辑

    public static String generateUniqueFileName(String originalFileName) {
        // 使用SimpleDateFormat获取当前时间作为文件名的一部分，防止重名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS");
        String timestamp = formatter.format(new Date());

        // 拼接原文件名与时间戳，可能需要处理特殊字符或非法文件名
        String extension = getExtension(originalFileName); // 获取文件扩展名
        return timestamp + "_" + originalFileName.replaceFirst("\\." + extension + "$", "") + "." + extension;
    }

    //获取文件扩展名。就是.后面的类型名。
    public static String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return ""; // 没有扩展名
        }
        return fileName.substring(lastDotIndex + 1);
    }
}
