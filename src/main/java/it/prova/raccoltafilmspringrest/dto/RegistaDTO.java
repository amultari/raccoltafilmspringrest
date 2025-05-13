package it.prova.raccoltafilmspringrest.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.model.Sesso;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistaDTO {

	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotBlank(message = "{nickName.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String nickName;

	@NotNull(message = "{dataDiNascita.notnull}")
	private LocalDate dataDiNascita;

	@NotNull(message = "{sesso.notblank}")
	private Sesso sesso;

	@JsonIgnoreProperties(value = { "regista" })
	private Set<FilmDTO> films = new HashSet<FilmDTO>(0);

	public RegistaDTO() {
	}

	public RegistaDTO(Long id) {
		this.id = id;
	}

	public RegistaDTO(Long id, String nome, String cognome, String nickName, LocalDate dataDiNascita, Sesso sesso) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.nickName = nickName;
		this.dataDiNascita = dataDiNascita;
		this.sesso = sesso;
	}

	public RegistaDTO(String nome, String cognome, String nickName) {
		this.nome = nome;
		this.cognome = cognome;
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public Sesso getSesso() {
		return sesso;
	}

	public void setSesso(Sesso sesso) {
		this.sesso = sesso;
	}

	public Set<FilmDTO> getFilms() {
		return films;
	}

	public void setFilms(Set<FilmDTO> films) {
		this.films = films;
	}

	public Regista buildRegistaModel() {
		return new Regista(this.id, this.nome, this.cognome, this.nickName, this.dataDiNascita, this.sesso);
	}

	public static RegistaDTO buildRegistaDTOFromModel(Regista registaModel, boolean includeFilms) {
		RegistaDTO result = new RegistaDTO(registaModel.getId(), registaModel.getNome(), registaModel.getCognome(),
				registaModel.getNickName(), registaModel.getDataDiNascita(), registaModel.getSesso());
		if (includeFilms)
			result.setFilms(FilmDTO.createFilmDTOSetFromModelSet(registaModel.getFilms(), false));
		return result;
	}

	public static List<RegistaDTO> createRegistaDTOListFromModelList(List<Regista> modelListInput,
			boolean includeFilms) {
		return modelListInput.stream().map(registaEntity -> {
			RegistaDTO result = RegistaDTO.buildRegistaDTOFromModel(registaEntity, includeFilms);
			if (includeFilms)
				result.setFilms(FilmDTO.createFilmDTOSetFromModelSet(registaEntity.getFilms(), false));
			return result;
		}).collect(Collectors.toList());
	}

	public static Page<RegistaDTO> fromModelPageToDTOPage(Page<Regista> input) {
		return new PageImpl<>(createRegistaDTOListFromModelList(input.getContent(), false),
				PageRequest.of(input.getPageable().getPageNumber(), input.getPageable().getPageSize(),
						input.getPageable().getSort()),
				input.getTotalElements());
	}

}
