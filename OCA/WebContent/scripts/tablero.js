/**
 * LA OCA - 2017 - Tecnologias y Sistemas Web 
 * Escuela Superior de Informatica de Ciudad Real
 * 
 * Josue Gutierrez Duran 
 * Sonia Querencia Martin 
 * Enrique Simarro Santamaria
 * Eduardo Fuentes Garcia De Blas
 */

// Creacion de Tablero
function Tablero() {
	this.casillas = [];
	this.crearCasillas();
}

// Dibujo de Casillas
Tablero.prototype.dibujar = function(lienzo) {
	for (var i = 0; i < 63; i++)
		this.casillas[i].dibujar(lienzo);
}

// Creacion de Casillas
Tablero.prototype.crearCasillas = function() {
	// Creacion Casillas
	for (var i = 0; i < 63; i++) {
		var casilla = new Casilla();
		this.casillas.push(casilla);
	}

	// Casillas OCAS
	var ocas = [ 4, 8, 13, 17, 22, 26, 31, 35, 40, 44, 49, 53, 58 ];
	for (var i = 0; i < ocas.length; i++)
		this.casillas[ocas[i]].tipo = "OCA";

	// Casillas DADOS
	var ocas = [ 25, 52 ];
	for (var i = 0; i < ocas.length; i++)
		this.casillas[ocas[i]].tipo = "DADO";

	// Casillas PUENTES
	var ocas = [ 5, 11 ];
	for (var i = 0; i < ocas.length; i++)
		this.casillas[ocas[i]].tipo = "PUENTE";

	// Casillas POSADA
	this.casillas[18].tipo = "POSADA";

	// Casillas POZO
	this.casillas[30].tipo = "POZO";

	// Casillas LABERINTO
	this.casillas[41].tipo = "LABERINTO";

	// Casillas Carcel
	this.casillas[55].tipo = "CARCEL";

	// Casillas Calavera
	this.casillas[57].tipo = "CALAVERA";

	// Casillas FIN
	this.casillas[62].tipo = "FIN";

	// Casillas INICIO
	this.casillas[0].tipo = "INICIO";

	// CREACION TABLERO
	var aux = 550;
	for (var count = 0; count < 63; count++) {

		// casilla 1
		if (count < 1) {
			this.casillas[count].x0 = 0; // x
			this.casillas[count].y0 = 500; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(100, 50, count, this.casillas);
		}

		// casillas 2 - 10
		if (count > 0 && count < 10) {
			this.casillas[count].x0 = (count + 1) * 50; // x
			this.casillas[count].y0 = 500; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
		}

		// casillas 11 - 20
		if (count > 9 && count < 20) {
			this.casillas[count].x0 = 500; // x
			this.casillas[count].y0 = 950 - count * 50; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
		}

		// casillas 21 - 30
		if (count > 19 && count < 30) {
			this.casillas[count].x0 = ((count * 50) - aux); // x
			aux = aux + 100;
			this.casillas[count].y0 = 0; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
			if (aux == 5500) {
				aux = 50
			}
		}

		// casillas 31 - 38
		if (count > 29 && count < 38) {
			this.casillas[count].x0 = 0; // x
			this.casillas[count].y0 = aux - 1500; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
			aux = aux + 50;
		}

		// casillas 39 - 46
		if (count > 37 && count < 46) {
			this.casillas[count].x0 = ((count + 1) * 50) - 1900; // x
			this.casillas[count].y0 = 400; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
		}

		// casillas 47 - 52
		if (count > 45 && count < 52) {
			this.casillas[count].x0 = 400; // x
			this.casillas[count].y0 = 2650 - count * 50; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
		}

		// casillas 53 - 58
		if (count > 51 && count < 58) {
			this.casillas[count].x0 = 2950 - count * 50; // x
			this.casillas[count].y0 = 100; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
		}

		// casillas 59 - 62
		if (count > 57 && count < 62) {
			this.casillas[count].x0 = 100; // x
			this.casillas[count].y0 = aux - 1800; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(50, 50, count, this.casillas);
			aux = aux + 50;
		}

		// casillas 63
		if (count > 61) {
			this.casillas[count].x0 = 150; // x
			this.casillas[count].y0 = 200; // y
			this.casillas[count].xF = this.casillas[count].x0 + 50;
			this.casillas[count].yF = this.casillas[count].y0 + 50;
			this.casillas[count].crearCirculo(200, 150, count, this.casillas);
		}
	}
}

// Casillas Normal
function Casilla() {
	this.tipo = "NORMAL";
}

// Dibujar en Lienzo
Casilla.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.g);
}

// Diseño de Casillas
Casilla.prototype.crearCirculo = function(width, heigth, casill, casillas) {

	// Grupo
	this.g = document.createElementNS("http://www.w3.org/2000/svg", "g");
	this.g.setAttribute("id", "casilla"+casill);

	// Rectangulo
	this.rectangulo = document.createElementNS("http://www.w3.org/2000/svg",
			"rect");
	this.rectangulo.setAttribute("x", this.x0);
	this.rectangulo.setAttribute("y", this.y0);
	this.rectangulo.setAttribute("width", width);
	this.rectangulo.setAttribute("height", heigth);
	this.rectangulo.setAttribute("stroke", "rgb(200,93,93)");
	this.rectangulo.setAttribute("stroke-width", "4");
	this.rectangulo.setAttribute("fill", "rgb(220,220,220)");
	this.g.appendChild(this.rectangulo);

	// Texto de Casilla
	this.numero = document
			.createElementNS("http://www.w3.org/2000/svg", "text");
	this.numero.setAttribute("x", this.x0 + 2);
	this.numero.setAttribute("y", this.y0 + 15);
	this.numero.setAttribute("fill", "black");
	this.numero.setAttribute("font-weight", "bold");
	this.numero.setAttribute("font-family", "Arial");
	this.numero.innerHTML = (casill);
	this.g.appendChild(this.numero);

	// Imagen Casilla
	this.img = document.createElementNS("http://www.w3.org/2000/svg", "image");
	this.img.setAttribute("x", this.x0 + 5);
	this.img.setAttribute("y", this.y0 + 10);
	this.img.setAttribute("height", "40");
	this.img.setAttribute("width", "40");
	switch (casillas[casill].tipo) {
	case "OCA":
		this.img.setAttribute("href", "./images/oca2.png");
		break;
	case "CALAVERA":
		this.img.setAttribute("href", "./images/calavera.svg");
		this.img.setAttribute("x", this.x0 + 10);
		this.img.setAttribute("height", "35");
		this.img.setAttribute("width", "35");
		break;
	case "FIN":
		this.img.setAttribute("href", "./images/oca1.png");
		this.img.setAttribute("height", "160");
		this.img.setAttribute("width", "160");
		break;
	case "DADO":
		this.img.setAttribute("href", "./images/dado.png");
		this.img.setAttribute("x", this.x0 + 11);
		this.img.setAttribute("height", "35");
		this.img.setAttribute("width", "35");
		break;
	case "POZO":
		this.img.setAttribute("href", "./images/pozo.png");
		this.img.setAttribute("x", this.x0 + 11);
		this.img.setAttribute("y", this.y0 + 5);
		break;
	case "CARCEL":
		this.img.setAttribute("href", "./images/carcel.png");
		this.img.setAttribute("height", "35");
		this.img.setAttribute("width", "35");
		this.img.setAttribute("x", this.x0 + 11);
		break;
	case "INICIO":
		this.img.setAttribute("href", "./images/puente.png");
		this.img.setAttribute("height", "45");
		this.img.setAttribute("width", "90");
		this.img.setAttribute("y", this.y0 + 1);
		break;
	case "PUENTE":
		this.img.setAttribute("href", "./images/puente.png");
		break;
	case "POSADA":
		this.img.setAttribute("href", "./images/posada.png");
		this.img.setAttribute("height", "35");
		this.img.setAttribute("width", "35");
		this.img.setAttribute("x", this.x0 + 11);
		break;
	case "LABERINTO":
		this.img.setAttribute("href", "./images/laberinto.png");
		this.img.setAttribute("height", "30");
		this.img.setAttribute("width", "30");
		this.img.setAttribute("y", this.y0 + 15);
		this.img.setAttribute("x", this.x0 + 15);
		break;
	}
	this.g.appendChild(this.img);

}