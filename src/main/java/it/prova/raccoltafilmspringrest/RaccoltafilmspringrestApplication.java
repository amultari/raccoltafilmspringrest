package it.prova.raccoltafilmspringrest;

import java.time.LocalDate;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.model.Sesso;
import it.prova.raccoltafilmspringrest.service.FilmService;
import it.prova.raccoltafilmspringrest.service.RegistaService;

@SpringBootApplication
public class RaccoltafilmspringrestApplication implements CommandLineRunner {

	@Autowired
	private RegistaService registaService;
	@Autowired
	private FilmService filmService;

	public static void main(String[] args) {
		SpringApplication.run(RaccoltafilmspringrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String steven = "Steven";
		String spielberg = "Spielberg";
		Regista registaSpielberg = registaService.findByNomeAndCognome(steven, spielberg);

		if (registaSpielberg == null) {
			registaSpielberg = new Regista(steven, spielberg, "stevsp", LocalDate.of(1946, 12, 18), Sesso.MASCHIO);
			registaService.inserisciNuovo(registaSpielberg);
		}

		Film loSqualo = new Film("Lo Squalo", "thriller", LocalDate.of(1975, 12, 19), 130, registaSpielberg);
		if (filmService.findByTitoloAndGenere(loSqualo.getTitolo(), loSqualo.getGenere()).isEmpty())
			filmService.inserisciNuovo(loSqualo);

		String kathryn = "Kathryn ";
		String bigelow = "Bigelow";
		Regista kathrynBigelow = registaService.findByNomeAndCognome(kathryn, bigelow);

		if (kathrynBigelow == null) {
			kathrynBigelow = new Regista(kathryn, bigelow, "katbig", LocalDate.of(1951, 11, 27), Sesso.FEMMINA);
			registaService.inserisciNuovo(kathrynBigelow);
		}

		Film pointBreak = new Film("Point Break", "thriller", LocalDate.of(1991, 2, 19), 122, kathrynBigelow);
		if (filmService.findByTitoloAndGenere(pointBreak.getTitolo(), pointBreak.getGenere()).isEmpty())
			filmService.inserisciNuovo(pointBreak);

		// ora inserisco un qualche dato di prova giusto per provare con ulteriori dati
		String nomeBulk = "Antonia";
		String cognomeBulk = "Carosto";
		// se ho già inserito queesti dati una volta, non lo faccio una seconda
		if (registaService.findByNomeAndCognome(nomeBulk + 1, cognomeBulk + 1) == null) {
			IntStream.rangeClosed(1, 15).forEach(i -> {
				Regista registaInstance = new Regista(nomeBulk + i, cognomeBulk + i, "antocar" + i,
						LocalDate.of(1960 + i, 12, 10 + i), Sesso.FEMMINA);
				registaService.inserisciNuovo(registaInstance);
			});
		}

	}

}
