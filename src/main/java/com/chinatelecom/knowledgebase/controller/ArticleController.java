package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.*;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.common.RofImg;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.service.impl.ArticleImpl;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Denny
 * @Date 2024/1/17 15:11
 * @Description 注释同样去看VideoController，思路一模一样
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleImpl articleImpl;

    @Autowired
    CommentImpl commentImpl;

    @Autowired
    UserImpl userImpl;

    //获取文章列表
    @GetMapping("/some")
    public R<Page<ArticleListDTO>> getArticleList(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName,
            @RequestParam(name="type",required = false) String type
    ){
        Page<ArticleListDTO> articleList = articleImpl.getArticleList(page, pageSize, queryName,type);
        //理论上不会有失败的数据
        return R.success(articleList,"成功传输article分页数据");

    }


    //
    @GetMapping()
    public R<ArticleDTO> getArticle(
            @RequestParam(name = "articleId",required = true) int articleId

    ){
        //查询到一篇文章的记录
        ArticleDTO articleDTO = articleImpl.getArticleDTO(articleId);


        return R.success(articleDTO,"成功传输课件");

    }

    @PostMapping("/add")
    public R addArticle(@RequestBody Article article)
    {
        if(article.getContent()==null||article.getTitle()==null)
            return R.error("上传失败，请输入文章的标题、内容");
        boolean saveRes = articleImpl.save(article);
        if (saveRes) {
            return R.success(null, "上传成功");
        } else {
            throw new RuntimeException("上传失败，可能是由于数据库操作异常");
        }
    }


//    @PostMapping("/add-image")
//    public R addImage



    @Value("${upload.image.directory}")
    private String uploadPath;//图片上传的存放路径，目前暂存在项目的windows路径下
    private static final int UPLOAD_SUCCESS_ERRNO = 0;
    private static final int UPLOAD_FAILURE_ERRNO = 1;

    private static final String VIDEO_DIRECTORY="http://localhost:8088/images/";

    //假设每次只传一张图片
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            Map<String, Object> errorResponseData = new HashMap<>();
            errorResponseData.put("errno", UPLOAD_FAILURE_ERRNO);
            errorResponseData.put("message", "文件为空，上传失败");
            return ResponseEntity.badRequest().body(errorResponseData);
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
            data.put("url",VIDEO_DIRECTORY+uniqueFileName);
            data.put("alt",uniqueFileName);
            data.put("href",VIDEO_DIRECTORY+uniqueFileName);
            responseData.put("data",data);
            return ResponseEntity.ok(responseData);

        } catch (IOException e) {
            Map<String, Object> errorResponseData = new HashMap<>();
            errorResponseData.put("errno", UPLOAD_FAILURE_ERRNO);
            errorResponseData.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponseData);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        // 使用SimpleDateFormat获取当前时间作为文件名的一部分，防止重名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SSS");
        String timestamp = formatter.format(new Date());

        // 拼接原文件名与时间戳，可能需要处理特殊字符或非法文件名
        String extension = getExtension(originalFileName); // 获取文件扩展名
        return timestamp + "_" + originalFileName.replaceFirst("\\." + extension + "$", "") + "." + extension;
    }

    //获取文件扩展名。就是.后面的类型名。
    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return ""; // 没有扩展名
        }
        return fileName.substring(lastDotIndex + 1);
    }


}
