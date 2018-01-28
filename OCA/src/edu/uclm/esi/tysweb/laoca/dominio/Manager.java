package edu.uclm.esi.tysweb.laoca.dominio;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BsonDocument;
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
	
	public Usuario getJugadorTurno(int partida) {
		return partidasEnJuego.get(partida).getJugadorConElTurno();
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
		JSONObject jso = null;
		
		if(msg.getString("tipo").equals("DADO"))
			jso = partida.tirarDado(nombreJugador, Integer.parseInt(msg.getString("mensaje")));
		
		//partida.broadcast(msg);
		partida.broadcast(jso);
		
		try {
			System.out.println(jso.get("destinoInicial"));
			System.out.println(jso.get("destinoFinal"));
			System.out.println(jso.get("e"));
		}catch(Exception e) {System.out.println("Error: "+e);}
		
		
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
	public JSONObject getRanking() throws Exception {
		return DAOUsuario.getRanking();
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	public void registrar(String email, String pwd, String user) throws Exception {
		Usuario usuario=new UsuarioRegistrado();
		usuario.seteMail(email);
		usuario.setNombre(user);
		usuario.setScore(0);
		usuario.setPhoto("profile/default.png");
		usuario.insert(pwd);
	}
	public void registrarGoogle(String email,String token,String user) throws Exception {
		Usuario usuario=new UsuarioRegistrado();
		usuario.seteMail(email);
		usuario.setNombre(user);
		usuario.setToken(token);
		usuario.setScore(0);
		usuario.setPhoto("profile/default.png");
		//usuario.insert(pwd);
		usuario.insertToken(token);
	}
	
	public Usuario login(String email, String pwd) throws Exception {
		//return DAOUsuario.login(email, pwd);
		Usuario usuario = new UsuarioRegistrado().login(email, pwd);
		return usuario;
	}
	public Usuario loginGoogle(String email, String token) throws Exception {
		//return DAOUsuario.login(email, pwd);
		Usuario usuario = new UsuarioRegistrado().loginGoogle(email, token);
		return usuario;
	}
	public Usuario registrar_anonimo(String nombre,String email) throws Exception{
        Usuario usuarioAnonimo = new Usuario();
        usuarioAnonimo.setNombre(nombre);
        usuarioAnonimo.seteMail(email);
        this.usuarios.put(nombre, usuarioAnonimo);
        return usuarioAnonimo;
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
	
	public void recuperarPassword(String email, String code) throws Exception {
		TokenRecuperacionPwd token = getTokenBBDD(email);
		
		BsonDocument btoken = token.toBsonDocument();
		
		if(!btoken.getString("email").getValue().equals(email))
			throw new Exception("3");
			
		if(!(btoken.getInt64("valor").getValue()+"").equals(code))
			throw new Exception("3");

		if(btoken.getInt64("caducidad").getValue()<System.currentTimeMillis())
			throw new Exception("2");
		
		int random = (new Random((new java.util.Date()).getTime())).nextInt();
		if(random<0)
			random =-random;
		String newPassword = String.valueOf(random);
		
		newPassword(email, newPassword);
		
		EmailSenderService ess = new EmailSenderService();
		ess.enviarPassword(email, newPassword);
	}
	
	private TokenRecuperacionPwd getTokenBBDD(String email) throws Exception {
		return DAOUsuario.getTokenPwd(email);
	}

	private void newPassword(String email, String newPassword) throws Exception {
		DAOUsuario.newPassword(email, newPassword);
		
	}

	public void enviarToken(String email, String url) throws Exception {
		long n= new java.util.Random().nextLong();
		if(n<0)
			n=-n;
		
		TokenRecuperacionPwd token = new TokenRecuperacionPwd(email, n);
		Manager.get().saveToken(email, token);
	
		EmailSenderService ess = new EmailSenderService();
		ess.enviarPeticionPassword(email, n, url);
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
		return usuario;
	}

	public void lanzarPartidas() {
		for (Integer key : partidasPendientes.keySet()) {
			if (partidasPendientes.get(key).isReady()) {
				this.partidasEnJuego.put(partidasPendientes.get(key).getId(), partidasPendientes.get(key));
				this.partidasEnJuego.get(partidasPendientes.get(key).getId()).comenzar();
				this.partidasPendientes.remove(partidasPendientes.get(key).getId());
			}
		}
	}
}








