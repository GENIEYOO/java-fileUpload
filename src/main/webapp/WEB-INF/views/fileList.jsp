<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>


 <%@page import="java.io.File" %>
 
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<!-- jsp에서는 모델맵에 붙여놨던 것들을 뽑아준다. -->
<body>

	
	<h2>업로드된 파일</h2>
	
	<table border=1>
	
	<th>파일번호</th>
	<th>파일이름</th>
	<th>파일다운로드</th>
	<th>파일사이즈</th>


		
	<c:forEach var="file" items="${fileList}" varStatus="status">
	<tr>
	<td><c:out value="${file.fileNo}"></c:out></td>
	<td><c:out value="${file.fileName}"></c:out></td>
	<td><a href="/fileDownload?fileName=${file.fileName}"><button>다운로드</button></a></td>
	<td><c:out value="${file.fileSize}"></c:out></td>
	</tr>
	
	</c:forEach>
	</table>
	
	
	<a href="/fileUpload">새파일 업로드</a>
</body>



</html>