package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.service.FilmService;

@RestController
@RequestMapping("api/film")
public class FilmController {

	@Autowired
	private FilmService filmService;

	@GetMapping
	public List<Film> getAll() {
		return filmService.listAllElements(true);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	public Film createNew(@Valid @RequestBody Film filmInput) {
		return filmService.inserisciNuovo(filmInput);
	}

}
