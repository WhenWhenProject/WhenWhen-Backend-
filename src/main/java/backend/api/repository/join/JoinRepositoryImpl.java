package backend.api.repository.join;

import backend.api.entity.Join;
import backend.api.entity.JoinInfo;
import backend.api.repository.util.MyQuerydslRepositorySupport;

public class JoinRepositoryImpl extends MyQuerydslRepositorySupport implements JoinRepositoryCustom {

    public JoinRepositoryImpl() {
        super(Join.class);
    }

}
