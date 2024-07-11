package upmsj.inventario.productos.respositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import upmsj.inventario.productos.modelo.Donante;

import java.util.Optional;

public interface DonanteRepositorio extends JpaRepository<Donante, Integer> {
    Optional<Donante> findByNombreDonante(String nombreDonante);
}


