/*
LA OCA - 2017 - Tecnologias y Sistemas Web
Escuela Superior de Informatica de Ciudad Real 

Josue Gutierrez Duran
Sonia Querencia Martin
Enrique Simarro Santamaria
Eduardo Fuentes Garcia De Blas
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
}
