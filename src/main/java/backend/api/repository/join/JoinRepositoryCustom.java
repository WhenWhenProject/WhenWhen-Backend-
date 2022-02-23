package backend.api.repository.join;

import backend.api.entity.Join;

import java.util.Optional;

public interface JoinRepositoryCustom {

    Optional<Join> findByUsernameAndPlanId(String username, Long planId);

}
