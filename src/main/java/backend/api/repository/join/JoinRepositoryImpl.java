package backend.api.repository.join;

import backend.api.entity.Join;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

import static backend.api.entity.QJoin.join;
import static backend.api.entity.QUser.user;

public class JoinRepositoryImpl extends MyQuerydslRepositorySupport implements JoinRepositoryCustom {

    public JoinRepositoryImpl() {
        super(Join.class);
    }

    @Override
    public Optional<Join> findByUsernameAndPlanId(String username, Long planId) {
        Join result = getQueryFactory()
                .select(join)
                .from(join)
                .join(join.user, user).on(user.username.eq(username))
                .join(join.joinInfoList).fetchJoin()
                .where(join.plan.id.eq(planId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Join> findByUsername(String username) {
        return getQueryFactory()
                .select(join)
                .from(join)
                .join(join.user)
                .where(join.user.username.eq(username))
                .fetch();
    }

}
