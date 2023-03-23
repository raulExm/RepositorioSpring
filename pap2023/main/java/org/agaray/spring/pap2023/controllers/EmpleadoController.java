package org.agaray.spring.pap2023.controllers;

import java.util.List;

import org.agaray.spring.pap2023.dto.EmpleadoDTO;
import org.agaray.spring.pap2023.entities.Empleado;
import org.agaray.spring.pap2023.exception.DangerException;
import org.agaray.spring.pap2023.helpers.PRG;
import org.agaray.spring.pap2023.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("view", "empleado/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestBody Empleado empleado
			) throws DangerException {
		/*
		try {
			empleadoService.saveEmpleado(empleado.getDni(),empleado.getNombre(),empleado.getApellido());
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/empleado/r");
		}
		return "redirect:/empleado/r";
		*/
		return "redirect:/";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<EmpleadoDTO> empleados = empleadoService.getEmpleados();
		m.put("empleados", empleados);
		m.put("view", "empleado/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idEmpleado, ModelMap m) {
		Empleado empleado = empleadoService.getEmpleadoById(idEmpleado);

		m.put("empleado", empleado);
		m.put("view", "empleado/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(@RequestParam("idEmpleado") Long idEmpleado,
			@RequestParam("nombre") String nombre) throws DangerException {
		String retorno = "redirect:/empleado/r";
		try {
			empleadoService.updateEmpleado(idEmpleado, nombre);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/empleado/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		empleadoService.deleteEmpleado(id);
		return "redirect:/empleado/r";
	}

}

