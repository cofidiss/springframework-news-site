<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css"
	href="<c:url value='/resources/css/bootstrap.min.css' />"
	rel="stylesheet" />
	
	
<link type="text/css"
	href="<c:url value='/resources/css/bootstrap.css' />"
	rel="stylesheet" />
	<link type="text/css" href="/vbm672proje/resources/css/fontawesome-all.css" rel="stylesheet">
<link type="text/css"
	href="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.min.css' />"
	rel="stylesheet" />
<link type="text/css"
	href="<c:url value='/resources/myCss/newsSite.css' />" rel="stylesheet" />
<link type="text/css"
	href="<c:url value='/resources/myCss/HomePageComponents.css' />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/myJs/newsSite.js' />">
	
</script> 
</head>
<body>

	
	<div class="clearfix card-box box-shadow" >
		<i class="fas fa-calendar-alt"></i> <span class="day">
		
	 <%
		 String pattern = "dd.MM.yyyy HH:mm:ss";
		 java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(pattern);
		 String date = simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
         out.println(date);
      %>
		</span>

		<c:choose>

			<c:when test="${Model.userName.equals('Misafir')}">

				<button class="btn btn-outline-primary"
					style="float: right; margin-left: 5px; padding: 5px; border: none"
					onClick="location.href='/vbm672proje/LoginPage'">
					<i class="fas fa-sign-in-alt"></i> Giriş
				</button>

			</c:when>

			<c:otherwise>


				<button class="btn btn-outline-secondary"
					style="float: right; margin-left: 5px; padding: 5px; border: none"
					onclick="location.href='/vbm672proje/SignOut'">
					<i class="fas fa-sign-out-alt"> </i> Çıkış
				</button>

			</c:otherwise>
		</c:choose>
		
		<button class="btn btn-outline-info"
			onclick="location.href='/vbm672proje/'"
			style="float: right; margin-left: 5px; padding: 5px; border: none"><i class="fas fa-home"></i>Ana Sayfa</button>
		<p style="float: right; margin: 0px; padding: 5px">
			<i class="fas fa-user"></i> Kullanıcı Adı: <strong>${Model.userName }</strong>




		</p>

	</div>
<br>
	<div class="clearfix card-box" style ="width:50%;margin:auto">
		<div style="    border-bottom-style: groove;
    border-bottom-width: 1px;">
			<p style="margin-bottom:10px;word-break: break-word;text-align:center"> ${Model.header }</p>

		</div>
		<div>
			<p style="margin-top:10px;word-break: break-word">${Model.body }</p>
		</div>


		</div>
	
	
	
	
	
	<script
	src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>