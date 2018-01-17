/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

// Creacion de Ficha
function Ficha(idficha, lienzo) {
	this.idficha = idficha;
	switch(this.idficha){
		case 1:
			pintarFicha1(lienzo);
			break;
		case 2:
			pintarFicha2(lienzo);
			break;
		case 3:
			pintarFicha3(lienzo);
			break;
		case 4:
			pintarFicha4(lienzo);
			break;
	}
}

// Nueva Tirada de Dado
Ficha.prototype.moverFicha = function(lienzo, tiradanueva, idficha) {
	
	this.idcasilla += tiradanueva;
	
	console.log(this.idcasilla);
	
	this.g = document.createElementNS("http://www.w3.org/2000/svg", "g");
	

	// blue
	// green
	// purple
	//red
	
	//this.g.appendChild(this.circulo);
	
	dibujar(lienzo, this.circulo);
}

//Pintar Ficha 1
function pintarFicha1(lienzo){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", 15);
	this.circulo.setAttribute("cy", 530);
	this.circulo.setAttribute("r", 10);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "purple");
	dibujar(lienzo, this.circulo);
}

//Pintar Ficha 2
function pintarFicha2(lienzo){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", 38);
	this.circulo.setAttribute("cy", 530);
	this.circulo.setAttribute("r", 10);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "red");
	dibujar(lienzo, this.circulo);
}

//Pintar Ficha 3
function pintarFicha3(lienzo){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", 61);
	this.circulo.setAttribute("cy", 530);
	this.circulo.setAttribute("r", 10);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "blue");
	dibujar(lienzo, this.circulo);
}

// Pintar Ficha 4
function pintarFicha4(lienzo){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	this.circulo.setAttribute("cx", 84);
	this.circulo.setAttribute("cy", 530);
	this.circulo.setAttribute("r", 10);
	this.circulo.setAttribute("stroke", "black");
	this.circulo.setAttribute("fill", "green");
	dibujar(lienzo, this.circulo);
}

// Dibujar en Lienzo
function dibujar(lienzo, g) {
	lienzo.appendChild(g);
}