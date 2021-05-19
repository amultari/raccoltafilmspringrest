package it.prova.raccoltafilmspringrest.repository.regista;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.prova.raccoltafilmspringrest.model.Regista;

public interface RegistaRepository extends CrudRepository<Regista, Long>, CustomRegistaRepository{
	Regista findByNomeAndCognome(String nome, String cognome);
	List<Regista> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome, String nome);
}
