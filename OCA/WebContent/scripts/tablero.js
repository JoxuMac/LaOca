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
	

	var aux = 550;
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
		if(count>0 && count<10){
			this.casillas[count].x0=(count+1)*50; //x
			this.casillas[count].y0=500; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 11 - 20
		if(count>9 && count<20){
			this.casillas[count].x0=500; //x
			this.casillas[count].y0=950-count*50; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 21 - 30
		if(count>19 && count<30){
			this.casillas[count].x0=((count*50)-aux); //x
			aux = aux + 100;
			this.casillas[count].y0=0; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
			if(aux == 5500){aux = 50}
		}
		
		// casillas 31 - 38
		if(count>29 && count<38){
			this.casillas[count].x0=0; //x
			this.casillas[count].y0=aux-1500; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
			aux = aux + 50;
		}
		
		// casillas 39 - 46
		if(count>37 && count<46){
			this.casillas[count].x0=((count+1)*50)-1900; //x
			this.casillas[count].y0=400; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 47 - 52
		if(count>45 && count<52){
			this.casillas[count].x0=400; //x
			this.casillas[count].y0=2650-count*50; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 53 - 58
		if(count>51 && count<58){
			this.casillas[count].x0=2950-count*50; //x
			this.casillas[count].y0=100; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
		}
		
		// casillas 59 - 62
		if(count>57 && count<62){
			this.casillas[count].x0=100; //x
			this.casillas[count].y0=aux-1800; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(50, 50, count);
			aux = aux + 50;
		}
		
		// casillas 63
		if(count>61){
			this.casillas[count].x0=150; //x
			this.casillas[count].y0=200; //y
			this.casillas[count].xF=this.casillas[count].x0+50;
			this.casillas[count].yF=this.casillas[count].y0+50;
			this.casillas[count].crearCirculo(200, 150, count);
		}
	}
}


function Casilla() {
	this.tipo="NORMAL";
}

Casilla.prototype.dibujar = function(lienzo) {
	lienzo.appendChild(this.g);
}

Casilla.prototype.crearCirculo = function(width, heigth, casill) {
	
	this.g=document.createElementNS("http://www.w3.org/2000/svg", "g");
	
	this.rectangulo=document.createElementNS("http://www.w3.org/2000/svg", "rect");
	this.rectangulo.setAttribute("x", this.x0);
	this.rectangulo.setAttribute("y", this.y0);
	this.rectangulo.setAttribute("width", width);
	this.rectangulo.setAttribute("height", heigth);
	this.rectangulo.setAttribute("stroke", "rgb(200,93,93)");
	this.rectangulo.setAttribute("stroke-width", "4");
	this.rectangulo.setAttribute("fill", "rgb(220,220,220)");
	this.g.appendChild(this.rectangulo);
	
	this.numero=document.createElementNS("http://www.w3.org/2000/svg", "text");
	this.numero.setAttribute("x", this.x0+2);
	this.numero.setAttribute("y", this.y0+15);
	this.numero.setAttribute("fill", "black");
	this.numero.setAttribute("font-weight", "bold");
	this.numero.setAttribute("font-family", "Arial");
	this.numero.innerHTML=(casill+1);
	this.g.appendChild(this.numero);
	
	//if((this.casillas[casill]).tipo=="OCA"){
		this.img=document.createElementNS("http://www.w3.org/2000/svg", "image");
		this.img.setAttribute("x", this.x0+5);
		this.img.setAttribute("y", this.y0+10);
		this.img.setAttribute("href", "./images/oca2.png");
		this.img.setAttribute("height", "40");
		this.img.setAttribute("width", "40");
		this.g.appendChild(this.img);
	//}
	
}

