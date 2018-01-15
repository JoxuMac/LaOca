function registrar() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/registrar.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result=="OK"){
    				location.href="login.html";

			}else
				location.href="registro.html?err=1";
		}
	};
	var p = {
		nombre:Nombre.value, email:CorreoElectronico.value, pwd1:pwd1.value, pwd2:pwd2.value
	};
	request.send("p=" + JSON.stringify(p));
	sleep(1000);
}
function login(){
   
    var request = new XMLHttpRequest();
    request.open("post","servers/login.jsp");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = function (){
        if(request.readyState === 4){
            var respuesta = JSON.parse(request.responseText);
            if(respuesta.result ==="OK"){	
            	
            	sessionStorage.nombre = respuesta.nombre;
            	sessionStorage.email = respuesta.email;
            	sessionStorage.photo = respuesta.photo;
            	
            	location.href="dashboard.html";
            }else{
            	location.href="login.html?err=1";
                //mensajeRegistro.innerHTML = respuesta.mensaje;
               // alert("Respuesta distinto a OK");
            }
        }
    };
    var p = {
            email:Nombre.value, pwd1:pass.value
    };
    request.send("p="+JSON.stringify(p));
    sleep(1000);

}

function estaConectado() {
	var request = new XMLHttpRequest();	
	request.open("get", "../estaConectado.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result!="OK") {
				alert("No estás conectado y no tienes permiso para esta página");
			}
		}
	};
	request.send();	
}

function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	};