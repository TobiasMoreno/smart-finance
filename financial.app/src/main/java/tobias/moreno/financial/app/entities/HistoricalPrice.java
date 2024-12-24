package tobias.moreno.financial.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historical_prices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalPrice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String symbol;
	private float price;
	private LocalDateTime timestamp;
}
