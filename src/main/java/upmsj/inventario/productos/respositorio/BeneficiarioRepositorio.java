package upmsj.inventario.productos.respositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import upmsj.inventario.productos.modelo.Beneficiario;

public interface BeneficiarioRepositorio extends JpaRepository<Beneficiario, Integer> {
}
