package com.dj.myfile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.MFile;
import com.dj.myfile.entity.User;
import com.dj.myfile.service.MFileService;
import com.dj.myfile.utils.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FileController
 * @Description //TODO
 * @Date 2023/4/24 16:07
 * @Author kcibr
 * @Version 1.0
 **/
@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @Value("${rootPath}")
    private String root_path;
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
    public Object queryFileList(String parentDir,String search,String type){
        if (search == null){
            search = "";
        }
        if (type == null){
            type = "";
        }
        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_dir", parentDir)
                .like("type", type)
                .like("f_name",search);
        System.out.println("调用了查询接口");
    return mFileService.list(wrapper);
    }
    @GetMapping("/queryType")
    public Object typeQueryFileList(String fileGroup,String search,String type){
        if (search == null){
            search = "";
        }
        if (type == null){
            type = "";
        } else {

        }
        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.eq("file_group", fileGroup)
                .like("type", type)
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


    @GetMapping("/createNewFolder")
    public String NewFolder( String folderName, String parentFolderPath, String fileGroup){
        return mFileService.CreateNewFolder(folderName,parentFolderPath,fileGroup);
    }

    /**
     * @param file
     * @param uploadFolderPath
     * @param fileGroup
     * @return
     */
    @PostMapping("/uploadFile")
    public String uploadFiles(List<MultipartFile> file ,String uploadFolderPath,String fileGroup){
        System.out.println(file);
        System.out.println(uploadFolderPath);
        System.out.println(fileGroup);
        for (MultipartFile f : file){
            try {
                mFileService.uploadFile(f,uploadFolderPath,fileGroup);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "上传完成";
    }

    @PostMapping("/download")
    public void fileDownload(@RequestBody Map<String, String> params, HttpServletResponse response)throws IOException{
        System.out.println(params.get("fid"));
        System.out.println(params.get("fName"));
        MFile f = mFileService.getById(params.get("fid"));

        File file = new File(root_path+f.getPath());

        InputStream inputStream = new FileInputStream(root_path+f.getPath());
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(f.getFName(), "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        while((len = inputStream.read(b)) > 0){
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

}
