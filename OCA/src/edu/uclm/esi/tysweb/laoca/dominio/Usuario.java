package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.dao.DAOUsuario;

public class Usuario {
	private String login;

	public Usuario(String nombreJugador) throws Exception {
		if (!DAOUsuario.existe(nombreJugador))
			throw new Exception("Usuario no registrado");
		this.login=nombreJugador;
	}

	public String getLogin() {
		return this.login;
	}

}