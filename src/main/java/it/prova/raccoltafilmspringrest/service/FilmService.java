package it.prova.raccoltafilmspringrest.service;

import java.util.List;

import it.prova.raccoltafilmspringrest.model.Film;

public interface FilmService {
	List<Film> listAllElements(boolean eager);

	Film caricaSingoloElemento(Long id);

	Film caricaSingoloElementoEager(Long id);

	Film aggiorna(Film filmInstance);

	Film inserisciNuovo(Film filmInstance);

	void rimuovi(Long idToRemove);

	List<Film> findByExample(Film example);

	List<Film> findByTitoloAndGenere(String titolo, String genere);

}
