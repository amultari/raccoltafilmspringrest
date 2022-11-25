package it.prova.raccoltafilmspringrest.service;

import java.util.List;

import it.prova.raccoltafilmspringrest.model.Regista;

public interface RegistaService {
	
	List<Regista> listAllElements();
	
	List<Regista> listAllElementsEager();

	Regista caricaSingoloElemento(Long id);
	
	Regista caricaSingoloElementoConFilms(Long id);

	Regista aggiorna(Regista registaInstance);

	Regista inserisciNuovo(Regista registaInstance);

	void rimuovi(Long idToRemove);
	
	List<Regista> findByExample(Regista example);
	
	List<Regista> cercaByCognomeENomeILike(String term);
	
	Regista findByNomeAndCognome(String nome, String cognome);

}
