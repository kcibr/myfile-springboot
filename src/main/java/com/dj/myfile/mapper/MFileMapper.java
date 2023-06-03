package com.dj.myfile.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.myfile.entity.MFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MFileMapper extends BaseMapper<MFile> {
    @Select("select * from m_file where file_group = #{filegroup} and is_delete = 1")
    List<MFile> queryIsDeleteFiles(String fileGroup);
}
