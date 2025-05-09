package it.prova.raccoltafilmspringrest.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.repository.regista.RegistaRepository;
import it.prova.raccoltafilmspringrest.web.api.exception.RegistaNotFoundException;

@Service
@Transactional(readOnly = true)
public class RegistaServiceImpl implements RegistaService {

	@Autowired
	private RegistaRepository repository;

	public List<Regista> listAllElements() {
		return (List<Regista>) repository.findAll();
	}

	public Regista caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Regista caricaSingoloElementoConFilms(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public Regista aggiorna(Regista registaInstance) {
		return repository.save(registaInstance);
	}

	@Transactional
	public Regista inserisciNuovo(Regista registaInstance) {
		return repository.save(registaInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove)
				.orElseThrow(() -> new RegistaNotFoundException("Regista not found con id: " + idToRemove));
		repository.deleteById(idToRemove);
	}

	public List<Regista> findByExample(Regista example) {
		return repository.findByExample(example);
	}

	@Override
	public Page<Regista> findByExampleWithPagination(Regista example, Integer pageNo, Integer pageSize, String sortBy) {
		Specification<Regista> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + example.getNome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + example.getCognome().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getNickName()))
				predicates
						.add(cb.like(cb.upper(root.get("nickName")), "%" + example.getNickName().toUpperCase() + "%"));

			if (example.getSesso() != null)
				predicates.add(cb.equal(root.get("sesso"), example.getSesso()));

			if (example.getDataDiNascita() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataDiNascita"), example.getDataDiNascita()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = null;
		// se non passo parametri di paginazione non ne tengo conto
		if (pageSize == null || pageSize < 10)
			paging = Pageable.unpaged();
		else
			paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return repository.findAll(specificationCriteria, paging);
	}

	@Override
	public Page<Regista> findByExampleNativeWithPagination(Regista example, Integer pageNo, Integer pageSize,
			String sortBy) {
		return repository.findByExampleNativeWithPagination(example.getNome(), example.getCognome(),
				example.getDataDiNascita(), example.getSesso(), example.getNickName(),
				PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
	}

	public List<Regista> cercaByCognomeENomeILike(String term) {
		return repository.findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(term, term);
	}

	public Regista findByNomeAndCognome(String nome, String cognome) {
		return repository.findByNomeAndCognome(nome, cognome);
	}

	@Override
	public List<Regista> listAllElementsEager() {
		return (List<Regista>) repository.findAllEager();
	}

}
