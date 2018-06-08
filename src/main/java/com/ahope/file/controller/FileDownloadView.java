package com.ahope.file.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

//라우팅을 해주는 컨트롤러와 view를 보여주는 jsp페이지 제외하고
//빈파일에 FileDownloadView 클래스를 실행되는 뷰로 설정해줌
//앱스트랙뷰를 상속한 fildDownloadView

public class FileDownloadView extends AbstractView {

	// 빈에 선언한 뷰 생성자
	public FileDownloadView() {
		// 객체가 생성할 때 Contect Type을 다음과 같이 변경
		setContentType("application/download; charset=utf-8");
	}

	@SuppressWarnings("unchecked")

	// 컨트롤러가 모델에 저장되는게 먼저 실행된 후에
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 모델에서 downloadFile을 get해서 file변수에 넣는다.
		File file = (File) model.get("downloadFile");

		// 응답의 콘텐트타입을 설정하고
		response.setContentType(getContentType());

		// 응답에 파일 길이를 실어서 보냅니다.
		response.setContentLength((int) file.length());

		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String fileName = null;

		if (ie) {
			fileName = URLEncoder.encode(file.getName(), "UTF-8");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
		}

		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fls = null;

		try {
			fls = new FileInputStream(file);
			FileCopyUtils.copy(fls, out);
			// 파일을 읽어서 복사해주고
		} finally {
			if (fls != null) {
				try {
					fls.close();
				} catch (IOException e){}
			}
		}
		out.flush();
	}

}

//// 모델을 인자로 받는다.
//// request
//// response
// Map<String, Object> model,
// HttpServletRequest request,
// HttpServletResponse response)
// throws Exception {
//
// // hashmap으로 파일정보 객체를 만든다.
// Map<String, Object> fileInfo = (Map<String, Object>)
//// model.get("downloadFile");
//
//
// // 파일 path, 파일 실제이름, 파일 이름
// String filePath = (String) fileInfo.get("filePath");
// String fileLogicalName = (String) fileInfo.get("fileLogicalName"); //화면에 표시될
//// 파일 이름
// String fileName = (String) fileInfo.get("fileName");//파일 물리명(실제 저장된 파일 이름)
//
// // 파일 생성하기
// File file = new File(filePath, fileName);
//
// // 응답에 콘텐트 타입을 실어보낸다.
// response.setContentType(getContentType());
// // 응답에 콘텐트 길이를 실어보낸다.
// response.setContentLength((int) file.length());
//
//
// String userAgent = request.getHeader("User-Agent");
// boolean ie = userAgent.indexOf("MSIE") > -1;
//
// String resFileName = null;
//
//
// if(ie) {
// resFileName = URLEncoder.encode(fileLogicalName, "UTF-8");
// }else{
// resFileName = new String(fileLogicalName.getBytes("utf-8"), "iso-8859-1");
// }
//
//
//
//
//
//
//
//
// // 응답받은 파일이름
//
// // resfileName은 파일이름을 "utf-8"로 인코딩한 것
//
// // 응답에 헤더를 보내는데
// response.setHeader("Content-Disposition", "attachment; filename=\"" +
//// resFileName + "\";");
// response.setHeader("Content-Transfer-Encoding", "binary");
// OutputStream out = response.getOutputStream();
//
// FileInputStream fls = null;
//
// try {
//
// } finally {
// if (fls != null)
// try {
// fls.close();
// } catch (IOException ex) {
//
// }
// }
// out.flush();
//
// }