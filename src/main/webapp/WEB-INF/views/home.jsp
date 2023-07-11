<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="vbm672proje.dto.*"%>
<%@ page import="vbm672proje.viewModel.*"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<link type="text/css"
	href="<c:url value='/resources/css/bootstrap.min.css' />"
	rel="stylesheet" />


<link type="text/css"
	href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet" />
<link type="text/css"
	href="/vbm672proje/resources/css/fontawesome-all.css" rel="stylesheet">
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ana Sayfa</title>
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
	<br />

	<div class="clearfix" style="width: 100%">
		<div class="">
			<div style="width: 100%"
				class="d-flex justify-content-between align-items-center breaking-news bg-white">
				<div style="background: linear-gradient(0deg, #f58282, #ff0000)"
					class="d-flex flex-row flex-grow-1 flex-fill justify-content-center bg-danger py-2 text-white px-1 news">
					<span class="d-flex align-items-center">&nbsp;Son Haberler</span>
				</div>
				<marquee class="news-scroll" behavior="scroll" direction="left"
					onmouseover="this.stop();" onmouseout="this.start();">
					<c:forEach items="${Model.listOfNewsDTOs}" var="newsDTO">


						<a style="color: black"
							href="/vbm672proje/GetNewsById?Id=${newsDTO.newsId}">
							${newsDTO.header }... </a>

					</c:forEach>
					<a> </a>
				</marquee>
			</div>
		</div>
	</div>


	<br>


	<c:if test="${Model.isSiteAdmin}">

		<button class="btn btn-info"
			onclick="location.href='/vbm672proje/SiteAdmin'"
			style="cursor: pointer; margin-bottom: 10px;">Site Yöneticisi
			Ekranı</button>

	</c:if>

	<c:if test="${Model.isCategoryAdmin}">

		<button class="btn btn-info"
			onclick="location.href='/vbm672proje/CategoryAdmin'"
			style="cursor: pointer; margin-bottom: 10px;">Kategori Yöneticisi
			Ekranı</button>

	</c:if>



	<ul class="nav nav-tabs">
		<c:forEach items="${Model.listOfCategoryDTOs}" var="CategoryDTO">

			<li class="nav-item "><a
				style="color: black; border-style: solid; border-width: 1px; border-color: rgba(255, 188, 212, 0.5);"
				class="nav-link ${CategoryDTO.categoryId == Model.categoryId ? 'active':'' }"
				href="/vbm672proje/?categoryId=${CategoryDTO.categoryId }">${CategoryDTO.categoryName }
			</a></li>

		</c:forEach>
	</ul>

	<div class="box-shadow clearfix card-box">
		<h2 style="text-align: center">${Model.categoryName }</h2>
		<c:forEach items="${Model.listOfNewsDTOs}" var="newsDTO">

			<figure class="snip1527">
			<div class="image">
				<img src="/vbm672proje/resources/images/newsBackground.png"
					alt="pr-sample23">
			</div>
			<figcaption
				style="height:90px; word-break: break-word;   background: linear-gradient(0deg, #ffffff, #ede8e8 );">

			<h3
				style="text-align: center; display: block; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;color:black;">${newsDTO.header }</h3>
			</p>
			</figcaption> 
			<figcaption	style="position:absolute; top:120px;word-break: break-word;background:linear-gradient(0deg, #ffffff, #ede8e8 );height:100%">

			<p style="overflow: hidden;color:black;">${newsDTO.body }</p>

			</figcaption> <a href="/vbm672proje/GetNewsById?Id=${newsDTO.newsId}"> </a> </figure>


		</c:forEach>
	</div>
	<script
		src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>
