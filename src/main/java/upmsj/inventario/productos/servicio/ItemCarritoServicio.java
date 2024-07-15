package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.respositorio.ItemCarritoRepositorio;
import upmsj.inventario.productos.respositorio.ProductoRepositorio;

import java.util.List;

@Service // Indica que esta clase es un servicio de Spring
public class ItemCarritoServicio implements IItemCarritoServicio {

    @Autowired // Inyección de dependencias para ItemCarritoRepositorio
    private ItemCarritoRepositorio itemCarritoRepositorio;

    @Autowired // Inyección de dependencias para ProductoRepositorio
    private ProductoRepositorio productoRepositorio;

    @Override
    // Listar todos los items del carrito
    public List<ItemCarrito> listarItemsCarrito() {
        return itemCarritoRepositorio.findAll(); // Devuelve una lista de todos los items del carrito
    }

    @Override
    // Buscar un item del carrito por ID
    public ItemCarrito buscarItemCarritoPorId(Integer idItem) {
        return itemCarritoRepositorio.findById(idItem).orElseThrow(() -> new RuntimeException("Item no encontrado")); // Busca un item del carrito por ID
    }

    @Override
    // Guardar un item del carrito
    public ItemCarrito guardarItemCarrito(ItemCarrito itemCarrito) {
        if (productoRepositorio.existsById(itemCarrito.getProducto().getIdProducto())) {
            return itemCarritoRepositorio.save(itemCarrito); // Guarda el item del carrito si el producto existe
        } else {
            throw new IllegalArgumentException("Producto no existe");
        }
    }

    @Override
    // Actualizar un item del carrito
    public ItemCarrito actualizarItemCarrito(Integer idItem, ItemCarrito itemCarritoDetalles) {
        ItemCarrito itemCarrito = buscarItemCarritoPorId(idItem);
        if (itemCarrito != null) {
            itemCarrito.setProducto(itemCarritoDetalles.getProducto());
            itemCarrito.setCantidadItem(itemCarritoDetalles.getCantidadItem());
            itemCarrito.setCarrito(itemCarritoDetalles.getCarrito());
            return itemCarritoRepositorio.save(itemCarrito); // Actualiza y guarda el item del carrito
        } else {
            throw new IllegalArgumentException("ItemCarrito no existe");
        }
    }

    @Override
    // Eliminar un item del carrito por ID
    public void eliminarItemCarritoPorId(Integer idItem) {
        if (itemCarritoRepositorio.existsById(idItem)) {
            itemCarritoRepositorio.deleteById(idItem); // Elimina el item del carrito por ID
        } else {
            throw new RuntimeException("Item no encontrado");
        }
    }

    @Override
    // Actualizar la cantidad de un item del carrito
    public ItemCarrito actualizarCantidadItemCarrito(Integer idItem, Integer nuevaCantidad) {
        ItemCarrito itemCarrito = buscarItemCarritoPorId(idItem);
        if (itemCarrito != null) {
            itemCarrito.setCantidadItem(nuevaCantidad);
            itemCarrito.setCarrito(itemCarrito.getCarrito());
            return itemCarritoRepositorio.save(itemCarrito); // Actualiza y guarda la cantidad del item del carrito
        } else {
            throw new IllegalArgumentException("ItemCarrito no existe");
        }
    }
}