package org.agaray.spring.pap2023.controllers;

import java.util.List;

import org.agaray.spring.pap2023.entities.Aficion;
import org.agaray.spring.pap2023.exception.DangerException;
import org.agaray.spring.pap2023.helpers.PRG;
import org.agaray.spring.pap2023.services.AficionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aficion")
public class AficionController {

	@Autowired
	private AficionService aficionService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("view", "aficion/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(@RequestParam("nombre") String nombre) throws DangerException {
		try {
		aficionService.saveAficion(nombre);
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"/aficion/r");
		}
		return "redirect:/aficion/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Aficion> aficiones = aficionService.getAficiones();
		m.put("aficiones", aficiones);
		m.put("view", "aficion/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idAficion, ModelMap m) {
		Aficion aficion = aficionService.getAficionById(idAficion);

		m.put("aficion", aficion);
		m.put("view", "aficion/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idAficion") Long idAficion, 
			@RequestParam("nombre") String nombre) throws DangerException {
		String retorno = "redirect:/aficion/r"; 
		try {
			aficionService.updateAficion(idAficion, nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(),"/aficion/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(
			@RequestParam("id") Long id
			) {
		aficionService.deleteAficion(id);
		return "redirect:/aficion/r";
	}

}
