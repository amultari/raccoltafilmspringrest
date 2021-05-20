package it.prova.raccoltafilmspringrest.web.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegistaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RegistaNotFoundException(String message) {
		super(message);
	}

}
