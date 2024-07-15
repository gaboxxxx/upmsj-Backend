package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Producto;
import upmsj.inventario.productos.respositorio.ProductoRepositorio;

import java.util.List;

@Service // Indica que esta clase es un servicio de Spring
public class ProductoServicio implements IProductoServicio {

    @Autowired // Inyección de dependencias de Spring para ProductoRepositorio
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        return this.productoRepositorio.findAll(); // Devuelve una lista de todos los productos
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto) {
        Producto producto = this.productoRepositorio.findById(idProducto).orElse(null); // Busca un producto por ID
        return producto;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        this.productoRepositorio.save(producto); // Guarda un producto en el repositorio
        return producto;
    }

    @Override
    public void eliminarProductoPorId(Integer idProducto) {
        this.productoRepositorio.deleteById(idProducto); // Elimina un producto por ID
    }
}
