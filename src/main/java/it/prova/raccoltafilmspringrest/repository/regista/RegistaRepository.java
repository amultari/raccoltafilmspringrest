package it.prova.raccoltafilmspringrest.repository.regista;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import it.prova.raccoltafilmspringrest.model.Regista;
import it.prova.raccoltafilmspringrest.model.Sesso;

public interface RegistaRepository
		extends JpaRepository<Regista,Long>, PagingAndSortingRepository<Regista, Long>, JpaSpecificationExecutor<Regista>, CustomRegistaRepository {
	Regista findByNomeAndCognome(String nome, String cognome);

	List<Regista> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome,
			String nome);

	@Query("select distinct r from Regista r left join fetch r.films ")
	List<Regista> findAllEager();

	@Query("from Regista r left join fetch r.films where r.id=?1")
	Regista findByIdEager(Long idRegista);

	@Query(value = "SELECT r.* " + "FROM regista r " + "WHERE ((:nome IS NULL OR LOWER(r.nome) LIKE %:nome%)  "
			+ "AND (:cognome IS NULL OR LOWER(r.cognome) LIKE %:cognome%)) "
			+ "AND (:datadinascita IS NULL OR r.datadinascita > :datadinascita) "
			+ "AND (:sesso IS NULL OR r.sesso = :sesso) "
			+ "AND (:nickname IS NULL OR LOWER(r.nickname) LIKE %:nickname%)", countQuery = "SELECT r.* "
					+ "FROM regista r " + "WHERE ((:nome IS NULL OR LOWER(r.nome) LIKE %:nome%)  "
					+ "AND (:cognome IS NULL OR LOWER(r.cognome) LIKE %:cognome%)) "
					+ "AND (:datadinascita IS NULL OR r.datadinascita > :datadinascita) "
					+ "AND (:sesso IS NULL OR r.sesso = :sesso) "
					+ "AND (:nickname IS NULL OR LOWER(r.nickname) LIKE %:nickname%)", nativeQuery = true)
	Page<Regista> findByExampleNativeWithPagination(@Param("nome") String nome, @Param("cognome") String cognome,
			@Param("datadinascita") LocalDate datadinascita, @Param("sesso") Sesso sesso,
			@Param("nickname") String nickname, Pageable pageable);

}
