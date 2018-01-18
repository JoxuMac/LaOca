package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUsuario;

public class Manager {
	private ConcurrentHashMap<String, Usuario> usuarios;
	private ConcurrentHashMap<Integer, Partida> partidasPendientes;
	private ConcurrentHashMap<Integer, Partida> partidasEnJuego;
	private String webAppPath;
	
	private Manager() {
		this.usuarios=new ConcurrentHashMap<>();
		this.partidasPendientes=new ConcurrentHashMap<>();
		this.partidasEnJuego=new ConcurrentHashMap<>();
	}
	
	public Usuario crearPartida(String nombreJugador, int numeroDeJugadores) throws Exception {
		Usuario usuario = findUsuario(nombreJugador);
		if (usuario.getPartida()!=null)
			throw new Exception("El usuario ya está asociado a una partida. Desconéctate para crear una nueva o unirte a otra");
			//usuario.setPartida(null);
		
		Partida partida=new Partida(usuario, numeroDeJugadores);
		usuario.setPartida(partida);
		this.partidasPendientes.put(partida.getId(), partida);
		return usuario;
	}
	
	public void broadcast(String nombreJugador, JSONObject msg) throws Exception {
		Usuario usuario = findUsuario(nombreJugador);
		Partida partida = usuario.getPartida();
		if(msg.getString("tipo").equals("DADO"))
			partida.tirarDado(nombreJugador, Integer.parseInt(msg.getString("msg")));
		else
			partida.broadcast(msg);
		
		
			//throw new Exception("El usuario ya está asociado a una partida. Desconéctate para crear una nueva o unirte a otra");
		//	usuario.setPartida(null);
		
		//if (this.partidasPendientes.isEmpty()) {
			// NO HAY PARTIDAS - SE CREA UNA NUEVA
		//	System.out.println("SE CREA UNA PARTIDA");
		//	partida = new Partida(usuario, 4);
		//	usuario.setPartida(partida);
		//	this.partidasPendientes.put(partida.getId(), partida);
			
		//}else{
			// HAY PARTIDAS - SE UNE A UNA
		//	System.out.println("SE UNE A UNA PARTIDA");
		//	partida=this.partidasPendientes.elements().nextElement();
		//	partida.add(usuario);
		//	usuario.setPartida(partida);
			
		//}
	
		//lanzarPartidas();
		
		
	}

	private Usuario findUsuario(String nombreJugador) throws Exception {
		Usuario usuario=this.usuarios.get(nombreJugador);
		if (usuario==null) {
			usuario=new Usuario(nombreJugador);
			this.usuarios.put(nombreJugador, usuario);
		}
		return usuario;
	}
		
	public Usuario addJugador(String nombreJugador) throws Exception {
		if (this.partidasPendientes.isEmpty())
			throw new Exception("No hay partidas pendientes. Crea una, pendejo");
		
		Partida partida=this.partidasPendientes.elements().nextElement();
		Usuario usuario=findUsuario(nombreJugador);
		
		if (usuario.getPartida()!=null) 
			throw new Exception("El usuario ya esta asociado a una partida. Desconectate para crear una nueva o unirte a otra");
		
		partida.add(usuario);
		usuario.setPartida(partida);
		if (partida.isReady()) {
			this.partidasPendientes.remove(partida.getId());
			this.partidasEnJuego.put(partida.getId(), partida);
		}
		return usuario;
	}
	
	public void setWebAppPath(String webAppPath) {
		this.webAppPath=webAppPath;
		if (!this.webAppPath.endsWith(File.separator))
			this.webAppPath+=File.separator;
	}
	
	public String getWebAppPath() {
		return webAppPath;
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	public void registrar(String email, String pwd, String user) throws Exception {
		Usuario usuario=new Usuario();
		usuario.seteMail(email);
		usuario.setNombre(user);
		usuario.setScore(0);
		usuario.setPhoto("profile/default.png");
		usuario.insert(pwd);
	}
	
	public Usuario login(String email, String pwd) throws Exception {
		return DAOUsuario.login(email, pwd);
	}
	
	public void changePass(String email, String pwd_old, String pwd1) throws Exception {
		DAOUsuario.changePassword(email, pwd_old, pwd1);
	}
	
	public void saveToken(String email, TokenRecuperacionPwd token) throws Exception {
		DAOUsuario.saveToken(email, token);
	}
	
	public void changePhoto(String email, String photo) throws Exception {
		DAOUsuario.changePhoto(email, photo);
	}
	
	public String getPhoto(String email) throws Exception {
		System.out.println(email);
		return DAOUsuario.getPhoto(email);
	}

	public JSONObject tirarDado(int idPartida, String jugador, int dado) throws Exception {
		Partida partida=this.partidasEnJuego.get(idPartida);
		JSONObject mensaje=partida.tirarDado(jugador, dado);
		mensaje.put("idPartida", idPartida);
		mensaje.put("jugador", jugador);
		partida.broadcast(mensaje);
		if (mensaje!=null && mensaje.opt("ganador")!=null) {
			terminar(partida);
		}
		return mensaje;
	}

	private void terminar(Partida partida) {
		partida.terminar();
		partidasEnJuego.remove(partida.getId());
	}
	
	public void enviarToken(String email, String url) throws Exception {
		long n= new java.util.Random().nextLong();
		if(n<0)
			n=-n;
		
		TokenRecuperacionPwd token = new TokenRecuperacionPwd(email, n);
		Manager.get().saveToken(email, token);
		// guardarlo en la BD asociado al email y ponerle 10 minutos de caducidad
	
		EmailSenderService ess = new EmailSenderService();
		ess.enviarPorGmail(email, n, url);
	}
	
	public Usuario jugar(String nombreJugador, String emailJugador) throws Exception {
		Usuario usuario = findUsuario(nombreJugador);
		Partida partida;
		// SI ESTABA EN UNA PARTIDA SE LE ECHA Y SE LE BUSCA UNA NUEVA
		if (usuario.getPartida()!=null)
			//throw new Exception("El usuario ya está asociado a una partida. Desconéctate para crear una nueva o unirte a otra");
			usuario.setPartida(null);
		
		if (this.partidasPendientes.isEmpty()) {
			// NO HAY PARTIDAS - SE CREA UNA NUEVA
			System.out.println("SE CREA UNA PARTIDA");
			partida = new Partida(usuario, 4);
			usuario.setPartida(partida);
			this.partidasPendientes.put(partida.getId(), partida);
			
		}else{
			// HAY PARTIDAS - SE UNE A UNA
			System.out.println("SE UNE A UNA PARTIDA");
			partida=this.partidasPendientes.elements().nextElement();
			partida.add(usuario);
			usuario.setPartida(partida);
			
		}
	
		lanzarPartidas();
		return usuario;
	}

	private void lanzarPartidas() {
		for (Integer key : partidasPendientes.keySet()) {
			if (partidasPendientes.get(key).isReady()) {
				this.partidasEnJuego.put(partidasPendientes.get(key).getId(), partidasPendientes.get(key));
				this.partidasEnJuego.get(partidasPendientes.get(key).getId()).comenzar();
				this.partidasPendientes.remove(partidasPendientes.get(key).getId());
			}
		}
	}
}








