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
function registrarAnonimo() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/sinRegistrar.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result=="OK"){
				localStorage.nombre= Nombre.value;
				localStorage.email= CorreoElectronico.value;

    				location.href="partida.html";

			}else
				location.href="registro.html?err=1";
		}
	};
	var p = {
		nombre:Nombre.value, email:CorreoElectronico.value
	};
	request.send("p=" + JSON.stringify(p));
	sleep(3000);
}
function registrarGoogle(profile) {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/registroGoogle.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result=="OK"){
				localStorage.nombre= profile.ig;
				localStorage.email= profile.U3;

    				location.href="dashboard.html";

			}else
				location.href="registro.html?err=1";
		}
	};
	var p = {
		token:profile.Eea, email:profile.U3, user:profile.ig
	};
	request.send("p=" + JSON.stringify(p));
	sleep(3000);
}

function login(){
    var request = new XMLHttpRequest();
    request.open("post","servers/login.jsp");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = function (){
        if(request.readyState === 4){
            var respuesta = JSON.parse(request.responseText);
            if(respuesta.result ==="OK"){	
            	
            	localStorage.nombre = respuesta.nombre;
            	localStorage.email = respuesta.email;
            	localStorage.photo = respuesta.photo;
            	
            	location.href="dashboard.html";
            }else{
            	location.href="login.html?err=1";
            }
        }
    };
    var p = {
            email:Nombre.value, pwd1:pass.value
    };
    request.send("p="+JSON.stringify(p));
    sleep(1000);

}
function loginGoogle(profile){
    var request = new XMLHttpRequest();
    request.open("post","servers/loginGoogle.jsp");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = function (){
        if(request.readyState === 4){
            var respuesta = JSON.parse(request.responseText);
            if(respuesta.result ==="OK"){	
            	
            	localStorage.nombre= profile.ig;
			localStorage.email= profile.U3;
            	//localStorage.photo = respuesta.photo;
            	
            	location.href="dashboard.html";
            }else{
            	location.href="login.html?err=1";
            }
        }
    };
    var p = {
    			token:profile.Eea, email:profile.U3

    };
    request.send("p="+JSON.stringify(p));
    sleep(1000);

}


function actualizarPassword(){
	if(pass1.value==pass2.value){
	var request = new XMLHttpRequest();
    request.open("post","servers/actualizarPassword.jsp");
    request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    request.onreadystatechange = function (){
        if(request.readyState === 4){
            var respuesta = JSON.parse(request.responseText);
            if(respuesta.result ==="OK"){	
            	location.href="dashboard.html?err=0";
            }else
            	location.href="dashboard.html?err=2";
        }
    };
    var p = {
            email:localStorage.email, pwd_old:pass_old.value, pwd1:pass1.value
    };
    request.send("p="+JSON.stringify(p));
    sleep(1000);
	}
	else
		location.href="dashboard.html?err=1";
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

function cerrarSesion() {
	localStorage.clear();
	sessionStorage.clear();
	location.href="index.html";
	console.log("Cerrando sesión");
	signOut();
}
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
	});
}

function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	};