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
			//���ϸ� ���
			String fileName=uploadfile.getOriginalFilename();
			//������ ����Ʈ ���� ���
			byte[] bytes = uploadfile.getBytes();
			//���������� ���
			String uploadPath = getDestinationLocation() + fileName;
			//file��ü ����
			File file = new File(uploadPath);
			//�������� ���翩�� Ȯ��
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			//���� �ƿ�ǲ ��Ʈ�� ����
			out = new FileOutputStream(file);
			//���� �ƿ�ǲ ��Ʈ���� ���� ����Ʈ����
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
