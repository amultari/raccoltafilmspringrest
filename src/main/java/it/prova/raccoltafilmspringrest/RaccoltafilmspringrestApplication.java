package it.prova.raccoltafilmspringrest;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.raccoltafilmspringrest.model.Film;
import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.model.Ruolo;
import it.prova.raccoltafilmspringrest.model.Sesso;
import it.prova.raccoltafilmspringrest.model.Utente;
import it.prova.raccoltafilmspringrest.service.FilmService;
import it.prova.raccoltafilmspringrest.service.RegistaService;
import it.prova.raccoltafilmspringrest.service.RuoloService;
import it.prova.raccoltafilmspringrest.service.UtenteService;

@SpringBootApplication
public class RaccoltafilmspringrestApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private RegistaService registaService;
	@Autowired
	private FilmService filmService;

	public static void main(String[] args) {
		SpringApplication.run(RaccoltafilmspringrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", Ruolo.ROLE_CLASSIC_USER));
		}

		// a differenza degli altri progetti cerco solo per username perche' se vado
		// anche per password ogni volta ne inserisce uno nuovo, inoltre l'encode della
		// password non lo
		// faccio qui perche gia lo fa il service di utente, durante inserisciNuovo
		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi", LocalDate.now());
			admin.setEmail("a.admin@prova.it");
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));
			utenteServiceInstance.inserisciNuovo(admin);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}

		if (utenteServiceInstance.findByUsername("user") == null) {
			Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", LocalDate.now());
			classicUser.setEmail("u.user@prova.it");
			classicUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser.getId());
		}

		if (utenteServiceInstance.findByUsername("user1") == null) {
			Utente classicUser1 = new Utente("user1", "user1", "Antonioo", "Verdii", LocalDate.now());
			classicUser1.setEmail("u.user1@prova.it");
			classicUser1.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser1);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser1.getId());
		}

		if (utenteServiceInstance.findByUsername("user2") == null) {
			Utente classicUser2 = new Utente("user2", "user2", "Antoniooo", "Verdiii", LocalDate.now());
			classicUser2.setEmail("u.user2@prova.it");
			classicUser2.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", Ruolo.ROLE_CLASSIC_USER));
			utenteServiceInstance.inserisciNuovo(classicUser2);
			// l'inserimento avviene come created ma io voglio attivarlo
			utenteServiceInstance.changeUserAbilitation(classicUser2.getId());
		}

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

	}

}
