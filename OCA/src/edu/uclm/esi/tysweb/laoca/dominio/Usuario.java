/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
*/

package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.IOException;

import javax.websocket.Session;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUsuario;

public class Usuario {
	protected String email;
	protected String nombre;
	protected Partida partida;
	protected int score;
	protected String photo;

	private Session session;
	private Casilla casilla;
	private int turnosSinTirar;
	private String token;

	public Usuario(String nombreJugador) throws Exception {
		//if (!DAOUsuario.existe(nombreJugador))
		//	throw new Exception("Usuario no registrado");
		this.nombre=nombreJugador;
	}

	public Usuario() {
	}

	public String geteMail() {
		return this.email;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setPartida(Partida partida) {
		this.partida=partida;
		if (partida!=null)
			partida.addJugador(this);
	}
	
	public Partida getPartida() {
		return partida;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo=photo;
	}

	public void setWSSession(Session sesion) {
		this.session=sesion;
	}

	public void seteMail(String email) {
		this.email=email;
	}
	
	public void setNombre(String user) {
		this.nombre=user;
	}

	public void insert(String pwd) throws Exception {
		DAOUsuario.insert(this, pwd);
	}
	public void insertToken(String token) throws Exception {
		DAOUsuario.insertToken(this, token);
	}

	public void enviar(JSONObject jso) throws IOException {
		this.session.getBasicRemote().sendText(jso.toString());
	}

	public Casilla getCasilla() {
		return this.casilla;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	public int getTurnosSinTirar() {
		return this.turnosSinTirar;
	}
	
	public void setTurnosSinTirar(int turnosSinTirar) {
		this.turnosSinTirar = turnosSinTirar;
	}
	
	@Override
	public String toString() {
		return this.nombre + " jugando en " + (this.partida!=null ? this.partida.getId() : "ninguna ") + ", " + this.casilla.getPos() + ", turnos: " + this.turnosSinTirar;
	}

	public void setScore(int score) throws Exception {
		this.score += score;
		DAOUsuario.setScore(this.email, this.score);
	}
	
	public int getScore() {
		return this.score;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token =token;
	}
}
