package backend.repository.joininfo;

import backend.domain.Join;
import backend.domain.JoinInfo;
import backend.repository.MyQuerydslRepositorySupport;

public class JoinInfoRepositoryImpl extends MyQuerydslRepositorySupport implements JoinInfoRepositoryCustom {

    public JoinInfoRepositoryImpl() {
        super(JoinInfo.class);
    }

}
