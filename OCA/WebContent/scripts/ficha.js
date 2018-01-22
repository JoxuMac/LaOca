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
function Ficha(idficha) {
	this.idficha = idficha;
	this.idcasilla = 0;
	pintarFicha(idficha, 0);
}

// Pintar ficha en una casilla
function pintarFicha(idficha, casilla){
	this.circulo = document.createElementNS("http://www.w3.org/2000/svg", "circle");
	var cx;
	var cy;
	if(casilla==0){
		cy = 530;
		cx = 15;
		if(idficha==2)
			cx += 23;
		else
			if(idficha==3)
				cx += 46;
			else
				if(idficha==4)
					cx += 69;
	}
	
	if(casilla>0 && casilla<10){
		cy = 540;
		cx = 60+50*casilla;
		if(idficha==2)
			cx += 30;
		else
			if(idficha==3)
				cy -= 30;
			else
				if(idficha==4){
					cy -= 30;
					cx += 30;
				}
	}
	

	if(casilla>9 && casilla<20){
		cy = 490 - 50*(casilla-10)
		//cy = 40 - 50*casilla;
		cx = 510;
		if(idficha==2)
			cx += 30;
		else
			if(idficha==3)
				cy -= 30;
			else
				if(idficha==4){
					cy -= 30;
					cx += 30;
				}
	}
	
	switch(idficha){
		case 1:
			this.circulo.setAttribute("fill", "purple");
			break;
		case 2:
			this.circulo.setAttribute("fill", "red");
			break;
		case 3:
			this.circulo.setAttribute("fill", "blue");
			break;
		case 4:
			this.circulo.setAttribute("fill", "green");
			break;
	}
	this.circulo.setAttribute("cx", cx);
	this.circulo.setAttribute("cy", cy);
	this.circulo.setAttribute("r", 10);
	this.circulo.setAttribute("id", "ficha"+idficha);
	this.circulo.setAttribute("stroke", "black");
	dibujar(document.getElementById("casilla"+casilla), this.circulo);
}

// Dibujar en Lienzo
function dibujar(lienzo, g) {
	lienzo.appendChild(g);
}

//Nueva Tirada de Dado
Ficha.prototype.moverFicha = function(tiradanueva) {
	this.idcasilla += tiradanueva;
	
	document.getElementById("ficha"+this.idficha).remove()
	
	pintarFicha(this.idficha, this.idcasilla);
}
