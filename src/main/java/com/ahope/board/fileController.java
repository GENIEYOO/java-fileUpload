package com.ahope.board;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class fileController {
	
	
	@RequestMapping(value="/fileUpload" , method = RequestMethod.GET)
	public String loadview(ModelMap modelMap) {
		return "fileUpload";
	}
	
	
	
	
	@RequestMapping(value = "/fileUploadSave", method=RequestMethod.POST)
	public String action(@RequestParam("uploadfile") MultipartFile uploadfile
	        ,HttpServletRequest request
	        , ModelMap modelMap) throws IOException {
	     
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		
		
		try{
			//파일명 얻기
			String fileName=uploadfile.getOriginalFilename();
			//파일의 바이트 정보 얻기
			byte[] bytes = uploadfile.getBytes();
			//파일저장경로 얻기
			String uploadPath = getDestinationLocation() + fileName;
			//file객체 생성
			File file = new File(uploadPath);
			//상위폴더 존재여부 확인
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			//파일 아웃풋 스트림 생성
			out = new FileOutputStream(file);
			//파일 아웃풋 스트림에 파일 바이트쓰기
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
	    
	    return "done";
	}

	
		private String getDestinationLocation(){
			return "C:\\Upload\\";
			
		}


}
