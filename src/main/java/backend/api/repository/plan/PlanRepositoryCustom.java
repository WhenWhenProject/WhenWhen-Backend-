package backend.api.repository.plan;

import backend.api.entity.Plan;

import java.util.List;

public interface PlanRepositoryCustom {

    List<Plan> findAllByHostUsername(String username);

    List<Plan> findAllByPlanIdListWithHostFetchJoin(List<Long> planIdList);

    List<Plan> findAllParticipatedPlanByUsername(String username);

}
