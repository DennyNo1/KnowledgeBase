package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.DTO.VideoDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Comment;
import com.chinatelecom.knowledgebase.entity.Video;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.VideoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 15:09
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/video")//这里已经多加了一层路径，所以下面可以直接用id代表videoId等。
public class VideoController {
    @Autowired
    VideoImpl videoImpl;


    //获取全部或者搜索条件下的视频
    @GetMapping("/some")
    public R<Page<VideoDTO>> getVideos(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "12") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName,
            @RequestParam(name="type",required = false) String type
    ){
        Page<VideoDTO> videos = videoImpl.getVideos(page, pageSize, queryName,type);
        //理论上不会有失败的数据

        return R.success(videos,"成功传输video分页数据");

    }
    //获取单个视频
    @GetMapping()
    public R<VideoDTO> getOneVideo(
            @RequestParam(name = "videoId",required = true) int id
            //这个登录用户id是用于点赞的，现在先没用
//            @RequestParam(name = "userId",required = false) int loginUserId
    ){

        //ArrayList<Object> res = new ArrayList<>();

        //video本身的数据
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Video one = videoImpl.getOne(queryWrapper);
        VideoDTO oneVideoDTO = videoImpl.getOneVideoDTO(one);
        return  R.success(oneVideoDTO,"成功传输视频");

    }
    //上传视频的记录，持久化到数据库
    @PostMapping("/add")
    public R addVideo(@RequestBody Video video){
        if(video.getUrl()==null)
            return R.error("上传失败，请输入视频地址");
        boolean saveRes = videoImpl.save(video);
        if(saveRes)
        {
            return R.success(null,"上传视频成功");

        }
        else {
            throw new RuntimeException("上传视频失败，可能是由于数据库操作异常");
        }

    }

    //将前端传过来的视频文件，保存到指定文件夹
    @Value("${upload.video.path}")
    private String videoUploadDirectory;

    @PostMapping("/upload")
    public R uploadVideo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return R.error("文件为空，上传失败");
        }

        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            Path destination = Paths.get(videoUploadDirectory, originalFilename);

            // 确保目标目录存在
            Files.createDirectories(destination.getParent());


            // 检查目标目录是否存在以及目标文件是否已存在
            if (!Files.exists(destination.getParent())) {
                Files.createDirectories(destination.getParent());
            } else if (Files.exists(destination)) {  // 检查文件是否已存在
                return R.error("已存在同名文件，请修改文件名后再上传");
            }

            // 将文件保存到指定目录
            file.transferTo(destination.toFile());

            // 返回成功的响应，这里可以返回文件的访问路径（如果需要的话）
//            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(URI.create("/video/" + originalFilename));
            return R.success(originalFilename,"上传成功");

        } catch (IOException e) {
            return R.error("Failed to store file: " + e.getMessage());
        }
    }



}
