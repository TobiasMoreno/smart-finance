package tobias.moreno.financial.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Alert> alerts;

}
