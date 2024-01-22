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
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoImpl videoImpl;

    @Autowired
    CommentImpl commentImpl;
    //获取全部或者搜索条件下的视频
    @GetMapping("/some")
    public R<Page<VideoDTO>> getVideos(
            @RequestParam(name = "page", required = true, defaultValue = "1") int page,
            @RequestParam(name = "pageSize", required = true, defaultValue = "6") int pageSize,
            @RequestParam(name="queryName",required = false) String queryName
    ){
        Page<VideoDTO> videos = videoImpl.getVideos(page, pageSize, queryName);
        //理论上不会有失败的数据
        return R.success(videos,"成功传输video分页数据");

    }
    //获取单个视频
    @GetMapping()
    public R<List> getOneVideo(
            @RequestParam(name = "id",required = true) int id
    ){
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Video one = videoImpl.getOne(queryWrapper);
        ArrayList<Object> res = new ArrayList<>();
        res.add(one);
        List<CommentDTO> commentsList = commentImpl.getComments(one.getId(), "static/video");
        res.add(commentsList);
        return R.success(res,"成功传输视频和其的评论");


    }
}
