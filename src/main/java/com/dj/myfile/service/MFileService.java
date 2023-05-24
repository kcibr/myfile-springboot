package com.dj.myfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.myfile.entity.MFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;


public interface MFileService extends IService<MFile> {

    String CreateNewFolder(String folderName, String parentFolderPath, String fileGroup);
    /**
     *
     * @author kcibr
     * @date 2023/4/24 18:55
     * @Description //上传文件
     * @param file
     * @param parentFolderPath
     * @param fileGroup
     * @return String
     */
    String uploadFile(MultipartFile file, String parentFolderPath, String fileGroup) throws IOException;
    MFile download(int fid, OutputStream os)throws IOException;
}
