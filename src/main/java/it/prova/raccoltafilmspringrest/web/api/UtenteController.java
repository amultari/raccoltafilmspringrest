package it.prova.raccoltafilmspringrest.web.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.raccoltafilmspringrest.model.Utente;
import it.prova.raccoltafilmspringrest.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.raccoltafilmspringrest.service.UtenteService;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

	private final UtenteService utenteService;

	public UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}

	// questa mi serve solo per capire se solo ADMIN vi ha accesso
	@GetMapping("/testSoloAdmin")
	public String test() {
		return "OK";
	}

	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {

		// se sono qui significa che sono autenticato quindi devo estrarre le info dal
		// contesto
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// estraggo le info dal principal
		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), utenteLoggato.getEmail(), ruoli));
	}
}
