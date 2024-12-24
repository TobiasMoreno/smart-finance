package tobias.moreno.financial.app.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alerts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "price_threshold")
	private float priceThreshold;
	private String symbol;
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
