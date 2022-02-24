package backend.api.repository.join_info;

import backend.api.entity.Join;

public interface JoinInfoRepositoryCustom {

    void deleteByJoin(Join join);

}
