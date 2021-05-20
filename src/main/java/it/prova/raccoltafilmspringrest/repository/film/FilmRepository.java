package it.prova.raccoltafilmspringrest.repository.film;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.raccoltafilmspringrest.model.Film;

public interface FilmRepository extends CrudRepository<Film, Long> {
	@Query("from Film f join fetch f.regista where f.id = ?1")
	Film findSingleFilmEager(Long id);
	
	List<Film> findByTitoloAndGenere(String titolo, String genere);
	
	@Query("select f from Film f join fetch f.regista")
	List<Film> findAllFilmEager();

}
