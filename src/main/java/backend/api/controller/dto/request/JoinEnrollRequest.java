package backend.api.controller.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class JoinEnrollRequest {

    private List<JoinInfoRequest> joinInfoRequestList = new ArrayList<>();

}
