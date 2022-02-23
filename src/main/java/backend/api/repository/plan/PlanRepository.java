package backend.api.repository.plan;

import backend.api.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findByLinkUrl(String linkUrl);
    Optional<Plan> findByTitle(String title);

}
