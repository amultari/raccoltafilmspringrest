package it.prova.raccoltafilmspringrest.repository.regista;

import java.util.List;

import it.prova.raccoltafilmspringrest.model.Regista;

public interface CustomRegistaRepository {
	List<Regista> findByExample(Regista example);
}
