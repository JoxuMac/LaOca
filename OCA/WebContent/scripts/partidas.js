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
			} else 
				if (mensaje.tipo=="DADO") {
					console.log(mensaje.tipo);
					console.log(mensaje.mensaje);
					
					///// EN CONSTRUCCION /////
					var request = new XMLHttpRequest();	
					request.open("post", "servers/getJugadorTurno.jsp");
					request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
					request.onreadystatechange=function() {
						if (request.readyState==4) {
							var respuesta=JSON.parse(request.responseText);
							comprobarTurno(respuesta.mensaje);
							var idJugador;
							if(respuesta.mensaje==document.getElementById("jg1").innerHTML){
								idJugador=3;
								console.log("jg4");
						}
							if(respuesta.mensaje==document.getElementById("jg2").innerHTML){
								idJugador=0;
								console.log("jg1");
							}
								if(respuesta.mensaje==document.getElementById("jg3").innerHTML){
								idJugador=1;
								console.log("jg2");
							}
								if(respuesta.mensaje==document.getElementById("jg4").innerHTML){
								idJugador=2;
								console.log("jg3");
							}
							
							console.log("mensaje"+mensaje.mensaje);
							
							//fichas[idJugador].moverFicha(mensaje.mensaje);
						}
					};
					var p = {
						partida : sessionStorage.idPartida
					};
					request.send("p=" + JSON.stringify(p));
				} else {
					console.log(mensaje.tipo);
					console.log(mensaje.mensaje);
					console.log(mensaje.jugadores);
				}
	}	
}

function comenzar(mensaje) {
	//var lienzoficha = document.getElementById("casilla0");
	//console.log(mensaje.jugadores[0]);
	fichas.push(new Ficha(1));
	document.getElementById("jg1").innerHTML = mensaje.jugadores[0];
	
	fichas.push(new Ficha(2));
	document.getElementById("jg2").innerHTML = mensaje.jugadores[1];
	
	fichas.push(new Ficha(3));
	document.getElementById("jg3").innerHTML = mensaje.jugadores[2];
	
	fichas.push(new Ficha(4));
	document.getElementById("jg4").innerHTML = mensaje.jugadores[3];

	sessionStorage.idPartida=mensaje.idPartida;
	
	comprobarTurno(mensaje);
}

function comprobarTurno(mensaje) {
	var btnDado=document.getElementById("lanzarDado");
	if (mensaje.jugadorConElTurno==localStorage.nombre)
		btnDado.disabled = false;
	 else 
		btnDado.disabled = true;
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