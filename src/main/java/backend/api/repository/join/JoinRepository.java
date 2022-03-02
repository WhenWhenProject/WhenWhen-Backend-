package backend.api.repository.join;

import backend.api.entity.Join;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository<Join, Long>, JoinRepositoryCustom {



}