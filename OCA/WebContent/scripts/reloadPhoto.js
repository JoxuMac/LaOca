/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

var request = new XMLHttpRequest();
request.open("post","servers/reloadPhoto.jsp");
request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
request.onreadystatechange = function (){
    if(request.readyState === 4){
        var respuesta = JSON.parse(request.responseText);
        if(respuesta.result ==="OK"){
        	localStorage.photo = respuesta.photo;
        }
        location.href="dashboard.html";
    }
};
var p = {
        email:localStorage.email
};
request.send("p="+JSON.stringify(p));
sleep(1000);

function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	};