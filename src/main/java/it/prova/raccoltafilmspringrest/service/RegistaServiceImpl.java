package it.prova.raccoltafilmspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.repository.regista.RegistaRepository;

@Service
public class RegistaServiceImpl implements RegistaService {

	@Autowired
	private RegistaRepository repository;


	public List<Regista> listAllElements() {
		return (List<Regista>)repository.findAll();
	}

	public Regista caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Regista caricaSingoloElementoConFilms(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public Regista aggiorna(Regista registaInstance) {
		return repository.save(registaInstance);
	}

	@Transactional
	public Regista inserisciNuovo(Regista registaInstance) {
		return repository.save(registaInstance);
	}

	@Transactional
	public void rimuovi(Regista registaInstance) {
		repository.delete(registaInstance);
	}

	public List<Regista> findByExample(Regista example) {
		return repository.findByExample(example);
	}

	public List<Regista> cercaByCognomeENomeILike(String term) {
		return repository.findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(term, term);
	}

	public Regista findByNomeAndCognome(String nome, String cognome) {
		return repository.findByNomeAndCognome(nome, cognome);
	}

	@Override
	public List<Regista> listAllElementsEager() {
		return (List<Regista>)repository.findAllEager();
	}

}
