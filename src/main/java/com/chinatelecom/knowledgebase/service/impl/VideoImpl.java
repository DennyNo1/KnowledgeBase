package com.chinatelecom.knowledgebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinatelecom.knowledgebase.DTO.VideoDTO;
import com.chinatelecom.knowledgebase.entity.User;
import com.chinatelecom.knowledgebase.entity.Video;
import com.chinatelecom.knowledgebase.mapper.VideoMapper;
import com.chinatelecom.knowledgebase.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Denny
 * @Date 2024/1/17 11:00
 * @Description 获取全部或者搜索条件下相关的video的数据
 * @Version 1.0
 */
@Service
public class VideoImpl extends ServiceImpl<VideoMapper, Video> implements VideoService
{
    @Autowired
    UserImpl userImpl;
    //第几页，每页有几个，搜索名
    public Page<VideoDTO> getVideos(int page,int pageSize,String queryName,String type)
    {
        Page<Video> videoPage = new Page<>(page, pageSize);//相当于limit语句。此时还未开始查询
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();//条件查询，相当与where语句
        if(queryName!=null){
            videoQueryWrapper.like("title",queryName);
        }
        if(type!=null){
            videoQueryWrapper.eq("type",type);
        }
        this.page(videoPage,videoQueryWrapper);//这个应该就是查询了，并把结果放在videoPage
        //还需要把Page里的List<Video>替换成List<VideoDto>

        Page<VideoDTO> videoDTOPage=new Page<>(page,pageSize);
        //除了records全部复制
        BeanUtils.copyProperties(videoPage,videoDTOPage,"records");
        //List<VideoDto>
        List<VideoDTO> list=new ArrayList<>();

        //遍历每个List<Video>的元素.List<Video>的名字叫records.
        for (Video video:videoPage.getRecords())
        {
            VideoDTO oneVideoDTO = this.getOneVideoDTO(video);
            list.add(oneVideoDTO);
        }
        videoDTOPage.setRecords(list);
        return videoDTOPage;

    }

    //通过一个video记录，去查这条记录有关的user记录，并组合成一个集合。
    public VideoDTO getOneVideoDTO(Video video){
        int uploaderId = video.getUploaderId();
        User user=userImpl.getOneUser(uploaderId);
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setVideo(video);
        videoDTO.setDepartment(user.getDepartment());
        videoDTO.setNickName(user.getNickName());
        videoDTO.setAvatar(user.getAvatar());
        videoDTO.setRole(user.getRole());
        return videoDTO;
    }
}
