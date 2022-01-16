package backend.repository.join;

import backend.domain.Join;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository<Join, Long>, JoinRepositoryCustom {

}