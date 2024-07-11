package upmsj.inventario.productos.servicio;

import upmsj.inventario.productos.modelo.Carrito;
import upmsj.inventario.productos.modelo.ItemCarrito;

import java.util.Date;
import java.util.List;


public interface ICarritoServicio {
    Carrito crearCarrito();
    List<Carrito> obtenerTodosLosCarritos();

    Carrito obtenerCarritoPorId(Integer id);

    Carrito guardarCarrito(Carrito carrito);

    Carrito actualizarCarrito(Integer id, Carrito carritoDetalles);

    void eliminarCarrito(Integer id);

    Carrito agregarItemAlCarrito(Integer carritoId, ItemCarrito itemCarrito);

    Carrito eliminarItemDelCarrito(Integer carritoId, Integer itemCarritoId);

    Carrito procesarCarrito(Integer id);

    Carrito cancelarCarrito(Integer id);

    void vaciarCarrito(Integer carritoId);

    List<Carrito> obtenerCarritosPorFecha(Date fechaInicio, Date fechaFin);

    Carrito asignarBeneficiario(Integer idCarrito, Integer idBeneficiario);
}