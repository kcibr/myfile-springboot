package com.dj.myfile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.myfile.entity.MFile;
import com.dj.myfile.entity.User;
import com.dj.myfile.mapper.UserMapper;
import com.dj.myfile.service.MFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyFileApplicationTests {
	@Value("${rootPath}")
	public String root_path;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private MFileService mFileService;
	@Test
	void contextLoads() {
	}
	@Test
	public void testUserSelect(){
		List<User> userList = userMapper.selectList(null);
	}
	@Test
	public void testFileSelect(){
		QueryWrapper<MFile> wrapper = new QueryWrapper<>();
		wrapper.eq("parent_dir", "/Elysia");
		List<MFile> fileList = mFileService.list(wrapper);
		System.out.println("生生世世"+root_path);
	}

}
