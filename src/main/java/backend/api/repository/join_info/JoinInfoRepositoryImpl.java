package backend.api.repository.join_info;

import backend.api.entity.JoinInfo;
import backend.api.repository.util.MyQuerydslRepositorySupport;

public class JoinInfoRepositoryImpl extends MyQuerydslRepositorySupport implements JoinInfoRepositoryCustom {

    public JoinInfoRepositoryImpl() {
        super(JoinInfo.class);
    }

}
