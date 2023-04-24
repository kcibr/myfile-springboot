package com.dj.myfile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.MFile;
import com.dj.myfile.entity.User;
import com.dj.myfile.service.MFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName FileController
 * @Description //TODO
 * @Date 2023/4/24 16:07
 * @Author kcibr
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    final MFileService mFileService;

    public FileController(MFileService mFileService){
        this.mFileService = mFileService;
    }
    /**
     *
     * @author kcibr
     * @date 2023/4/24 18:32
     * @Description //查询用户全部文件
     * @param parentDir
     * @param search
     * @return Object
     */
    @GetMapping("/queryAll")
    public Object queryFileList(String parentDir,String search){
        if (search == null){
            search = "";
        }
        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_dir", parentDir)
                .like("f_name",search);
        System.out.println("调用了查询接口");
    return mFileService.list(wrapper);
    }
    /**
     *
     * @author kcibr
     * @date 2023/4/24 19:10
     * @Description //新建文件夹
     * @param folderName
     * @param parentFolderPath
     * @param fileGroup
     * @return String
     */
    @PostMapping("/createNewFolder")
    public String NewFolder(String folderName, String parentFolderPath, String fileGroup){
       return mFileService.CreateNewFolder(folderName,parentFolderPath,fileGroup);
    }

    @PostMapping("/uploadFile")
    public String uploadFiles(List<MultipartFile> files ,String uploadFolderPath,String fileGroup){
        for (MultipartFile file : files){
            try {
                mFileService.uploadFile(file,uploadFolderPath,fileGroup);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "上传完成";
    }

}
