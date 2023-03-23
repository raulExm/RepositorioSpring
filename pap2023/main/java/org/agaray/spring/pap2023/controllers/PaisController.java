package org.agaray.spring.pap2023.controllers;

import java.util.List;

import org.agaray.spring.pap2023.entities.Pais;
import org.agaray.spring.pap2023.entities.Persona;
import org.agaray.spring.pap2023.exception.DangerException;
import org.agaray.spring.pap2023.helpers.H;
import org.agaray.spring.pap2023.helpers.PRG;
import org.agaray.spring.pap2023.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pais")
public class PaisController {

	@Autowired
	private PaisService paisService;

	@GetMapping("c")
	public String cGet(
			ModelMap m,
			HttpSession s
			) throws DangerException {
		try {
			H.isRolOk("admin",(Persona)(s.getAttribute("usuario"))); 
		}
		catch (Exception e)
		{
			PRG.error("No tienes permiso para realizar esta acción ("+e.getMessage()+")");
			
		}
		m.put("view", "pais/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(@RequestParam("nombre") String nombre) throws DangerException {
		try {
		paisService.savePais(nombre);
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"/pais/r");
		}
		return "redirect:/pais/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Pais> paises = paisService.getPaises();
		m.put("paises", paises);
		m.put("view", "pais/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idPais, ModelMap m) {
		Pais pais = paisService.getPaisById(idPais);

		m.put("pais", pais);
		m.put("view", "pais/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(@RequestParam("idPais") Long idPais, @RequestParam("nombre") String nombre) throws DangerException {
		String retorno = "redirect:/pais/r"; 
		try {
			paisService.updatePais(idPais, nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/pais/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String dPost(
			@RequestParam("id") Long id
			) throws DangerException {
		String retorno="redirect:/";
		try {
			paisService.deletePais(id);
			retorno = "redirect:/pais/r";
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"/pais/r");
		}
		return retorno;
	}

}
