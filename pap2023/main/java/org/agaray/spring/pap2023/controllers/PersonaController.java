package org.agaray.spring.pap2023.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.agaray.spring.pap2023.entities.Aficion;
import org.agaray.spring.pap2023.entities.Pais;
import org.agaray.spring.pap2023.entities.Persona;
import org.agaray.spring.pap2023.exception.DangerException;
import org.agaray.spring.pap2023.helpers.PRG;
import org.agaray.spring.pap2023.services.AficionService;
import org.agaray.spring.pap2023.services.PaisService;
import org.agaray.spring.pap2023.services.PersonaService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/persona")
public class PersonaController {
	
	@Value("${app.uploadFolder}")
	private String UPLOAD_FOLDER;  

	@Value("${app.uploadURIPath}")
	private String UPLOAD_URI;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private PaisService paisService;

	@Autowired
	private AficionService aficionService;

	@GetMapping("c")
	public String cGet(ModelMap m) {
		m.put("paises", paisService.getPaises());
		m.put("aficiones", aficionService.getAficiones());
		m.put("view", "persona/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("nombre") String nombre,
			@RequestParam("loginname") String loginname,
			@RequestParam("password") String password,
			@RequestParam("foto") MultipartFile foto, //Foto de la persona
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) //Necesario para la fecha de nacimiento
			@RequestParam("fnac") LocalDate fnac,
			@RequestParam("idPaisNace") Long idPaisNace,
			@RequestParam("idPaisResidencia") Long idPaisResidencia,
			@RequestParam(required = false, name="idGustos[]") Long[] idGustos,
			@RequestParam(required = false, name="idOdios[]") Long[] idOdios
			
			) throws DangerException {
		try {
			idGustos = (idGustos==null ? new Long[0] : idGustos);
			idOdios = (idOdios ==null ? new Long[0] : idOdios);
			byte[] bytes = foto.getBytes();
			String extension = FilenameUtils.getExtension(foto.getOriginalFilename());
			Persona persona = personaService.savePersona(nombre,loginname,password,fnac,extension,idPaisNace,idPaisResidencia,idGustos, idOdios);
			
			//guardar imagen
			try {
				Path path = Paths.get(UPLOAD_FOLDER + "user-"+ persona.getId()+"."+extension);
				Files.write(path, bytes);
			}
			catch (Exception e) {
				PRG.error("No se pudo guardar la foto de perfil de "+persona.getNombre());
			}


		} catch (Exception e) {
			PRG.error(e.getMessage(), "/persona/r");
		}
		return "redirect:/persona/r";
	}

	@GetMapping("r")
	public String rGet(ModelMap m) {
		List<Persona> personas = personaService.getPersonas();
		m.put("personas", personas);
		m.put("uriIMG", UPLOAD_URI );
		m.put("view", "persona/r");
		return "_t/frame";
	}

	@GetMapping("u")
	public String uGet(@RequestParam("id") Long idPersona, ModelMap m) {
		Persona persona = personaService.getPersonaById(idPersona);
		List<Pais> paises = paisService.getPaises();
		List<Aficion> aficiones = aficionService.getAficiones();

		m.put("persona", persona);
		m.put("paises", paises);
		m.put("aficiones", aficiones);
		m.put("view", "persona/u");

		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idPersona") Long idPersona,
			@RequestParam("idPaisNace") Long idPaisNace,
			@RequestParam("idPaisVive") Long idPaisVive,
			@RequestParam(required = false, name="idGustos[]") Long[] idGustos,
			@RequestParam(required = false, name="idOdios[]") Long[] idOdios,
			@RequestParam("nombre") String nombre
			) throws DangerException {
		idGustos = (idGustos == null ? new Long[0] : idGustos);
		idOdios = (idOdios == null ? new Long[0] : idOdios);
		String retorno = "redirect:/persona/r";
		try {
			personaService.updatePersona(idPersona, nombre, idPaisNace,idPaisVive,idGustos,idOdios);
		} catch (Exception e) {
			PRG.error(e.getMessage(), "/persona/r");
		}
		return retorno;
	}

	@PostMapping("d")
	public String d(@RequestParam("id") Long id) {
		personaService.deletePersona(id);
		return "redirect:/persona/r";
	}

}

