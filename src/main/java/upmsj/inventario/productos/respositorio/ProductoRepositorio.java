package upmsj.inventario.productos.respositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import upmsj.inventario.productos.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {



}
