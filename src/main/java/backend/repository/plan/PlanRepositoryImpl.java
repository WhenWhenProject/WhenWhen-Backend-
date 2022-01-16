package backend.repository.plan;

import backend.domain.JoinInfo;
import backend.domain.Plan;
import backend.repository.MyQuerydslRepositorySupport;

public class PlanRepositoryImpl extends MyQuerydslRepositorySupport implements PlanRepositoryCustom {

    public PlanRepositoryImpl() {
        super(Plan.class);
    }

}
