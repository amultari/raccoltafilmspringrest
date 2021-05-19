package it.prova.raccoltafilmspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.repository.film.FilmRepository;

@Service
public class FilmServiceImpl implements FilmService {

	@Autowired
	private FilmRepository repository;

	public List<Film> listAllElements(boolean eager) {
		if (eager)
			return (List<Film>) repository.findAllFilmEager();
		
		return (List<Film>) repository.findAll();
	}

	public Film caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Film caricaSingoloElementoEager(Long id) {
		return repository.findSingleFilmEager(id);
	}

	@Transactional
	public Film aggiorna(Film filmInstance) {
		return repository.save(filmInstance);
	}

	@Transactional
	public Film inserisciNuovo(Film filmInstance) {
		return repository.save(filmInstance);
	}

	@Transactional
	public void rimuovi(Film filmInstance) {
		repository.delete(filmInstance);
	}

	public List<Film> findByExample(Film example) {
		// da implementare
		return this.listAllElements(false);
	}

	public Film findByTitoloAndGenere(String titolo, String genere) {
		return repository.findByTitoloAndGenere(titolo, genere);
	}

}
