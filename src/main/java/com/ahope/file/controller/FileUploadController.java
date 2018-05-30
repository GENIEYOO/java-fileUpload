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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
			String fileName=uploadfile.getOriginalFilename();
			
			//파일 사이즈 얻기
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
	

	//파일다운로드
	
	//기본 context는 null값
		private WebApplicationContext context=null;
		
		
		//download.do 컨트롤러
		@RequestMapping("download.do")
		//path와 fileName을 파라미터로 받는다.
		public ModelAndView download(@RequestParam("path")String path, @RequestParam("fileName")String fileName){
			System.out.println("컨트롤러시작");
			//fullPath를 지정해준다.
			
			String download=path + "\\" + fileName;
			System.out.println("fullPath출력" + fullPath);
			//파일을 fullPath에 넣는다.
			File file = new File(fullPath);
			
			//DownloadView="download"에 모델에 붙이고, "down하는 컨트롤러에 붙인다. file을
			//빈객체에 저장해 두었으므로
			
			//ModelAndView arguments
			//ModelAndView(Object view, String modelName, Object modelObject)
			
			System.out.println("컨트롤러끝");
			return new ModelAndView("download", "downloadFile", file);
			
			
		}
		
		
		
		//이 콘텍스트를 다운로드 뷰를 주관하는 bean arg0에 붙인다.
		
//		@Override
//		public void setApplicationContext(ApplicationContext arg0) 
//				throws BeansException {
//			// TODO Auto-generated method stub
//			this.context = (WebApplicationContext)arg0;
//		}
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		private String getDestinationLocation(){
			return "C:\\Upload\\";
			
		}
		
		private String getDownloadDestinationLocation(){
			return "C:\\Download";
		}


}
