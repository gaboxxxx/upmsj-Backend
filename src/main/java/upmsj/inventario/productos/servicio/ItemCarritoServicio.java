package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.respositorio.ItemCarritoRepositorio;
import upmsj.inventario.productos.respositorio.ProductoRepositorio;

import java.util.List;

@Service
public class ItemCarritoServicio   implements  IItemCarritoServicio{

    @Autowired
    private ItemCarritoRepositorio itemCarritoRepositorio;

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<ItemCarrito> listarItemsCarrito() {
        return itemCarritoRepositorio.findAll();
    }

    @Override
    public ItemCarrito buscarItemCarritoPorId(Integer idItem) {
        return itemCarritoRepositorio.findById(idItem).orElseThrow(() -> new RuntimeException("Item no encontrado"));
    }

    @Override
    public ItemCarrito guardarItemCarrito(ItemCarrito itemCarrito) {
        if (productoRepositorio.existsById(itemCarrito.getProducto().getIdProducto())) {
            return itemCarritoRepositorio.save(itemCarrito);
        } else {
            throw new IllegalArgumentException("Producto no existe");
        }    }

    @Override
    public ItemCarrito actualizarItemCarrito(Integer idItem, ItemCarrito itemCarritoDetalles) {
        ItemCarrito itemCarrito = buscarItemCarritoPorId(idItem);
        if (itemCarrito != null) {
            itemCarrito.setProducto(itemCarritoDetalles.getProducto());
            itemCarrito.setCantidadItem(itemCarritoDetalles.getCantidadItem());
            itemCarrito.setCarrito(itemCarritoDetalles.getCarrito());
            return itemCarritoRepositorio.save(itemCarrito);
        } else {
            throw new IllegalArgumentException("ItemCarrito no existe");
        }
    }

    @Override
    public void eliminarItemCarritoPorId(Integer idItem) {
        if (itemCarritoRepositorio.existsById(idItem)) {
            itemCarritoRepositorio.deleteById(idItem);
        } else {
            throw new RuntimeException("Item no encontrado");
        }
    }

    @Override

    public ItemCarrito actualizarCantidadItemCarrito(Integer idItem, Integer nuevaCantidad) {
        ItemCarrito itemCarrito = buscarItemCarritoPorId(idItem);
        if (itemCarrito != null) {
            itemCarrito.setCantidadItem(nuevaCantidad);
            itemCarrito.setCarrito(itemCarrito.getCarrito());

            return itemCarritoRepositorio.save(itemCarrito);
        } else {
            throw new IllegalArgumentException("ItemCarrito no existe");
        }
    }
}