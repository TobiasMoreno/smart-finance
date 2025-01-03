package tobias.moreno.financial.app.entities;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class BaseEntity {

	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
