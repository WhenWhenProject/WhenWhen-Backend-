package backend.repository.join;

import backend.domain.Join;
import backend.repository.MyQuerydslRepositorySupport;

public class JoinRepositoryImpl extends MyQuerydslRepositorySupport implements JoinRepositoryCustom {

    public JoinRepositoryImpl() {
        super(Join.class);
    }

}
