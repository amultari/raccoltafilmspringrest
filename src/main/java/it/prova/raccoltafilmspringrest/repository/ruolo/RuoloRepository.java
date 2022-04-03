package it.prova.raccoltafilmspringrest.repository.ruolo;

import org.springframework.data.repository.CrudRepository;

import it.prova.raccoltafilmspringrest.model.Ruolo;

public interface RuoloRepository extends CrudRepository<Ruolo, Long> {
	Ruolo findByDescrizioneAndCodice(String descrizione, String codice);
}
