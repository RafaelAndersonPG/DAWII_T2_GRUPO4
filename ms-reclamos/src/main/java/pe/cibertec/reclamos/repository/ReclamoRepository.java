package pe.cibertec.reclamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.reclamos.model.Reclamo;

public interface ReclamoRepository extends JpaRepository<Reclamo, Integer> {
}
