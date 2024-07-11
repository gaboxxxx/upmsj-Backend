package upmsj.inventario.productos.servicio;

import upmsj.inventario.productos.modelo.ItemCarrito;

import java.util.List;

public interface IItemCarritoServicio {

    List<ItemCarrito> listarItemsCarrito();

    ItemCarrito buscarItemCarritoPorId(Integer idItem);

    ItemCarrito guardarItemCarrito(ItemCarrito itemCarrito);

    ItemCarrito actualizarItemCarrito(Integer id, ItemCarrito itemCarritoDetalles);

    void eliminarItemCarritoPorId(Integer idItem);

    ItemCarrito actualizarCantidadItemCarrito(Integer idItem, Integer nuevaCantidad);
}
