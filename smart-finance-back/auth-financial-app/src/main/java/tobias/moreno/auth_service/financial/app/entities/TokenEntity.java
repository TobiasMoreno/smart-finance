package tobias.moreno.auth_service.financial.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "expires_at")
	private LocalDateTime expiresAt;
	@Column(name = "validated_at")
	private LocalDateTime validatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id",nullable = false)
	private UserEntity userEntity;
}
