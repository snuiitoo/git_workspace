<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
    
<%
	// isErrorPage = "true" 속성을 추가하면, 발생한 예외 객체에 선언 없이 접근할 수 있다.
	String msg = exception.getMessage();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류</title>
<style>
body {
	text-align : center;
}
h1 {
	margin : 10px 0;
	font-size : 30vw;
}
p {
	color : red;
}
</style>
</head>
<body>
	<!-- 에러가 발생하면 이 페이지가 뜸 -->
	<h1>에러</h1>
	<p><%= msg %></p>
	<p><a href="<%= request.getContextPath() %>/">홈으로</a></p>
</body>
</html>