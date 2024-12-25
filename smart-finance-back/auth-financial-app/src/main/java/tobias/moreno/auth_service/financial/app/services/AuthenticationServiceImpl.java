package tobias.moreno.auth_service.financial.app.services;

import tobias.moreno.auth_service.financial.app.dto.AuthenticationRequest;
import tobias.moreno.auth_service.financial.app.dto.AuthenticationResponse;
import tobias.moreno.auth_service.financial.app.dto.EmailTemplateName;
import tobias.moreno.auth_service.financial.app.dto.RegistrationRequest;
import tobias.moreno.auth_service.financial.app.entities.RoleEntity;
import tobias.moreno.auth_service.financial.app.entities.TokenEntity;
import tobias.moreno.auth_service.financial.app.entities.UserEntity;
import tobias.moreno.auth_service.financial.app.repositories.IRoleRepository;
import tobias.moreno.auth_service.financial.app.repositories.ITokenRepository;
import tobias.moreno.auth_service.financial.app.repositories.IUserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

	private final IRoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final IUserRepository userRepository;
	private final ITokenRepository tokenRepository;
	private final EmailServiceImpl emailService;
	private final AuthenticationManager authenticationManager;
	private final JwtServiceImpl jwtServiceImpl;

	@Value("${application.mailing.frontend.activation-url}")
	private String activationUrl;

	public void register(RegistrationRequest registrationRequest) throws MessagingException {
		RoleEntity userRole = roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("The role USER was not found"));

		UserEntity userEntity = UserEntity.builder()
				.firstName(registrationRequest.getFirstName())
				.lastName(registrationRequest.getLastName())
				.email(registrationRequest.getEmail())
				.password(passwordEncoder.encode(registrationRequest.getPassword()))
				.accountLocked(false)
				.enabled(false)
				.roles(List.of(userRole))
				.build();
		userRepository.save(userEntity);
		sendValidationEmail(userEntity);
	}

	private void sendValidationEmail(UserEntity userEntity) throws MessagingException {
		var newToken = generateAndSaveActivationToken(userEntity);
		emailService.sendEmail(userEntity.getEmail(), userEntity.fullName(), EmailTemplateName.ACTIVATE_ACCOUNT, activationUrl, newToken, "Account Activation");

	}

	private String generateAndSaveActivationToken(UserEntity userEntity) {
		String generatedToken = generateActivacionCode(6);
		TokenEntity token = TokenEntity.builder()
				.token(generatedToken)
				.createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(30))
				.userEntity(userEntity)
				.build();
		tokenRepository.save(token);
		return generatedToken;
	}

	private String generateActivacionCode(int length) {
		String characters = "0123456789";
		StringBuilder activationCode = new StringBuilder();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(characters.length());
			activationCode.append(characters.charAt(randomIndex));
		}
		return activationCode.toString();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		var auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getEmail(),
						authenticationRequest.getPassword()
				)
		);
		var claims = new HashMap<String, Object>();
		UserEntity user = (UserEntity) auth.getPrincipal();
		claims.put("fullName", user.fullName());
		var jwtToken = jwtServiceImpl.generateToken(claims, user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public void activateAccount(String token) throws MessagingException {
		TokenEntity tokenEntity = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("The token was not found"));

		if (LocalDateTime.now().isAfter(tokenEntity.getExpiresAt())) {
			sendValidationEmail(tokenEntity.getUserEntity());
			throw new RuntimeException("The token has expired. A new token has been created.");
		}
		UserEntity userEntity = userRepository.findById(tokenEntity.getUserEntity().getId())
				.orElseThrow(() -> new RuntimeException("The user was not found"));
		userEntity.setEnabled(true);
		userRepository.save(userEntity);
		tokenEntity.setValidatedAt(LocalDateTime.now());
		tokenRepository.save(tokenEntity);
	}
}
