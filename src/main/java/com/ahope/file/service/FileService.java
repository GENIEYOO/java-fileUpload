package com.ahope.file.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ahope.file.dao.FileDao;
import com.ahope.file.dao.FileVO;
import com.ahope.file.controller.FileUploadController;

//컨트롤러와 메소드명 동일하게 설정하여도 괜찮음

//naming fileCreate, fileRead, fileUpdate, fileDelete

@Service
public class FileService {
	
	@Autowired
	private  FileDao fileDao;
	private FileVO fileInfo;
	
	
	//fileInsert
	public List<?> fileList() throws Exception{
		return fileDao.selectFile();
		
	}
	
	
	
	//fileSave
	public  void fileSave(FileVO fileInfo) throws Exception{
		
		System.out.println("[서비스시작]");
		//System.out.println(fileInfo.getFileName());
		//System.out.println(fileInfo.getFileSize());
		fileDao.insertFile(fileInfo);
		System.out.println("[서비스종료]");
		
		//fileInfo.setFileName(fileName);
		//fileInfo.setFileSize(fileSize);
	}

}
