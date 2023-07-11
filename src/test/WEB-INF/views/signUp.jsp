<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Kayıt Sayfası</title>



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

    
<h2 style="text-align:center"> Kayıt Sayfası</h2>
</br>


<form id="signUpForm" style="width:40%;margin:auto;" action="./SignUp" method="post"> 
  <!-- Email input -->
  <div class="form-outline mb-4">
    <input type="text" onchange="onUserNameChange(this)"  valid="false" name="userName" placeholder="Kullanıcı Adı" id="form2Example1" class="form-control" />
    <span id="userNameValidationMessage" >  </span>
  </div>

  <!-- Password input -->
  <div class="form-outline mb-4">
    <input type="password" onchange="onPasswordChange(this)" valid="false" name="password" placeholder="Şifre" id="form2Example2" class="form-control" />
       <span  id="passwordValidationMessage"></span>
  </div>
 <p style="text-align:center">${Model}</p>
  <!-- 2 column grid layout for inline styling -->
  <div class="row mb-4">
    <div class="col d-flex justify-content-center">
      <!-- Checkbox -->
       
    
    </div>

  
  </div>

  <!-- Submit button -->
  <button onclick="onSignUpButton()" type="button" class="btn btn-primary btn-block mb-4">Kayıt Ol</button>

  <!-- Register buttons -->
  <div class="text-center">
    <p>Zaten Üye misiniz? <a href="./LoginPage">Giriş</a></p>
 
  </div>
</form>





<script src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>