package com.dj.myfile.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.MFile;
import com.dj.myfile.mapper.MFileMapper;
import com.dj.myfile.service.MFileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private MFileMapper mapper;
    public FileController(MFileService mFileService){
        this.mFileService = mFileService;
    }

    /**
     * 查询全部文件
     * @param parentDir
     * @param search
     * @param type
     * @return
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
                .orderBy(true,true,"size")
                .like("type", type)
                .like("f_name",search);
        System.out.println("调用了查询接口");
    return mFileService.list(wrapper);
    }

    /**
     * 分类查询
     * @param params
     * @return
     */
    @PostMapping("/queryType")
    public Object typeQueryFileList(@RequestBody Map<String, String> params){
        String search = "";
        System.out.println(params.get("fileGroup"));
        System.out.println(params.get("typeList"));
        if (params.get("search") != null){
            search = params.get("search");
        }
        String arr[] = params.get("typeList").split("/");// 分割文件类型字符串，并转换成数组
        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.eq("file_group", params.get("fileGroup"))
                .orderBy(true,true,"size")
                .in("type", arr)
                .like("f_name", search);
        System.out.println("调用了分类查询接口");
        return mFileService.list(wrapper);
    }

    /**
     * 新建文件夹
     * @param folderName
     * @param parentFolderPath
     * @param fileGroup
     * @return
     */
    @GetMapping("/createNewFolder")
    public String NewFolder( String folderName, String parentFolderPath, String fileGroup){
        return mFileService.CreateNewFolder(folderName,parentFolderPath,fileGroup);
    }

    /**
     * 文件上传
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

    /**
     * 文件下载
     * @param params
     * @param response
     * @throws IOException
     */
    @PostMapping("/download")
    public void fileDownload(@RequestBody Map<String, String> params, HttpServletResponse response)throws IOException{
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

    /**
     * 删除文件
     * @param params
     * @return
     */
    @PostMapping("/deleteFiles")
    public String deleteFile(@RequestBody Map<String, String> params){
        System.out.println(params.get("fileList").split("/"));
        String arr[] = params.get("fileList").split("/");
        QueryWrapper<MFile> wrapper = new QueryWrapper<>();
        wrapper.in("fid",arr);
        mFileService.remove(wrapper);
        return "删除完成";
    }

    /**
     * 修改文件名
     * @param fid
     * @param fName
     * @return
     */
    @GetMapping("/reName")
    public String reName(String fid,String fName) {
        MFile file = mFileService.getById(fid);
        file.setFName(fName);
        mFileService.updateById(file);
        return "修改成功";
    }

    /**
     * 查询回收站文件
     * @param fileGroup
     * @return
     */
    @GetMapping("/queryDeletedFile")
    public Object qDFiles(String fileGroup){
        return mapper.queryIsDeleteFiles(fileGroup);
    }
}
