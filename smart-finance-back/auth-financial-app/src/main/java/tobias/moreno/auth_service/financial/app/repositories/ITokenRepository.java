package tobias.moreno.auth_service.financial.app.repositories;

import tobias.moreno.auth_service.financial.app.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {
	Optional<TokenEntity> findByToken(String token);
}
