package tobias.moreno.auth_service.financial.app.controllers;

import tobias.moreno.auth_service.financial.app.dto.AuthenticationRequest;
import tobias.moreno.auth_service.financial.app.dto.AuthenticationResponse;
import tobias.moreno.auth_service.financial.app.dto.RegistrationRequest;
import tobias.moreno.auth_service.financial.app.services.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

	private final AuthenticationServiceImpl authenticationService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest) throws MessagingException {
		authenticationService.register(registrationRequest);
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
		return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
	}

	@GetMapping("/activate-account")
	public void confirm(@RequestParam String token) throws MessagingException {
		authenticationService.activateAccount(token);
	}

}
