package backend.api.repository.plan;

import backend.api.entity.Plan;
import backend.api.entity.User;
import backend.api.repository.util.MyQuerydslRepositorySupport;

public class PlanRepositoryImpl extends MyQuerydslRepositorySupport implements PlanRepositoryCustom {

    public PlanRepositoryImpl() {
        super(Plan.class);
    }


}
