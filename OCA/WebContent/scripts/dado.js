/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

// Creacion de Dado
function Dado(x, y) {
	this.x = x;
	this.y = y;
}

// Nueva Tirada de Dado
Dado.prototype.realizarTidada = function(lienzo) {
	
	// Tirada entre 1 y 6
	var tirada = Math.floor(Math.random() * 6) + 1;
	
	console.log("TIRADA: "+tirada);
	
	// Grupo
	this.g = document.createElementNS("http://www.w3.org/2000/svg", "g");

	// Puntos del dado
	switch(tirada){
		case 1:
			this.g.appendChild(dibujarCentroCentro(this.x, this.y));
			break;
		case 2:
			this.g.appendChild(dibujarCentroIzquierda(this.x, this.y));
			this.g.appendChild(dibujarCentroDerecha(this.x, this.y));
			break;
		case 3:
			this.g.appendChild(dibujarArribaIzquierda(this.x, this.y));
			this.g.appendChild(dibujarCentroCentro(this.x, this.y));
			this.g.appendChild(dibujarAbajoDerecha(this.x, this.y));
			break;
		case 4:
			this.g.appendChild(dibujarArribaIzquierda(this.x, this.y));
			this.g.appendChild(dibujarArribaDerecha(this.x, this.y));
			this.g.appendChild(dibujarAbajoIzquierda(this.x, this.y));
			this.g.appendChild(dibujarAbajoDerecha(this.x, this.y));
			break;
		case 5:
			this.g.appendChild(dibujarArribaIzquierda(this.x, this.y));
			this.g.appendChild(dibujarArribaDerecha(this.x, this.y));
			this.g.appendChild(dibujarCentroCentro(this.x, this.y));
			this.g.appendChild(dibujarAbajoIzquierda(this.x, this.y));
			this.g.appendChild(dibujarAbajoDerecha(this.x, this.y));
			break;
		case 6:
			this.g.appendChild(dibujarArribaIzquierda(this.x, this.y));
			this.g.appendChild(dibujarArribaDerecha(this.x, this.y));
			this.g.appendChild(dibujarCentroIzquierda(this.x, this.y));
			this.g.appendChild(dibujarCentroDerecha(this.x, this.y));
			this.g.appendChild(dibujarAbajoIzquierda(this.x, this.y));
			this.g.appendChild(dibujarAbajoDerecha(this.x, this.y));
			break;
	}
	
	dibujar(lienzo, this.g);
}

// Circulo Arriba Derecha
function dibujarArribaDerecha(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x*3/4);
	this.circulo.setAttribute("cy", y/4);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo ;
}

// Circulo Arriba Izquierda
function dibujarArribaIzquierda(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x/4);
	this.circulo.setAttribute("cy", y/4);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo;
}

// Circulo Centro Izquierda
function dibujarCentroIzquierda(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x/4);
	this.circulo.setAttribute("cy", y/2);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo;
}

// Circulo Centro Derecha
function dibujarCentroDerecha(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x*3/4);
	this.circulo.setAttribute("cy", y/2);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo ;
}

// Circulo Central
function dibujarCentroCentro(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x/2);
	this.circulo.setAttribute("cy", y/2);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo;
}

// Circulo Abajo Derecha
function dibujarAbajoDerecha(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x*3/4);
	this.circulo.setAttribute("cy", y*3/4);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo;
}

// Circulo Abajo Izquierda
function dibujarAbajoIzquierda(x, y){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", x/4);
	this.circulo.setAttribute("cy", y*3/4);
	this.circulo.setAttribute("r", 20);
	this.circulo.setAttribute("width", 20);
	this.circulo.setAttribute("height", 20);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "white");
	
	return this.circulo;
}

// Dibujar en Lienzo
function dibujar(lienzo, g) {
	lienzo.appendChild(g);
}