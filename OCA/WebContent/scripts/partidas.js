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
var fichas = [];

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
			partidaLista();
		}
	};
	var p = {
		nombre : localStorage.nombre,
		email: localStorage.email
	};
	request.send("p=" + JSON.stringify(p));
	
}

function partidaLista() {
	var request = new XMLHttpRequest();	
	request.open("post", "servers/partidaReady.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.send();
}

function conectarWebSocket() {
	ws=new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/OCA/servidorDePartidas");
	
	ws.onopen = function() {
		addMensaje("<b>OCA: </b>Conectado");
	}
	
	ws.onmessage = function(datos) {
		var mensaje=datos.data;
		mensaje=JSON.parse(mensaje);
		if (mensaje.tipo=="DIFUSION") {
			addMensaje(mensaje.mensaje);
		} else 
			if (mensaje.tipo=="COMIENZO") {
			addMensaje("<b>OCA: </b>Comienza la partida");
			comenzar(mensaje);
		} else {
			console.log(mensaje.tipo);
			console.log(mensaje.mensaje);
		}
	}	
}

function comenzar(mensaje) {
	var lienzoficha = document.getElementById("casilla0");
	
	fichas.push(new Ficha(1, lienzoficha));
	document.getElementById("jg1").innerHTML = mensaje.Jugador1;
	
	fichas.push(new Ficha(2, lienzoficha));
	document.getElementById("jg2").innerHTML = mensaje.Jugador2;
	
	fichas.push(new Ficha(3, lienzoficha));
	document.getElementById("jg3").innerHTML = mensaje.Jugador3;
	
	fichas.push(new Ficha(4, lienzoficha));
	document.getElementById("jg4").innerHTML = mensaje.Jugador4;

	var btnDado=document.getElementById("lanzarDado");
	if (mensaje.jugadorConElTurno==localStorage.nombre)
		btnDado.disabled = false;
	 else 
		btnDado.disabled = true;
	
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
		}
	};
	var p = {
		usuario : localStorage.getItem("nombre"),
		msg : texto,
		tipo : tipo
	};
	request.send("p=" + JSON.stringify(p));
}

function addMensaje(texto) {
	var div=document.createElement("div");
	divChat.appendChild(div);
	div.innerHTML=texto;
}