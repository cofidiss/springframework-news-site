<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="vbm672proje.dto.*" %>
<%@ page import="vbm672proje.viewModel.*" %>
<%@ page import="java.lang.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Site Yöneticisi Ekranı</title>

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
	<div class="card-box box shadow clearfix">
<h2 style= "text-align:center">Kategori Tanımlama</h2>

<br>


 <form action="./saveSiteAdminForm" method="post" id="siteAdminForm"> 
       <table id="SiteAdminTable"  class="table table-striped table-bordered" data-tableType="SiteAdminTable">  
       <thead>
       <tr>
       <th>Kategori Adminleri</th>
        <th>Kategori Adı</th>       
          <th>Sil</th>
             
       </tr>
       </thead>
       <tbody> 
       <c:forEach items="${Model.listOfCategoryDTOs}" var="categoryDTO">
       
       <tr data-rowtype="unchanged" data-categoryId="${categoryDTO.categoryId}" onchange="onRowUpdate(this);">
             <td><input type="hidden" name="newsId" value="${categoryDTO.categoryId}">
<input type="text" maxlength="20" error-span-id="listOfcategoryAdminsUserNamesError" name="ListOfCategoryAdminsUserNames" required="true" valid="true" value="${categoryDTO.stringOfAdminsUserNames}">
<span id="listOfcategoryAdminsUserNamesError"> </span> </td>
     <td><input type="text" maxlength="20" error-span-id="categoryNameError"  name="CategoryName"  valid="true" required="true" value="${categoryDTO.categoryName}">
      <span id="categoryNameError"> </span>  </td>
   
        <td>   <input onchange="onDeleteRowClickFromSiteAdmin(this,${categoryDTO.categoryId})" type="checkbox" name="delete" value="true">   </td>
       </tr>
       </c:forEach>
       
      
       
        </tbody>
       
       
       </table>
       <button style="float:right;margin-right:10px" type="button" class="btn btn-success" onclick="saveSiteAdminForm()"> Kaydet </button>
       <button type="button" onclick="addCategory()" class="btn btn-info">Kategori Ekle </button>
       <br>
       <p style="margin-top:10px;"><i class="fas fa-exclamation-triangle"></i>    Kategori yöneticisi olarak eklenebilecek kullanıcılar: ${String.join(',',Model.listOfNonCatetegoryAdminsUserNames) }   </p>
            <p> <i class="fas fa-exclamation-triangle"></i> <b>Lütfen "," ile ayrılmış. Kullanıcı adları yazınız. Örneğin user1,user2 </b></p>
            <p><i class="fas fa-exclamation-triangle"></i> <b> Her kullanıcı sadece bir kategoride yönetici olabilir. </b></p>
        </form >

        <table data-tabletype="prototype" style="display:none"> 
        <tbody>  
        <tr data-rowtype="inserted" onchange="onRowUpdate(this)">
             <td>
<input type="text"  maxlength="20" error-span-id="listOfcategoryAdminsUserNamesError" valid="false" name="ListOfCategoryAdminsUserNames" required="true">
<span id="listOfcategoryAdminsUserNamesError"> </span>
 </td>
        <td><input type="text"  maxlength="20" error-span-id="categoryNameError" valid="false" name="CategoryName" required="true" value="">
        <span id="categoryNameError"> </span>
        </td>
   
           
        <td>   <input onchange="onDeleteRowClickFromSiteAdmin(this)" type="checkbox" name="delete" value="true">   </td>
       </tr> </tbody></table>
<br>
  </div>
        <br>
        <div class="card-box box-shadow">   
        <h2 style= "text-align:center">Kategori Yöneticisi Ekranları</h2>
        <br>
        <div style="display:table;margin:auto"> 
        <label for="categorySelect" style="margin-right: 10px;"> Kategori Adı: </label>
        <select id="categorySelect" class="browser-default custom-select" style="margin-right: 10px;width:auto">
    <c:forEach items="${Model.listOfCategoryDTOs }" var="categoryDTO">
        <option value="${categoryDTO.categoryId}" >${categoryDTO.categoryName}</option>
    </c:forEach>
</select>

<button  style="margin-right: 10px;" class="btn btn-info" onClick=onGoToCategoryAdminPage() title="Kategori Yönetici Ekranına Gider"> Git</button>
       </div> 
        </div>
        
        
        
        <script src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>