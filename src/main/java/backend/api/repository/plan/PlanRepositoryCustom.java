package backend.api.repository.plan;

import backend.api.entity.Plan;
import backend.api.entity.User;

import java.util.List;

public interface PlanRepositoryCustom {

    List<Plan> findAllByHostUsername(String username);

    List<Plan> findAllByPlanIdList(List<Long> planIdList);

    List<Plan> findAllParticipatingPlanByUsername(String username);

    List<Plan> findAllFixedByUsername(String username);

    List<Plan> findAllUnfixedByUsername(String username);

}
