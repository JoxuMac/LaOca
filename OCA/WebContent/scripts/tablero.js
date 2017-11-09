function Tablero() {
	this.casillas=[];
	this.crearCasillas();
}

Tablero.prototype.dibujar = function(lienzo) {
	for (var i=0; i<15; i++)
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
	

	for(var count=0; count<63; count++){
		
		// casilla 1
		if(count<1){
			this.casillas[count].x0=0; //x
			this.casillas[count].y0=500; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(100, 50, count);
		}
		
		// casillas 2 - 10
		if(count>0 && count<11){
			this.casillas[count].x0=(count+1)*50; //x
			this.casillas[count].y0=500; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 11 - 20
		if(count>9 && count<16){
			this.casillas[count].x0=500; //x
			this.casillas[count].y0=950-count*50; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 21 - 30
		
		// casillas 31 - 38
		
		// casillas 39 - 46
		
		// casillas 47 - 52
		
		// casillas 53 - 58
		
		// casillas 59 - 62
		
		// casillas 63

	}
}


function Casilla() {
	this.tipo="NORMAL";
}

Casilla.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.g);
}

Casilla.prototype.crearCirculo = function(width, heigth, casill) {
	
	this.rectangulo=document.createElementNS("http://www.w3.org/2000/svg", "rect");
	this.rectangulo.setAttribute("x", this.x0);
	this.rectangulo.setAttribute("y", this.y0);
	this.rectangulo.setAttribute("width", width);
	this.rectangulo.setAttribute("height", heigth);
	this.rectangulo.setAttribute("stroke", "green");
	this.rectangulo.setAttribute("stroke-width", "4");
	this.rectangulo.setAttribute("fill", "rgb(100,100,100)");
	
	this.numero=document.createElementNS("http://www.w3.org/2000/svg", "text");
	this.numero.setAttribute("x", this.x0+2);
	this.numero.setAttribute("y", this.y0+15);
	this.numero.setAttribute("fill", "white");
	this.numero.innerHTML=(casill+1);
	
	this.g=document.createElementNS("http://www.w3.org/2000/svg", "g");
	
	this.g.appendChild(this.rectangulo);
	this.g.appendChild(this.numero);
	
}

