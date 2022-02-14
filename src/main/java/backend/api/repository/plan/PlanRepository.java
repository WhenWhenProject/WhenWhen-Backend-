package backend.api.repository.plan;


import backend.api.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {

    Plan findByLinkUrl(String linkUrl);

}
