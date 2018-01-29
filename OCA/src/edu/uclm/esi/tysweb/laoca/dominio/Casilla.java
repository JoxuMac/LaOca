/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.dominio;

public class Casilla {
	private int posicion;
	private Casilla siguiente;
	private String mensaje;
	private int turnosSinTirar;
	
	Casilla(int posicion) {
		this.posicion=posicion;
		this.siguiente=null;
	}

	public int getPos() {
		return this.posicion;
	}

	void setSiguiente(Casilla siguiente) {
		this.siguiente = siguiente;
	}
	
	void setMensaje(String mensaje) {
		this.mensaje=mensaje;
	}

	Casilla getSiguiente() {
		return this.siguiente;
	}

	String getMensaje() {
		return this.mensaje;
	}

	void setTurnosSinTirar(int n) {
		this.turnosSinTirar=n;
	}
	
	int getTurnosSinTirar() {
		return turnosSinTirar;
	}
}
