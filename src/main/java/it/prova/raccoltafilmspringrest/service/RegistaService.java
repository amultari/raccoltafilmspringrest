package it.prova.raccoltafilmspringrest.service;

import java.util.List;

import org.springframework.data.domain.Page;

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

	Page<Regista> findByExampleWithPagination(Regista example, Integer pageNo, Integer pageSize, String sortBy);

	Page<Regista> findByExampleNativeWithPagination(Regista example, Integer pageNo, Integer pageSize, String sortBy);

	List<Regista> cercaByCognomeENomeILike(String term);

	Regista findByNomeAndCognome(String nome, String cognome);

}
