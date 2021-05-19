package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.service.RegistaService;

@RestController
@RequestMapping("api/regista")
public class RegistaController {

	@Autowired
	private RegistaService registaService;

	@GetMapping
	public List<Regista> getAll() {
		return registaService.listAllElements();
	}
	
	//gli errori di validazione vengono mostrati con 400 Bad Request ma 
	//elencandoli grazie al ControllerAdvice
	@PostMapping
	public Regista createNew(@Valid @RequestBody Regista registaInput) {
		return registaService.inserisciNuovo(registaInput);
	}

}
