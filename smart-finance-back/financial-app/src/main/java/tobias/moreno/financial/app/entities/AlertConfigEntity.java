package tobias.moreno.financial.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "alert_configs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertConfigEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "check_interval")
	private Long checkInterval;   // Intervalo de verificación en milisegundos

	@Column(name = "notify_email")
	private boolean notifyEmail;  // Configuración para notificación por correo

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
}
