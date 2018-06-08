
package com.ahope.file.controller;

import java.io.File;
import com.ahope.file.dao.FileVO;
import com.ahope.file.dao.FileDao;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.ahope.file.service.FileService;

@Controller
public class FileUploadController {
	
	
	@Autowired
	private FileService service;
	
	
	
	//FileList 만들기
	@RequestMapping(value="/fileList", method=RequestMethod.GET)
	public String fileList(ModelMap modelMap) throws Exception{
		
		List<?> fileList=service.fileList();
		modelMap.addAttribute("fileList", fileList);
		
		return "fileList";
	}
	
	
	//File Create
	@RequestMapping(value="/fileUpload" , method = RequestMethod.GET)
	public String loadview(ModelMap modelMap) {
		return "fileUpload";
	}
	
	
	
	
	@RequestMapping(value = "/fileUploadSave", method=RequestMethod.POST)
    public String action(@RequestParam("uploadfile") MultipartFile uploadfile
    		,HttpServletRequest request
    		, ModelMap modelMap, @ModelAttribute FileVO fileInfo) throws Exception {
    	
    	
    	System.out.println("[컨트롤러시작]");
    	OutputStream out = null;
    	PrintWriter printWriter = null;
    	try{
    		//파일명 얻기
    		System.out.println("[파일명얻기]");
    		
    		
    		String userAgent = request.getHeader("User-Agent");
    		boolean ie = userAgent.indexOf("MSIE") > -1;
    		String fileName = null;
    		
    		if(ie) {
    			fileName = URLEncoder.encode(uploadfile.getOriginalFilename(), "EUC-KR");
    		} else {
    			fileName = new String(uploadfile.getOriginalFilename().getBytes("UTF-8"), "iso-8859-1");
    		}
    		
    		
    		long fileSize = uploadfile.getSize();
    		
    		//콘솔에 fileName 및 fileSize 찍어보기
    		//System.out.println("fileName"+fileName);
    		//콘솔에 파일사이즈 찍어보기
    		//System.out.println("파일사이즈"+ fileSize);
    		//파일의 바이트 정보 얻기
    		
    		//fileVO에 파일이름과 사이즈 넣어주기 
    		fileInfo.setFileName(fileName);
    		System.out.println(fileInfo.getFileName());
    		fileInfo.setFileSize(fileSize);
    		System.out.println(fileInfo.getFileSize());
    		service.fileSave(fileInfo);
    		//파일의 바이트정보 얻어오기
    		byte[] bytes = uploadfile.getBytes();
    		System.out.println("바이트크기" + bytes);
    		
    		//파일 저장경로 얻기
    		String uploadPath = getDestinationLocation() + fileName;
    		
    		//file객체 생성
    		File file = new File(uploadPath);
    		
    		//상위폴더 존재여부 확인
    		if(!file.getParentFile().exists()){
    			file.getParentFile().mkdirs();
    		}
    		
    		
    		//파일 아웃풋 스트림 생성
    		out = new FileOutputStream(file);
    		
    		//파일 아웃풋 스트림에 파일 바이트 쓰기
    		out.write(bytes);
    		
    	}finally{
    		try{
    			if(out !=null){
    				out.close();
    			}
    			if(printWriter !=null){
    				printWriter.close();
    			}
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    	
    	
    	modelMap.addAttribute("fileInfo",fileInfo);
    	System.out.println("[컨트롤러종료]");
    	return "redirect:/fileList";
    	
    }
	
	
	
	//File Download
    @RequestMapping(value="/fileDownload")
    public ModelAndView fileDownload(HttpServletRequest request) 
    		throws Exception {
    	System.out.println("[시작]");
    	File downloadFile = new File("C:\\Upload\\"+ request.getParameter("fileName"));
    	// downloadFile은 fileName에서 파라미터얻어서 리퀘스트 getParameter해서 이름을 얻어서 넣어준다.
    	
    	System.out.println(downloadFile);
    	System.out.println("[끝]");
    	return new ModelAndView("FileDownloadView", "downloadFile", downloadFile);
    	//Bean에 선언된 fileDownloadView에서 가져와서
    	//downloadFileView에서 download파일을 모델에 붙인다.
    }
    	

		private String getDestinationLocation(){
			return "C:\\Upload\\";
			
		}
		
		private String getDownloadDestinationLocation(){
			return "C:\\Download";
		}

}
