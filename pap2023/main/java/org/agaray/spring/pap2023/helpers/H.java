package org.agaray.spring.pap2023.helpers;

import org.agaray.spring.pap2023.entities.Persona;
import org.agaray.spring.pap2023.entities.Rol;

public class H {
	
	//Este método estático se utiliza para comprobar si el rol de un usuario coincide con el rol especificado
	//y lanza una excepción si no es así.
	public static void isRolOk(String nombreRol, Persona usuario) throws Exception {
		if (!usuario.getRol().equals(new Rol(nombreRol))) {
			throw new Exception("Rol inadecuado, rol actual: "+usuario.getRol()+", rol requerido: "+nombreRol);
		}
	}
}
