package com.chinatelecom.knowledgebase.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinatelecom.knowledgebase.DTO.CommentDTO;
import com.chinatelecom.knowledgebase.DTO.VideoDTO;
import com.chinatelecom.knowledgebase.common.R;
import com.chinatelecom.knowledgebase.entity.Video;
import com.chinatelecom.knowledgebase.service.impl.CommentImpl;
import com.chinatelecom.knowledgebase.service.impl.VideoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(name="queryName",required = false) String queryName
    ){
        Page<VideoDTO> videos = videoImpl.getVideos(page, pageSize, queryName);
        //理论上不会有失败的数据

        return R.success(videos,"成功传输video分页数据");

    }
    //获取单个视频
    @GetMapping()
    public R<VideoDTO> getOneVideo(
            @RequestParam(name = "id",required = true) int id,
            @RequestParam(name = "userId") int loginUserId
    ){

        //ArrayList<Object> res = new ArrayList<>();

        //video本身的数据
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Video one = videoImpl.getOne(queryWrapper);
        VideoDTO oneVideoDTO = videoImpl.getOneVideoDTO(one);
        return  R.success(oneVideoDTO,"成功传输视频");

        //

/*        res.add(oneVideoDTO);

        //该video的评论的数据
        List<CommentDTO> commentsList = commentImpl.getComments(one.getId(), "video",loginUserId);
        res.add(commentsList);

        return R.success(res,"成功传输视频和其的评论");*/


    }
}
