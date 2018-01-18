<%-- 
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas 
--%>

<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String p=request.getParameter("p");
	JSONObject jso=new JSONObject(p);
	
	JSONObject respuesta=new JSONObject();
	try {
		String nombreJugador=jso.getString("nombre");
		String emailJugador=jso.getString("email");
		Usuario usuario = Manager.get().jugar(nombreJugador, emailJugador);
		
		
		//Usuario usuario=Manager.get().crearPartida(nombreJugador, numeroDeJugadores);
		session.setAttribute("usuario", usuario);	
		respuesta.put("result", "OK");
		respuesta.put("mensaje", usuario.getPartida().getId());
		
		System.out.println(usuario.getPartida().getJugadores().get(0).getNombre());
		
		if(usuario.getPartida().getJugadoresListos()==4){
			respuesta.put("Jugador1", usuario.getPartida().getJugadores().get(0).getNombre());
			respuesta.put("Jugador2", usuario.getPartida().getJugadores().get(1).getNombre());
			respuesta.put("Jugador3", usuario.getPartida().getJugadores().get(2).getNombre());
			respuesta.put("Jugador4", usuario.getPartida().getJugadores().get(3).getNombre());
		}
		
		 //Gson gson = new Gson();

	        // Convert numbers array into JSON string.
	    //    String numbersJson = gson.toJson(numbers);

	        // Convert strings array into JSON string
	   //     String daysJson = gson.toJson(days);
		//respuesta.put("jugadores", usuario.getPartida().getJugadores());
		
		//Cookie cookie=new Cookie("kookie", "" + numeroDeJugadores);
		//cookie.setMaxAge(30);
		//response.addCookie(cookie);
	}
	catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", e.getMessage());
	}
	out.println(respuesta.toString());
%>