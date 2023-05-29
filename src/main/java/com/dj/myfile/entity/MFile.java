package com.dj.myfile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value ="m_file")
@Data
public class MFile extends BaseColumns{
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer fid;

    /**
     * 文件路径
     */
    private String path;

    /**
     * md5验证
     */
    private String md5;

    /**
     * 文件名
     */
    private String fName;

    /**
     * 文件类型#
     */
    private String type;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 文件组*
     */
    private String fileGroup;

    /**
     * 父级目录*
     */
    private String parentDir;

    /**
     * 下载次数
     */
    private Integer downloadCount;
    /**
     * 版本
     */
    private String version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 是否是文件夹
     */
    private Integer isFolder;
}
