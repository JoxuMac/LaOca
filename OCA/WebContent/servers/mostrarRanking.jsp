<%-- 
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas 
--%>

<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONObject jso=new JSONObject();

	try {		
		jso.put("ranking",Manager.get().getRanking());
		jso.put("result", "OK");
	}
	catch (Exception e) {
		jso.put("result", "ERROR");
		System.out.println(e.getMessage());
	}
	out.println(jso.toString());

%>