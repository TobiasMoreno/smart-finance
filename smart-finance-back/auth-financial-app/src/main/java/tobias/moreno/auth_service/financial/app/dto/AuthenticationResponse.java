package tobias.moreno.auth_service.financial.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
	private String token;

}
