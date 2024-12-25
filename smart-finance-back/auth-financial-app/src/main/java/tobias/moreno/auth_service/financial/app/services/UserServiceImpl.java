package tobias.moreno.auth_service.financial.app.services;

import tobias.moreno.auth_service.financial.app.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
	private IUserRepository userRepository;

}
