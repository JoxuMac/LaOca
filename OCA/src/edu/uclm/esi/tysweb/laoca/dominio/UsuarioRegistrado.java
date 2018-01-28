/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.persistencia.*;

/**
 *
 */
public class UsuarioRegistrado extends Usuario {

    public UsuarioRegistrado() {
        super();
    }

    Usuario login(String email, String pwd)throws Exception {
       return DAOUsuario.login(email,pwd);
    }
    Usuario loginGoogle(String email, String token)throws Exception {
        return DAOUsuario.loginGoogle(email,token);
     } 

	/*public Usuario loginConGoogle(String email, String token) throws Exception {
		// TODO Auto-generated method stub
		return DAOUsuario.loginGoogle(email,token);
	}*/

  
}
