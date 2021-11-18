package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.dto.FilmDTO;
import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.service.FilmService;
import it.prova.raccoltafilmspringrest.web.api.exception.FilmNotFoundException;

@RestController
@RequestMapping("api/film")
public class FilmController {

	@Autowired
	private FilmService filmService;

	@GetMapping
	public List<FilmDTO> getAll() {
		return FilmDTO.createFilmDTOListFromModelList(filmService.listAllElements(true), true) ;
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	public FilmDTO createNew(@Valid @RequestBody FilmDTO filmInput) {
		Film filmInserito = filmService.inserisciNuovo(filmInput.buildFilmModel());
		return FilmDTO.buildFilmDTOFromModel(filmInserito, true);
	}
	
	@GetMapping("/{id}")
	public FilmDTO findById(@PathVariable(value = "id", required = true) long id) {
		Film film = filmService.caricaSingoloElementoEager(id);

		if (film == null)
			throw new FilmNotFoundException("Film not found con id: " + id);
		
		return FilmDTO.buildFilmDTOFromModel(film, true);
	}

}
