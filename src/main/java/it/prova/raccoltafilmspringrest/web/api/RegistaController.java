package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.dto.RegistaDTO;
import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.service.RegistaService;
import it.prova.raccoltafilmspringrest.web.api.exception.IdNotNullForInsertException;
import it.prova.raccoltafilmspringrest.web.api.exception.RegistaNotFoundException;

@RestController
@RequestMapping("api/regista")
public class RegistaController {

	@Autowired
	private RegistaService registaService;

	@GetMapping
	public List<RegistaDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return RegistaDTO.createRegistaDTOListFromModelList(registaService.listAllElementsEager(), true);
	}

	@GetMapping("/{id}")
	public RegistaDTO findById(@PathVariable(value = "id", required = true) long id) {
		Regista regista = registaService.caricaSingoloElementoConFilms(id);

		if (regista == null)
			throw new RegistaNotFoundException("Regista not found con id: " + id);

		return RegistaDTO.buildRegistaDTOFromModel(regista, true);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RegistaDTO createNew(@Valid @RequestBody RegistaDTO registaInput) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (registaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Regista registaInserito = registaService.inserisciNuovo(registaInput.buildRegistaModel());
		return RegistaDTO.buildRegistaDTOFromModel(registaInserito, false);
	}

	@PutMapping("/{id}")
	public RegistaDTO update(@Valid @RequestBody RegistaDTO registaInput, @PathVariable(required = true) Long id) {
		Regista regista = registaService.caricaSingoloElemento(id);

		if (regista == null)
			throw new RegistaNotFoundException("Regista not found con id: " + id);

		registaInput.setId(id);
		Regista registaAggiornato = registaService.aggiorna(registaInput.buildRegistaModel());
		return RegistaDTO.buildRegistaDTOFromModel(registaAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		registaService.rimuovi(id);
	}

	@PostMapping("/search")
	public List<RegistaDTO> search(@RequestBody RegistaDTO example) {
		return RegistaDTO.createRegistaDTOListFromModelList(registaService.findByExample(example.buildRegistaModel()),
				false);
	}

	@PostMapping("/searchWithPagination")
	public ResponseEntity<Page<RegistaDTO>> searchPaginated(@RequestBody RegistaDTO example,
			@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {

		Page<Regista> entityPageResults = registaService.findByExampleWithPagination(example.buildRegistaModel(),
				pageNo, pageSize, sortBy);

		return new ResponseEntity<Page<RegistaDTO>>(RegistaDTO.fromModelPageToDTOPage(entityPageResults),
				HttpStatus.OK);
	}

	@PostMapping("/searchNativeWithPagination")
	public ResponseEntity<Page<RegistaDTO>> searchNativePaginated(@RequestBody RegistaDTO example,
			@RequestParam(required = true) Integer pageNo, @RequestParam(required = true) Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {

		Page<Regista> entityPageResults = registaService.findByExampleNativeWithPagination(example.buildRegistaModel(),
				pageNo, pageSize, sortBy);

		return new ResponseEntity<Page<RegistaDTO>>(RegistaDTO.fromModelPageToDTOPage(entityPageResults),
				HttpStatus.OK);
	}

}
