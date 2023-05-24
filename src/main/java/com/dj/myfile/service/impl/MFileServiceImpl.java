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
     *
     * @author kcibr
     * @date 2023/4/24 19:02
     * @Description //新建文件夹
     * @param folderName
     * @param parentFolderPath
     * @param fileGroup
     * @return String
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
     *
     * @author kcibr
     * @date 2023/4/24 18:54
     * @Description //文件上传
     * @param file
     * @param parentFolderPath
     * @param fileGroup
     * @return String
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
        double filesize = file.getSize();
        System.out.println("04.文件大小==》"+filesize);
        // 6.上传文件
        //判断文件夹是否存在，不存在则创建一个
        File file1 = new File(root_path+"/"+parentFolderPath);
        if (!file1.exists()) {
            file1.mkdirs();
            System.out.println("文件夹不存在，已创建文件夹");
        }
        try {
            file.transferTo(new File(root_path+filepath));
            System.out.println("上传成功");
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
        mFile.setSize(filesize);
        mFile.setIsFolder(0);
        save(mFile);
        // TODO 更新文件夹大小
        return "上传成功";
    }
    @Override
    public MFile download(int fid, OutputStream os) throws IOException{
        MFile file = getById(fid);
        //获取文件下载地址
        String filepath=root_path+file.getPath();
        System.out.println("下载地址==》"+filepath);

        //读取目标文件
        java.io.File f=new java.io.File(filepath);

        //创建输入流
        InputStream is=new FileInputStream(f);

        //利用IOUtils将输入流的内容 复制到输出流
        IOUtils.copy(is,os);
        os.flush();

        //关闭流
        is.close();
        os.close();
        System.out.println("下载完成");

        return null;
    }
}




