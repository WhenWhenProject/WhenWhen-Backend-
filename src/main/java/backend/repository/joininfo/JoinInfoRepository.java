package backend.repository.joininfo;

import backend.domain.JoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinInfoRepository extends JpaRepository<JoinInfo, Long>, JoinInfoRepositoryCustom {

}