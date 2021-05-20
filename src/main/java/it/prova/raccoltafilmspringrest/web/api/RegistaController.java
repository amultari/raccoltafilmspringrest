package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.service.RegistaService;
import it.prova.raccoltafilmspringrest.web.api.exception.RegistaNotFoundException;

@RestController
@RequestMapping("api/regista")
public class RegistaController {

	@Autowired
	private RegistaService registaService;

	@GetMapping
	public List<Regista> getAll() {
		return registaService.listAllElements();
	}

	@GetMapping("/{id}")
	public Regista findById(@PathVariable(value = "id", required = true) long id) {
		Regista regista = registaService.caricaSingoloElemento(id);

		if (regista == null)
			throw new RegistaNotFoundException("Regista not found con id: " + id);
		
		return regista;
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Regista createNew(@Valid @RequestBody Regista registaInput) {
		return registaService.inserisciNuovo(registaInput);
	}
	
	@PutMapping("/{id}")
	public Regista update(@Valid @RequestBody Regista registaInput, @PathVariable(required = true) Long id) {
		Regista regista = registaService.caricaSingoloElemento(id);

		if (regista == null)
			throw new RegistaNotFoundException("Regista not found con id: " + id);
		
		registaInput.setId(id);
		return registaService.aggiorna(registaInput);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Regista regista = registaService.caricaSingoloElemento(id);

		if (regista == null)
			throw new RegistaNotFoundException("Regista not found con id: " + id);
		
		registaService.rimuovi(regista); 
	}
	
	@PostMapping("/search")
	public List<Regista> search(@RequestBody Regista example) {
		return registaService.findByExample(example);
	}

}
