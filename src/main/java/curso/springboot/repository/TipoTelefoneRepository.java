package curso.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import curso.springboot.model.TipoTelefone;

@Repository
@Transactional
public interface TipoTelefoneRepository extends JpaRepository<TipoTelefone, Long> {
	
	

}
