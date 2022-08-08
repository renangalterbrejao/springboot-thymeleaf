package curso.springboot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Page<Pessoa> findPessoaByNomeContainingIgnoreCaseOrSobrenomeContainingIgnoreCase(String nome, String sobrenome, PageRequest page);
	
	Page<Pessoa> findPessoaBySexo(String sexo, PageRequest page);
	
	@Query("SELECT p FROM Pessoa p WHERE (UPPER(p.nome) LIKE %?1% OR UPPER(p.sobrenome) LIKE %?2%) AND "
			+ " p.sexo LIKE %?3%")
	Page<Pessoa> findPessoaPorNomeSobrenomeSexo(String nome, String sobrenome, String sexo, PageRequest page);

}
