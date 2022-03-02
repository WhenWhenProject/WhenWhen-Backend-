package backend.api.repository.plan;

import backend.api.entity.Plan;
import backend.api.repository.util.MyQuerydslRepositorySupport;
import com.querydsl.jpa.JPAExpressions;

import java.util.List;

import static backend.api.entity.QJoin.join;
import static backend.api.entity.QPlan.plan;
import static backend.api.entity.QUser.user;

public class PlanRepositoryImpl extends MyQuerydslRepositorySupport implements PlanRepositoryCustom {

    public PlanRepositoryImpl() {
        super(Plan.class);
    }

    @Override
    public List<Plan> findAllByHostUsername(String username) {
        return getQueryFactory()
                .select(plan)
                .from(plan)
                .join(plan.host).fetchJoin()
                .where(plan.host.username.eq(username))
                .fetch();
    }

    @Override
    public List<Plan> findAllByPlanIdList(List<Long> planIdList) {
        return getQueryFactory()
                .select(plan)
                .from(plan)
                .join(plan.host).fetchJoin()
                .where(plan.id.in(planIdList))
                .fetch();
    }

    @Override
    public List<Plan> findAllParticipatingPlanByUsername(String username) {
        return getQueryFactory()
                .select(plan)
                .from(plan)
                .join(plan.host).fetchJoin()
                .where(plan.id.in(
                        JPAExpressions
                                .select(join.plan.id)
                                .from(join)
                                .join(join.user, user).on(user.username.eq(username))
                ))
                .fetch();
    }

}
