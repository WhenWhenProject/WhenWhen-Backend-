package backend.api.repository.plan;

import backend.api.entity.Plan;
import backend.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {

    Optional<Plan> findByLinkUrl(String linkUrl);
    Optional<Plan> findByTitle(String title);

}
