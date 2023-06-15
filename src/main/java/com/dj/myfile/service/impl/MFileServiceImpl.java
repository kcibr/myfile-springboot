package com.dj.myfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.myfile.entity.MFile;
import com.dj.myfile.mapper.MFileMapper;
import com.dj.myfile.service.MFileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class MFileServiceImpl extends ServiceImpl<MFileMapper, MFile> implements MFileService {
    @Value("${rootPath}")
    private String root_path;

    /**
     * 新建文件夹
     * @param folderName
     * @param parentFolderPath
     * @param fileGroup
     * @return
     */
    @Override
    public String CreateNewFolder(String folderName, String parentFolderPath, String fileGroup) {

        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.eq("path",parentFolderPath+"/"+folderName);
        //根据文件路径判断是否已经存在该文件夹，若已存在则list(wrapper).isEmpty()为false
        if (list(wrapper).isEmpty()){
            MFile folder = new MFile();
            folder.setFName(folderName);
            folder.setIsFolder(1);
            folder.setType("folder");
            folder.setFileGroup(fileGroup);
            folder.setSize("---");
            folder.setParentDir(parentFolderPath);
            folder.setPath(parentFolderPath+"/"+folderName);
            if (save(folder)){
                return "新建文件夹成功";
            } else {
                return "新建文件夹失败";
            }
        }else {
            return "已存在相同文件夹";
        }
    }

    /**
     * 文件上传
     * @param file
     * @param parentFolderPath
     * @param fileGroup
     * @return
     * @throws IOException
     */
    @Override
    public String uploadFile(MultipartFile file, String parentFolderPath, String fileGroup) throws IOException {
        // 1.获取文件名
        String filename = file.getOriginalFilename();
        System.out.println("01.文件名==》"+filename);
        if (filename == null){
            filename = "未知文件";
        }
        // 2.获取文件父级目录，并生成文件路径
        String filepath = parentFolderPath+"/"+filename;
        System.out.println("02.文件路径==》"+filepath);
        // 3.获取文件类型
        String filetype = filename.substring(filename.lastIndexOf(".") + 1);
        System.out.println("03.文件类型==》"+filetype);
        // 4.获取文件大小
        double size = file.getSize();
        String fileSize = "";
        /**
         * 自动调整单位（保留2位小数）
         */
        String dec="%.2f"; //两位小数
        int kb=1024;
        int mb=1024*1024;
        int gb=1024*1024*1024;
        if (size >= gb) {
            fileSize = String.format(dec, size / gb) + "GB";
        } else if (size<gb && size>=mb) {
            fileSize = String.format(dec, size / mb) + "MB";
        } else if (size<mb && size>kb) {
            fileSize = String.format(dec, size / kb)+ "KB";
        }else if (size<kb){
            fileSize = String.format(dec, size) + "B";
        }
        System.out.println("04.文件大小==》"+fileSize);
        // 6.上传文件
        // 判断该路径下是否已经存在同名文件，有则中断执行返回信息
        QueryWrapper<MFile> w = new QueryWrapper<>();
        w.eq("path",filepath);
        System.out.println("是否重复上传"+(getOne(w) != null));
        if (getOne(w) != null){
            System.out.println("重复上传");
            return "已存在同名文件："+filename;
        } else {
            //判断文件夹是否存在，不存在则创建一个
            File file1 = new File(root_path+"/"+parentFolderPath);
            if (!file1.exists()) {
                file1.mkdirs();
                System.out.println("文件夹不存在，已创建文件夹");
            }
            try {
                file.transferTo(new File(root_path+filepath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 7.保存信息到数据库并且同时将文件大小加入到父级目录大小中
            MFile mFile = new MFile();
            mFile.setFName(filename);
            mFile.setFileGroup(fileGroup);
            mFile.setPath(filepath);
            mFile.setType(filetype);
            mFile.setParentDir(parentFolderPath);
            mFile.setSize(fileSize);
            mFile.setIsFolder(0);
            save(mFile);
            // TODO 更新文件夹大小
            return "上传成功";
        }

    }
}




