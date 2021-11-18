package it.prova.raccoltafilmspringrest.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.raccoltafilmspringrest.model.Film;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmDTO {

	private Long id;

	@NotBlank(message = "{titolo.notblank}")
	@Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String titolo;

	@NotBlank(message = "{genere.notblank}")
	private String genere;

	@NotNull(message = "{dataPubblicazione.notnull}")
	private Date dataPubblicazione;

	@NotNull(message = "{minutiDurata.notnull}")
	@Min(1)
	private Integer minutiDurata;

	@JsonIgnoreProperties(value = { "films" })
	@NotNull(message = "{regista.notnull}")
	private RegistaDTO regista;

	public FilmDTO() {
	}

	public FilmDTO(Long id, String titolo, String genere, Date dataPubblicazione, Integer minutiDurata,
			RegistaDTO regista) {
		this.id = id;
		this.titolo = titolo;
		this.genere = genere;
		this.dataPubblicazione = dataPubblicazione;
		this.minutiDurata = minutiDurata;
		this.regista = regista;
	}

	public FilmDTO(Long id, String titolo, String genere, Date dataPubblicazione, Integer minutiDurata) {
		this.id = id;
		this.titolo = titolo;
		this.genere = genere;
		this.dataPubblicazione = dataPubblicazione;
		this.minutiDurata = minutiDurata;
	}

	public FilmDTO(String titolo, String genere) {
		this.titolo = titolo;
		this.genere = genere;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Integer getMinutiDurata() {
		return minutiDurata;
	}

	public void setMinutiDurata(Integer minutiDurata) {
		this.minutiDurata = minutiDurata;
	}

	public RegistaDTO getRegista() {
		return regista;
	}

	public void setRegista(RegistaDTO regista) {
		this.regista = regista;
	}

	public Film buildFilmModel() {
		return new Film(this.id, this.titolo, this.genere, this.dataPubblicazione, this.minutiDurata,
				this.regista.buildRegistaModel());
	}

	public static FilmDTO buildFilmDTOFromModel(Film filmModel, boolean includeRegisti) {
		FilmDTO result = new FilmDTO(filmModel.getId(), filmModel.getTitolo(), filmModel.getGenere(),
				filmModel.getDataPubblicazione(), filmModel.getMinutiDurata());

		if (includeRegisti)
			result.setRegista(RegistaDTO.buildRegistaDTOFromModel(filmModel.getRegista(), false));

		return result;
	}

	public static List<FilmDTO> createFilmDTOListFromModelList(List<Film> modelListInput, boolean includeRegisti) {
		return modelListInput.stream().map(filmEntity -> {
			return FilmDTO.buildFilmDTOFromModel(filmEntity, includeRegisti);
		}).collect(Collectors.toList());
	}

	public static Set<FilmDTO> createFilmDTOSetFromModelSet(Set<Film> modelListInput, boolean includeRegisti) {
		return modelListInput.stream().map(filmEntity -> {
			return FilmDTO.buildFilmDTOFromModel(filmEntity, includeRegisti);
		}).collect(Collectors.toSet());
	}
}
