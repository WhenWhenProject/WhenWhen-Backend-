package backend.api.repository.join;

import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoinRepository extends JpaRepository<Join, Long>, JoinRepositoryCustom {



}