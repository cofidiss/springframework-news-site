function onUserNameChange(element){
	element.value = element.value.trim();
	
		var errorSpanElement = 	document.getElementById("userNameValidationMessage");
	
	if (element.value.length === 0){
		
errorSpanElement.innerText = "Lütfen Bir Kullanıcı Adı Giriniz";
element.setAttribute("valid","false");
return;		
		
	}
	
		if (element.value.length > 20){
		
errorSpanElement.innerText = "Lütfen En Fazla 20 Karakter Giriniz";
element.setAttribute("valid","false");
return;		
		
	}
	errorSpanElement.innerText = "";
element.setAttribute("valid","true");
return;
	
}



function onRowUpdate(updatedRow){

	if(updatedRow.getAttribute("data-rowtype") == "unchanged" ){
			updatedRow.setAttribute("data-rowtype","updated");
		
	}

var textElements = Array.from(updatedRow.getElementsByTagName("input")).filter(element => element.type == "text");
for (const element of textElements){
	element.value = element.value.trim();
	if (element.getAttribute("required") == "true"){

		 if (element.value.length === 0){
			element.parentElement.querySelector("#"+ element.getAttribute("error-span-id")).innerText="Lütfen En Az Bir Karakter Giriniz";
			element.setAttribute("valid","false");
		} 
		
		else {
			
			element.parentElement.querySelector("#"+ element.getAttribute("error-span-id")).innerText="";
					element.setAttribute("valid","true");
			
		}
		
	}
	

}
	
	
} 


function onPasswordChange(element){

		var errorSpanElement = 	document.getElementById("passwordValidationMessage");
	
	if (element.value.length === 0){
		
errorSpanElement.innerText = "Lütfen Bir Şifre Giriniz";
element.setAttribute("valid","false");
return;		
		
	}
	
		if (element.value.length > 20){
		
errorSpanElement.innerText = "Lütfen En Fazla 20 Karakter Giriniz";
element.setAttribute("valid","false");
return;		
		
	}
	errorSpanElement.innerText = "";
element.setAttribute("valid","true");
return;
	
}


function onSignUpButton(){
	
var signUpForm = document.getElementById("signUpForm");
	var inputElements = signUpForm.getElementsByTagName("input");
	
		for (let i=0; i<inputElements.length;++i ){
			inputElements[i].onchange();
		}
		
	for (let i=0; i<inputElements.length;++i ){
			if(inputElements[i].getAttribute("valid") == "false"){
						Swal.fire({
  title: "Lütfen Zorunlu Alanları Doldurunuz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
   }
 
})
				return;
			}
			
		
			
		
	}
		signUpForm.submit();
}

function onLoginButton(){
	
		
var loginForm = document.getElementById("loginForm");
	var inputElements = loginForm.getElementsByTagName("input");
	
		for (let i=0; i<inputElements.length;++i ){
		inputElements[i].onchange();
		
			
	}
	
	
	for (let i=0; i<inputElements.length;++i ){
		inputElements[i].onchange();
		
			if(inputElements[i].getAttribute("valid") == "false"){
						Swal.fire({
  title: "Lütfen Zorunlu Alanları Doldurunuz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
   }
 
})
				return;
			}
		
	}
	
				loginForm.submit();	
	
	
}



function onDeleteRowClickFromCategoryAdmin(element,deletedRowId){
	
	if(deletedRowId=== undefined || deletedRowId=== null){
		
		element.closest("tr").remove();
		
		
	}
	
	else {
		
	var deletedRowElement =  document.querySelector("[data-newsid='" + deletedRowId + "']");	
		deletedRowElement.setAttribute("data-rowtype","deleted");
		deletedRowElement.classList.add("hidden");
	}
	
}


function onDeleteRowClickFromSiteAdmin(element,deletedRowId){
	
	if(deletedRowId=== undefined || deletedRowId=== null){
		
		element.closest("tr").remove();
		
		
	}
	
	else {
		
	var deletedRowElement =  document.querySelector("[data-categoryid='" + deletedRowId + "']");	
		deletedRowElement.setAttribute("data-rowtype","deleted");
		deletedRowElement.classList.add("hidden");
	}
	
}


function onIsSharedChange(checkboxElement){

	if(checkboxElement.checked){
		checkboxElement.value="true";	
		
	}
	else{
		checkboxElement.value="false";	
		
	}
	
}

function addNews(){
	
 var prototypeRowElement =   document.querySelector("[data-tabletype='prototype']").querySelector("tr");	
  var categoryAdminTableBodyElement =   document.querySelector("[data-tabletype='CategoryAdminTable']").querySelector("tbody");	
 categoryAdminTableBodyElement.appendChild(prototypeRowElement.cloneNode(true));
	
}



function saveCategoryAdminForm(){
	
var inputELements =  document.querySelector("#CategoryAdminTable").getElementsByTagName("input");
var categoryFormElement =  document.querySelector("#categoryAdminForm");

if(isFormValid(categoryFormElement)== false){
	return;
	
}
	for (let i=0; i< inputELements.length;++i){
		
	
	
		if (inputELements[i].getAttribute("valid") == "false"){
					Swal.fire({
  title: "Lütfen Zorunlu Alanları Doldurunuz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    }
 
})
			return;
		
		} 

	}
	
	
	var deletedRowIdsArr=[];
	
	
	var deletedRowElements = document.querySelectorAll("[data-rowtype='deleted']");
	for(let i=0; i<deletedRowElements.length;i++){
		
		deletedRowIdsArr.push(deletedRowElements[i].getAttribute("data-newsId"));
		
	}
	console.log(deletedRowIdsArr);
	
	var insertedRowsObjArr=[];
	
		var insertedRowElements = document.querySelector("[data-tabletype='CategoryAdminTable']").querySelectorAll("[data-rowtype='inserted']");
	for(let i=0; i<insertedRowElements.length;i++){
		
		let instertedRow ={};	
		
		instertedRow.header=insertedRowElements[i].querySelector("[name='Header']").value;
		instertedRow.body=insertedRowElements[i].querySelector("[name='Body']").value;	
		instertedRow.isShared=insertedRowElements[i].querySelector("[name='IsShared']").value;
		insertedRowsObjArr.push(instertedRow);		
		
	}
		console.log(insertedRowsObjArr);
		
			var updatedRowsObjArr=[];
		
			var updatedRowElements = document.querySelector("[data-tabletype='CategoryAdminTable']").querySelectorAll("[data-rowtype='updated']");
	for(let i=0; i<updatedRowElements.length;i++){
		
		let updatedRow ={};	
		
		updatedRow.header=updatedRowElements[i].querySelector("[name='Header']").value;
		updatedRow.body=updatedRowElements[i].querySelector("[name='Body']").value;		
		updatedRow.isShared=updatedRowElements[i].querySelector("[name='IsShared']").value;
		updatedRow.newsId=updatedRowElements[i].getAttribute("data-newsId");
		updatedRowsObjArr.push(updatedRow);		
		
	}
	
	console.log(updatedRowsObjArr);
	var categoryId = 	document.getElementById("categoryId").value;
		var formData={deletedRowIds:deletedRowIdsArr,insertedRows:insertedRowsObjArr,updatedRows:updatedRowsObjArr,categoryId:categoryId};
		
		 // Url for the request 
    var url = './saveCategoryAdminForm';
  
fetch(url, {
  method: 'POST',  
  body: new URLSearchParams({param1: JSON.stringify(formData)}),    
})
.then(
	
	response => { 
	
		return response.text();
	})
.then(
	data => {
		
		
	
		if (data.startsWith("00")){
		
					Swal.fire({
  title: data.substring(2),
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})
		return;
			
		}
		
	
		Swal.fire({
  title: data,
  icon:"success",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})
		

})
.catch((error) => {
	
	Swal.fire({
  title: "Hata Geldi Lütfen Tekrar Deneyiniz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})



});
	
}


function addCategory(){
	
 var prototypeRowElement =   document.querySelector("[data-tabletype='prototype']").querySelector("tr");	
  var siteAdminTableBodyElement =   document.querySelector("[data-tabletype='SiteAdminTable']").querySelector("tbody");	
 siteAdminTableBodyElement.appendChild(prototypeRowElement.cloneNode(true));
	
}

function saveSiteAdminForm(){
	
var inputELements =  document.querySelector("#SiteAdminTable").getElementsByTagName("input");
var siteAdminForm =  document.querySelector("#siteAdminForm");

if(isFormValid(siteAdminForm)== false){
	return;
	
}
	for (let i=0; i< inputELements.length;++i){
		
	
	
		if (inputELements[i].getAttribute("valid") == "false"){
					Swal.fire({
  title: "Lütfen Zorunlu Alanları Doldurunuz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    }
 
})
			return;
		
		} 

	}
	
	
	var deletedRowIdsArr=[];
	
	
	var deletedRowElements = document.querySelectorAll("[data-rowtype='deleted']");
	for(let i=0; i<deletedRowElements.length;i++){
		
		deletedRowIdsArr.push(deletedRowElements[i].getAttribute("data-categoryId"));
		
	}
	console.log(deletedRowIdsArr);
	
	var insertedRowsObjArr=[];
	
		var insertedRowElements = document.querySelector("[data-tabletype='SiteAdminTable']").querySelectorAll("[data-rowtype='inserted']");
	for(let i=0; i<insertedRowElements.length;i++){
		
		let instertedRow ={};	
		
		instertedRow.stringOfAdminsUserNames=insertedRowElements[i].querySelector("[name='ListOfCategoryAdminsUserNames']").value;
		instertedRow.categoryName=insertedRowElements[i].querySelector("[name='CategoryName']").value;		
		insertedRowsObjArr.push(instertedRow);		
		
	}
		console.log(insertedRowsObjArr);
		
			var updatedRowsObjArr=[];
		
			var updatedRowElements = document.querySelector("[data-tabletype='SiteAdminTable']").querySelectorAll("[data-rowtype='updated']");
	for(let i=0; i<updatedRowElements.length;i++){
		
		let updatedRow ={};	
		
		updatedRow.stringOfAdminsUserNames=updatedRowElements[i].querySelector("[name='ListOfCategoryAdminsUserNames']").value;
		updatedRow.categoryName=updatedRowElements[i].querySelector("[name='CategoryName']").value;	
		updatedRow.categoryId=updatedRowElements[i].getAttribute("data-categoryId");
		updatedRowsObjArr.push(updatedRow);		
		
	}
	
	console.log(updatedRowsObjArr);
		
		var formData={deletedRowIds:deletedRowIdsArr,insertedRows:insertedRowsObjArr,updatedRows:updatedRowsObjArr};
		
		 // Url for the request 
    var url = './saveSiteAdminForm';
  
fetch(url, {
  method: 'POST',  

  body: new URLSearchParams({param1: JSON.stringify(formData)}),    
})
.then(
	
	response => { 
			if (response.ok == false) {
      return  Promise.reject(response);
      }
        return response.text();     
           

	})
.then(
	data => 	
	
	{
		
		if (data.startsWith("00")){
		
					Swal.fire({
  title: data.substring(2),
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})
		return;
			
		}
		
	
		Swal.fire({
  title: data,
  icon:"success",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})
		

})
.catch((error) => {

	Swal.fire({
  title: "Hata Geldi Lütfen Tekrar Deneyiniz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
}).then((result) => {
  /* Read more about isConfirmed, isDenied below */
  if (result.isConfirmed) {
    location.reload();}
 
})



});
	
}



function isFormValid(formElement){
	let valid = true;
let rowElements = formElement.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
	for (let i = 0;i<rowElements.length;++i){
	let rowElement = 	rowElements[i];
		var textElements = Array.from(rowElement.getElementsByTagName("input")).filter(element => element.type == "text");
for (const element of textElements){
	element.value = element.value.trim();
	if (element.getAttribute("required") == "true"){

		 if (element.value.length === 0){
			element.parentElement.querySelector("#"+ element.getAttribute("error-span-id")).innerText="Lütfen En Az Bir Karakter Giriniz";
			element.setAttribute("valid","false");
			valid = false;
		} 
		
		else {
			
			element.parentElement.querySelector("#"+ element.getAttribute("error-span-id")).innerText="";
					element.setAttribute("valid","true");
			
		}
		
	}
	

}
		
		
	}
	
	return valid;
}











function onGoToCategoryAdminPage(){
	var categoryId = document.getElementById("categorySelect").value;
	if (categoryId === "" || categoryId === undefined || categoryId === null){
			Swal.fire({
  title: "Lütfen Kategori Seçiniz",
  icon:"error",
  showDenyButton: false,
  showCancelButton: false,
  confirmButtonText: 'Tamam',  
});

		return;
	}
	let url = "/vbm672proje/CategoryAdmin?CategoryId=" + document.getElementById("categorySelect").value;
	
	location.replace(url);
	
	
	
	
	
	
}