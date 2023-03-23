package org.agaray.spring.pap2023;

import org.agaray.spring.pap2023.services.PersonaService;
import org.agaray.spring.pap2023.services.RolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class} )
public class Pap2023Application {

	public static void main(String[] args) {
		SpringApplication.run(Pap2023Application.class, args);
	}

	@Bean
	CommandLineRunner run (PersonaService personaService,RolService rolService) {
		return (args -> crearRolesYUsuarios(rolService,personaService));
	}

	
	private static void crearRolesYUsuarios(RolService rs, PersonaService ps) {
		try {

			if (! rs.existeRol("admin") ) {
				rs.saveRol("admin");
			}

			if (! rs.existeRol("user") ) {
				rs.saveRol("user");
			}

		}
		catch (Exception e) {
			System.out.println("Error al crear roles por defecto");
			System.out.println(e.getMessage());
		}
		try {

			if (! ps.existePersona("admin")) {
				ps.saveAdmin("admin", "admin", "admin", null, null, null, null, null, null);
			}
		}
		catch (Exception e) {
			System.out.println("Error al crear usuario admin");
			System.out.println(e.getMessage());
		}

	}

}
