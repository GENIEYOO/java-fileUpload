package com.ahope.file.controller;

import java.io.File;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;

//빈에는 DownloadView클래스 선언해둠

public class FileDownloadController implements ApplicationContextAware {

	// 기본 context는 null값
	private WebApplicationContext context = null;

	// download.do 컨트롤러
	@RequestMapping("download.do")
	// path와 fileName을 파라미터로 받는다.
	public ModelAndView download(@RequestParam("path") String path, @RequestParam("fileName") String fileName) {

		// fullPath를 지정해준다.
		String fullPath = path + "\\" + fileName;

		// 파일을 fullPath에 넣는다.
		File file = new File(fullPath);

		// DownloadView="download"에 모델에 붙이고, "down하는 컨트롤러에 붙인다. file을
		// 빈객체에 저장해 두었으므로

		// ModelAndView arguments
		// ModelAndView(Object view, String modelName, Object modelObject)
		return new ModelAndView("download", "downloadFile", file);

	}

	// 이 콘텍스트를 다운로드 뷰를 주관하는 bean arg0에 붙인다.

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.context = (WebApplicationContext) arg0;
	}

}
