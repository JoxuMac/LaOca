/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

var ws;

function jugar() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/jugar.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			console.log(respuesta.result);
			console.log(respuesta.mensaje);
			conectarWebSocket();
		}
	};
	var p = {
		nombre : localStorage.nombre,
		email: localStorage.email
	};
	request.send("p=" + JSON.stringify(p));
}
/*
function crearPartida() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/crearPartida.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			console.log(respuesta.result);
			console.log(respuesta.mensaje);
			conectarWebSocket();
		//	localStorage.nombre=document.getElementById("nombre").value;
		}
	};
	var p = {
		nombre : localStorage.nombre,
		numeroDeJugadores : 4
	};
	request.send("p=" + JSON.stringify(p));
}*/
/*
function unirse() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/llegarASalaDeEspera.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=request.responseText;
			console.log(respuesta);
			conectarWebSocket();
		//	localStorage.nombre=document.getElementById("nombre").value;
		}
	};
	var p = {
		nombre : localStorage.nombre
	};
	request.send("p=" + JSON.stringify(p));
}
*/

function conectarWebSocket() {
	ws=new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/OCA/servidorDePartidas");
	
	ws.onopen = function() {
		addMensaje("<b>OCA: </b>Conectado");
	}
	
	ws.onmessage = function(datos) {
		var mensaje=datos.data;
		mensaje=JSON.parse(mensaje);
		if (mensaje.tipo=="DIFUSION") {
			
		//	var div=document.createElement("div");
		//	divChat.appendChild(div);
		//	div.innerHTML=mensaje.mensaje;
			addMensaje(mensaje.mensaje);
		} else 
			if (mensaje.tipo=="COMIENZO") {
			addMensaje("Comienza la partida");
			comenzar(mensaje);
		} else{
			console.log(mensaje.tipo);
			console.log(mensaje.mensaje);
		}
	}	
}

function comenzar(mensaje) {
	var btnDado=document.getElementById("lanzarDado");
	if (mensaje.jugadorConElTurno==localStorage.nombre) {
		btnDado.disabled = false;
	} else {
		btnDado.disabled = true;
	}
	//var spanListaJugadores=document.getElementById("spanListaJugadores");
	//var jugadores=mensaje.jugadores;
	//var r="";
//	for (var i=0; i<jugadores.length; i++)
//		r=r+jugadores[i] + " / ";
	//spanListaJugadores.innerHTML=r;
	sessionStorage.idPartida=mensaje.idPartida;
}

function broadcast(texto, tipo) {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/broadcast.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=request.responseText;
			console.log(respuesta);
		//	conectarWebSocket();
		//	localStorage.nombre=document.getElementById("nombre").value;
		}
	};
	var p = {
		usuario : localStorage.getItem("nombre"),
		msg : texto,
		tipo : tipo
	};
	request.send("p=" + JSON.stringify(p));
	//var div=document.createElement("div");
	//divChat.appendChild(div);
	//div.innerHTML=texto;
	
}

function addMensaje(texto) {
	var div=document.createElement("div");
	divChat.appendChild(div);
	div.innerHTML=texto;
	
}
