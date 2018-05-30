package com.ahope.file.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileDao {
	
	@Autowired
	private SqlSession query;
	
	
	
	public List<?> selectFile() throws Exception{
		return query.selectList("file.fileList");
	}
	
	
	public  void insertFile(FileVO param) throws Exception{
		query.insert("file.fileInsert", param);
	}

}
