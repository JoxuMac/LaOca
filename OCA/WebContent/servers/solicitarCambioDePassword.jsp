<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.tysweb.laoca.dominio.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recuperacion de Password</title>
</head>
<body>

	<%
	String email = request.getParameter("email");
	if(email==null){
	%>
		<form action="servers/solicitarCambioDePassword.jsp" method="GET">
			<input type="text" placeholder="Correo electronico" name="email"><br>
			<button type="submit">Enviame un correo</button>
		</form>
		
		
		
		
		<form action="servers/subirFoto.jsp" method="POST" enctype="multipart/form-data">
			<input type="file" placeholder="Selecciona tu foto" name="foto" accept="image/x-png, image/gif, image/jpeg">
			<button type="submit">Subir la Foto</button>
		</form>
		
		
	<%
	} else {
		try{
			enviarToken(email);
			out.print("Te hemos enviado un correo");
		}
		catch (Exception e){
			out.print("Ha fallado algo, chaval" + e.toString());
		}
	}
	%>
</body>
</html>

<%!
private void enviarToken(String email) throws Exception{
// ESTO SE DEBE CREAR EN EL MANAGER, COGEMOS UNA INSTANCIA DEL MANAGER Y LO PONEMOS AHI
long n= new java.util.Random().nextLong();
if(n<0)
	n=-n;
	TokenRecuperacionPwd token = new TokenRecuperacionPwd(email, n);
// guardarlo en la BD asociado al email y ponerle 10 minutos de caducidad

	EmailSenderService ess = new EmailSenderService();
	ess.enviarPorGmail(email, n);


}
%>