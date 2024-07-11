package upmsj.inventario.productos.respositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import upmsj.inventario.productos.modelo.Carrito;

public interface CarritoRepositorio extends JpaRepository<Carrito, Integer> {
}
