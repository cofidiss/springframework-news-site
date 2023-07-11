<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="vbm672proje.dto.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${Model.categoryName } Yönetici Ekranı</title>


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
               
     <div class="clearfix card-box box-shadow">
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
	<div class="card-box box-shadow">    
<h2 style="text-align:center">${Model.categoryName } Yönetici Ekranı</h2>
<br>
 <form action="./saveCategoryAdminForm" method="post" id="categoryAdminForm"> 
 
 <input type="hidden" id="categoryId" value=${Model.categoryId } /> 
       <table id="CategoryAdminTable"  class="table table-striped" data-tableType="CategoryAdminTable">  
       <thead>
       <tr>
       <th>Başlık</th>
        <th>Metin</th>       
          <th>Herkes Görsün Mü?</th>
            <th>Sil</th>
            
       </tr>
       </thead>
       <tbody> 
       <c:forEach items="${Model.listOfNewsDTOs}" var="newsDTO">
       
       <tr data-rowtype="unchanged" data-newsId="${newsDTO.newsId}" onchange="onRowUpdate(this);">
             <td><input type="hidden" name="newsId" value="${newsDTO.newsId}">
<input type="text" maxlength="200" error-span-id="headerError" name="Header" required="true" valid="true" value="${newsDTO.header}">
<span id="headerError"> </span> </td>
        <td><input type="text" maxlength="200" error-span-id="bodyError"  name="Body"  valid="true" required="true" value="${newsDTO.body}">
      <span id="bodyError"> </span>  </td>
     
           <td>   <input type="checkbox"  <%=(((NewsDTO) pageContext.getAttribute("newsDTO")).getIsShared() == true ? "checked":"")%>  name="IsShared" onchange="onIsSharedChange(this)" value=<%=(((NewsDTO) pageContext.getAttribute("newsDTO")).getIsShared() == true ? "true":"false")%> >   </td>    
        <td>   <input onchange="onDeleteRowClickFromCategoryAdmin(this,${newsDTO.newsId})" type="checkbox" name="delete" value="true">   </td>
       </tr>
       </c:forEach>
       
      
       
        </tbody>
       
       
       </table>
       <button style="float:right;margin-right:10px" type="button" class="btn btn-success" onclick="saveCategoryAdminForm()"> Kaydet </button>
       <button type="button" onclick="addNews()" class="btn btn-info">Haber Ekle </button>
            
        </form >

        <table data-tabletype="prototype" style="display:none"> 
        <tbody>  
        <tr data-rowtype="inserted" onchange="onRowUpdate(this)">
             <td>
<input type="text"  maxlength="200" error-span-id="headerError" valid="false" name="Header" required="true">
<span id="headerError"> </span>
 </td>
        <td><input type="text"  maxlength="200" error-span-id="bodyError" valid="false" name="Body" required="true" value="">
        <span id="bodyError"> </span>
        </td>
   
           <td>   <input type="checkbox" name="IsShared" onchange="onIsSharedChange(this)" value="false">   </td>    
        <td>   <input onchange="onDeleteRowClickFromCategoryAdmin(this)" type="checkbox" name="delete" value="true">   </td>
       </tr> </tbody></table>
<br>

    
        
        
        </div>
        
        
        
        
        
<script src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>