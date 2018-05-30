package com.ahope.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView  {

	//download타입을 정해주는 메소드
	public void Download(){
		setContentType("application/download; utf-8");
	}
	
	
	
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		// TODO Auto-generated method stub
		
		//모델에있는 다운로드 파일을 file변수에 넣어준다.
		File file = (File)model.get("dowloadFile");
		
		//filePath와 Name을 잘 가져오는지 확인
		System.out.println("DownloadView--> file.getPath()" + file.getPath());
		System.out.println("DownloadView--> file.getPath()" + file.getName());
		
		
		//응답에 실어보낸다 무엇? 데이터 타입, 데이터 길이
		response.setContentType(getContentType());
		response.setContentLength((int)file.length());
		
		
		//The User-Agent appears in an HTTP Request Header, 
		//not an HTTP Response one. In general, the request is sent from browser to the web 
		//application. So the user-agent variable is filled by the browser.
		
		String userAgent= request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		
		String fileName=null;
		
		//만약에 ie값이 트루면 
		
		//파일 이름 파악에 여러가지 다양한 경우가 있는것 같다
		if(ie){
			//파일이름을 얻어서 인코딩하고
			fileName = URLEncoder.encode(file.getName(), "utf-8");
		}else{
			//아니면 바이트를 얻어서 이름을 얻어서 파일에 저장해서 스트링으로 바꿔서 파일이름에 저장
			fileName = new String(file.getName().getBytes("utf-8"));
		}
		
		
		//응답에 실어보낸다. 무엇을 헤더를 파일이름을 실어보낸다.
		response.setHeader("Content-Dispositin", "attachment; filename=\"" +fileName +"\";");
		
		
		//인코딩은 바이너리로 보낸다.
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		//방출한다.
		OutputStream out = response.getOutputStream();
		
		//인풋스트림을 만든다.(로컬에 세이브하려고)
		FileInputStream fls = null;
		
		try{
			fls = new FileInputStream(file);
			//fls를 복사해서 out에 넣기
			FileCopyUtils.copy(fls, out);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fls != null){
				try{
					fls.close();
				}catch(Exception e){}
			}
		}//try end
		out.flush();
		
	}//render() end;
	

}
