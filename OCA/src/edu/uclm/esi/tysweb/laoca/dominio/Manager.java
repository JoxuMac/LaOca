package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.concurrent.ConcurrentHashMap;

import edu.uclm.esi.tysweb.laoca.dao.Broker;

public class Manager {
	private ConcurrentHashMap<String, Usuario> usuarios;
	private ConcurrentHashMap<Integer, Partida> partidasPendientes;
	private ConcurrentHashMap<Integer, Partida> partidasEnJuego;
	
	private Manager() {
		this.usuarios=new ConcurrentHashMap<>();
		this.partidasPendientes=new ConcurrentHashMap<>();
		this.partidasEnJuego=new ConcurrentHashMap<>();
		Broker.get();
	}
	
	public int crearPartida(String nombreJugador, int numeroDeJugadores) throws Exception {
		Usuario usuario = findUsuario(nombreJugador);

		Partida partida=new Partida(usuario, numeroDeJugadores);
		this.partidasPendientes.put(partida.getId(), partida);
		return partida.getId();
	}

	private Usuario findUsuario(String nombreJugador) throws Exception {
		Usuario usuario=this.usuarios.get(nombreJugador);
		if (usuario==null) {
			usuario=new Usuario(nombreJugador);
			this.usuarios.put(nombreJugador, usuario);
		}
		return usuario;
	}
	
	public void addJugador(String nombreJugador) throws Exception {
		if (this.partidasPendientes.isEmpty())
			throw new Exception("No hay partidas pendientes. Crea una, pendejo");
		Partida partida=this.partidasPendientes.elements().nextElement();
		Usuario usuario=findUsuario(nombreJugador);
		partida.add(usuario);
		if (partida.isReady()) {
			this.partidasPendientes.remove(partida.getId());
			this.partidasEnJuego.put(partida.getId(), partida);
		}
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
}