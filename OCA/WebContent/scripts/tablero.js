function Tablero() {
	this.casillas=[];
	this.crearCasillas();
}

Tablero.prototype.dibujar = function(lienzo) {
	for (var i=0; i<63; i++)
		this.casillas[i].dibujar(lienzo);
}

Tablero.prototype.crearCasillas = function() {
	for (var i=0; i<63; i++) {
		var casilla=new Casilla();
		this.casillas.push(casilla);
	}
	var ocas= [4, 8, 13, 17, 22, 26, 31, 35, 40, 44, 49, 53, 58];
	for (var i=0; i<ocas.length; i++)
		this.casillas[ocas[i]].tipo="OCA";
	

	var count = 0;
	
	// filas
	for(var j=0; j<8;j++){
		//columnas
		for(var i=0; i<10;i++){//8
			// casilla inicial
			if(count<1){
				this.casillas[count].x0=i*50; //x
				this.casillas[count].y0=450-(j*50); //y
				this.casillas[count].xF=this.casillas[count].x0+50;
				this.casillas[count].yF=this.casillas[count].y0+50;
				this.casillas[count].crearCirculo(100, 50);
				i=1;
			}
			
			// resto de casillas
			if(count<63 && count>=1){
				this.casillas[count].x0=i*50; //x
				this.casillas[count].y0=450-(j*50); //y
				this.casillas[count].xF=this.casillas[count].x0+50;
				this.casillas[count].yF=this.casillas[count].y0+50;
				this.casillas[count].crearCirculo(50, 50);
			}
			count = count + 1;
		}
	}		
}

function Casilla() {
	this.tipo="NORMAL";
}

Casilla.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.rectangulo);
}

Casilla.prototype.crearCirculo = function(width, heigth) {
	this.rectangulo=document.createElementNS("http://www.w3.org/2000/svg", "rect");
	this.rectangulo.setAttribute("x", this.x0);
	this.rectangulo.setAttribute("y", this.y0);
	this.rectangulo.setAttribute("width", width);
	this.rectangulo.setAttribute("height", heigth);
	this.rectangulo.setAttribute("stroke", "green");
	this.rectangulo.setAttribute("stroke-width", "4");
	this.rectangulo.setAttribute("fill", "rgb(100,100,100)");
}


