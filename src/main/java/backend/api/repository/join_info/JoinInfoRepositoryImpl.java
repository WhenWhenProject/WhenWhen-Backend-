package backend.api.repository.join_info;

import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import backend.api.repository.util.MyQuerydslRepositorySupport;

import static backend.api.entity.QJoinInfo.joinInfo;

public class JoinInfoRepositoryImpl extends MyQuerydslRepositorySupport implements JoinInfoRepositoryCustom {

    public JoinInfoRepositoryImpl() {
        super(JoinInfo.class);
    }

    @Override
    public void deleteByJoin(Join join) {
        getQueryFactory()
                .delete(joinInfo)
                .where(joinInfo.join.eq(join))
                .execute();
    }

}
