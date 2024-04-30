package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.ArticleDTO;
import com.chinatelecom.knowledgebase.DTO.ArticleListDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Article;
import com.chinatelecom.knowledgebase.entity.Attachment;
import com.chinatelecom.knowledgebase.entity.UserLike;
import com.chinatelecom.knowledgebase.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.chinatelecom.knowledgebase.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chinatelecom.knowledgebase.util.FileNameUtils.generateUniqueFileName;

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
            @RequestParam(name="type",required = false) String type,
            @RequestParam(name = "uploaderId",required = false) String uploaderId //因为传过来可能是空值，所以用String

    ){
        //对默认进行特殊处理

        if(type.equals("默认")) type=null;
        Page<ArticleListDTO> articleList = articleImpl.getArticleList(page, pageSize, queryName,type,uploaderId);
        //理论上不会有失败的数据
        return R.success(articleList,"成功传输article分页数据");

    }

@Autowired
UserLikeImpl userLikeImpl;
//获取谋篇文章详情页

    @GetMapping()
    public R<ArticleDTO> getArticle(
            @RequestParam(name = "articleId",required = true) int articleId,
            @RequestParam(name = "userId",required = false) String userId//因为有可能是空值。传过来的string”“,没法转换成int的某个值。所以直接用string对接string。

    ){
        //查询到一篇文章的记录
        ArticleDTO articleDTO = articleImpl.getArticleDTO(articleId);


        if(userId!=null&&!userId.equals(""))//判断userId存在，并且不为空
        {
            int loginUserId=Integer.valueOf(userId);
                boolean likeRes = userLikeImpl.isLikeOrNot("article", articleDTO.getArticleListDTO().getArticle().getId(), loginUserId);
            articleDTO.setLiked(likeRes);

                    }
        else articleDTO.setLiked(false);



        return R.success(articleDTO,"成功传输课件");

    }

    @PostMapping("/add")
    public R addArticle(@RequestBody Article article)
    {
        if(article.getType()==null||article.getTitle()==null)
            return R.error("上传失败，请输入文章的标题、类型");
        boolean saveRes = articleImpl.save(article);
        if (saveRes) {
            Integer id = article.getId();
            return R.success(id, "上传成功");
        } else {
            throw new RuntimeException("上传失败，可能是由于数据库操作异常");
        }
    }
    @PostMapping("/update")
    public R updateArticle(@RequestBody Article article){
        try{
            articleImpl.updateById(article);
            return R.success(null,"更新文章成功");
        }
        catch (Exception e){
            return R.error(e.getMessage());
        }
    }


//    @PostMapping("/add-image")
//    public R addImage



    //这是图片存放在服务器上的真实路径
    @Value("${upload.image.path}")
    private String imageUploadPath;//图片上传的存放路径，目前暂存在项目的windows路径下
    @Value("${upload.video.path}")
    private String videoUploadPath;
    private static final int UPLOAD_SUCCESS_ERRNO = 0;
    private static final int UPLOAD_FAILURE_ERRNO = 1;

    //这是图片存放在服务器上的虚拟路径
    private  String imageAccessPath="http://localhost:8088/images/";
    private String videoAccessPath="http://localhost:8088/videos/";

    //实际能接收多个文件，假设每次只传一张图片
    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, Object> upload = UploadFileUtils.upload(file, UPLOAD_FAILURE_ERRNO, UPLOAD_SUCCESS_ERRNO, imageUploadPath, imageAccessPath);
        if(upload.get("errno").equals(UPLOAD_FAILURE_ERRNO)) return ResponseEntity.badRequest().body(upload);
        else return ResponseEntity.ok(upload);
    }
    @PostMapping("/upload-video")
    public ResponseEntity<Map<String, Object>> uploadVideo(@RequestParam("video") MultipartFile file) {
        Map<String, Object> upload = UploadFileUtils.upload(file, UPLOAD_FAILURE_ERRNO, UPLOAD_SUCCESS_ERRNO, videoUploadPath, videoAccessPath);
        if(upload.get("errno").equals(UPLOAD_FAILURE_ERRNO)) return ResponseEntity.badRequest().body(upload);
        else return ResponseEntity.ok(upload);
    }










    @Value("${upload.attachment.path}")
    private String attachmentUploadPath;
    private  String attachmentAccessPath="http://localhost:8088/attachments/";

    //多个附件上传的接口
    @PostMapping("/upload-attachment")
    public ResponseEntity<List<Map<String, Object>>> uploadAttachment(@RequestParam("file") List<MultipartFile> files) {
        List<Map<String, Object>> allResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<String, Object> upload = UploadFileUtils.upload(file, UPLOAD_FAILURE_ERRNO, UPLOAD_SUCCESS_ERRNO,attachmentUploadPath, attachmentAccessPath);
            allResponses.add(upload);
        }

        // 返回包含所有文件上传结果的响应
        return ResponseEntity.ok(allResponses);

    }

    @Autowired
    AttachmentImpl attachmentImpl;
    @PostMapping("/save_attachment")
    public R saveAttachment(@RequestBody Attachment attachment){
        boolean save = attachmentImpl.save(attachment);
        if(save)
            return R.success(null,"上传附件成功");
        else return R.error("上传附件失败");
    }

    //删除附件记录
    @PostMapping("/delete_attachment")
    public R deleteAttachment(@RequestBody Attachment attachment){
        try{
            Integer articleId = attachment.getArticleId();
            QueryWrapper<Attachment> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("article_id",articleId);
            attachmentImpl.remove(queryWrapper);
            return R.success(null,"删除该文件的所有附件记录成功");
        }
        catch (Exception e){
            return R.error(e.getMessage());
        }
    }



//    修改置顶度
@PostMapping("/top")
   public R modifyTop(@RequestBody Article article){
        //会报错的点，只能是查不到该article对象
    Article byId = articleImpl.getById(article.getId());
    byId.setTop(article.getTop());
    articleImpl.updateById(byId);
    return R.success(null,"修改置顶度成功");
}




}
