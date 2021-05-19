package it.prova.raccoltafilmspringrest;

import java.text.SimpleDateFormat;

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
			registaSpielberg = new Regista(steven, spielberg, "stevsp",
					new SimpleDateFormat("dd/MM/yyyy").parse("18/12/1946"), Sesso.MASCHIO);
			registaService.inserisciNuovo(registaSpielberg);
		}

		Film loSqualo = new Film("Lo Squalo", "thriller", new SimpleDateFormat("dd/MM/yyyy").parse("19/12/1975"), 130,
				registaSpielberg);
		if (filmService.findByTitoloAndGenere(loSqualo.getTitolo(), loSqualo.getGenere()) == null)
			filmService.inserisciNuovo(loSqualo);

		String kathryn = "Kathryn ";
		String bigelow = "Bigelow";
		Regista kathrynBigelow = registaService.findByNomeAndCognome(kathryn, bigelow);

		if (kathrynBigelow == null) {
			kathrynBigelow = new Regista(kathryn, bigelow, "katbig",
					new SimpleDateFormat("dd/MM/yyyy").parse("27/11/1951"), Sesso.FEMMINA);
			registaService.inserisciNuovo(kathrynBigelow);
		}

		Film pointBreak = new Film("Point Break", "thriller", new SimpleDateFormat("dd/MM/yyyy").parse("19/02/1991"),
				122, kathrynBigelow);
		if (filmService.findByTitoloAndGenere(pointBreak.getTitolo(), pointBreak.getGenere()) == null)
			filmService.inserisciNuovo(pointBreak);

	}

}
