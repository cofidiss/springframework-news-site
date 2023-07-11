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
    





<h2 style="text-align:center"> Giriş Sayfası</h2>
</br>


<form id="loginForm" style="width:40%;margin:auto;" action="./LoginUser" method="post"> 
  <!-- Email input -->
  <div class="form-outline mb-4">
    <input type="text"   onchange="onUserNameChange(this)"  valid="false" name="userName" placeholder="Kullanıcı Adı" id="form2Example1" class="form-control" />
        <span id="userNameValidationMessage" >  </span>
  </div>

  <!-- Password input -->
  <div class="form-outline mb-4">
    <input type="password" onchange="onPasswordChange(this)"  valid="false" name="password" placeholder="Şifre" id="form2Example2" class="form-control" />
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
  <button type="button" onclick="onLoginButton()" class="btn btn-primary btn-block mb-4">Giriş</button>

  <!-- Register buttons -->
  <div class="text-center">
    <p>Üye Değil misiniz? <a href="./SignUpPage">Kayıt Ol!</a></p>
 
  </div>
</form>

<script> 

let loginForm = document.getElementById("loginForm");

//Execute a function when the user presses a key on the keyboard
loginForm.addEventListener("keypress", function(event) {
// If the user presses the "Enter" key on the keyboard
if (event.key === "Enter") {
 // Cancel the default action, if needed
 event.preventDefault();
 onLoginButton();
 
}
});
</script>

<script src="<c:url value='/resources/packages/sweetalert/dist/sweetalert2.all.min.js' />"></script>
</body>
</html>