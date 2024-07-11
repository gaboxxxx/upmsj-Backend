package upmsj.inventario.productos.respositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import upmsj.inventario.productos.modelo.Carrito;
import upmsj.inventario.productos.modelo.ItemCarrito;

import java.util.List;

public interface ItemCarritoRepositorio extends JpaRepository<ItemCarrito, Integer> {
    List<ItemCarrito> findByCarrito(Carrito carrito);
}
