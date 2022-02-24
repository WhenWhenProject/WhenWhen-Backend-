package backend.api.repository.join_info;

import backend.api.entity.JoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinInfoRepository extends JpaRepository<JoinInfo, Long>, JoinInfoRepositoryCustom {


}