package com.chinatelecom.knowledgebase.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.chinatelecom.knowledgebase.util.FileNameUtils.generateUniqueFileName;

/**
 * @Author Denny
 * @Date 2024/4/2 10:07
 * @Description
 * @Version 1.0
 */
public class UploadFileUtils
{
    public  static Map<String, Object> upload( MultipartFile file,int UPLOAD_FAILURE_ERRNO,int UPLOAD_SUCCESS_ERRNO,String uploadPath,String accessPath) {
        if (file.isEmpty()) {
            Map<String, Object> errorResponseData = new HashMap<>();
            errorResponseData.put("errno", UPLOAD_FAILURE_ERRNO);
            errorResponseData.put("message", "文件为空，上传失败");
            return errorResponseData;
//            return ResponseEntity.badRequest().body(errorResponseData);
        }

        // 生成唯一文件名（可结合当前时间防止重名）
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        String filePath = uploadPath + File.separator + uniqueFileName;

        try {
            // 保证上传目录存在
            Files.createDirectories(Paths.get(uploadPath));

            // 将文件保存到指定位置
            Path targetLocation = Paths.get(filePath);
            Files.copy(file.getInputStream(), targetLocation);

            // 构建响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("errno", UPLOAD_SUCCESS_ERRNO);
            Map<String,String> data=new HashMap<>();
            data.put("url",accessPath+uniqueFileName);
            data.put("alt",uniqueFileName);
            data.put("href",accessPath+uniqueFileName);
            responseData.put("data",data);
            return responseData;
//            return ResponseEntity.ok(responseData);

        } catch (IOException e) {
            Map<String, Object> errorResponseData = new HashMap<>();
            errorResponseData.put("errno", UPLOAD_FAILURE_ERRNO);
            errorResponseData.put("message", e.getMessage());
            return errorResponseData;
//            return ResponseEntity.badRequest().body(errorResponseData);
        }
    }
}
