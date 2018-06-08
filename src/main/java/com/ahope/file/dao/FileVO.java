package com.ahope.file.dao;

public class FileVO {
	private Integer fileNo;
	private String fileName;
	private long fileSize;
	private String fileLogicalName;
	private String filePath;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileNo() {
		return fileNo;
	}

	public void setFileNo(Integer fileNo) {
		this.fileNo = fileNo;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileLogicalName() {
		return fileLogicalName;
	}

	public void setFileLogicalName(String fileLogicalName) {
		this.fileLogicalName = fileLogicalName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
