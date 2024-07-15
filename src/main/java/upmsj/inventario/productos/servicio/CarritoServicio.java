package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Beneficiario;
import upmsj.inventario.productos.modelo.Carrito;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.modelo.Producto;
import upmsj.inventario.productos.respositorio.BeneficiarioRepositorio;
import upmsj.inventario.productos.respositorio.CarritoRepositorio;
import upmsj.inventario.productos.respositorio.ItemCarritoRepositorio;
import upmsj.inventario.productos.respositorio.ProductoRepositorio;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Indica que esta clase es un servicio de Spring
public class CarritoServicio implements ICarritoServicio {

    @Autowired // Inyección de dependencias para CarritoRepositorio
    private CarritoRepositorio carritoRepositorio;

    @Autowired // Inyección de dependencias para ItemCarritoRepositorio
    private ItemCarritoRepositorio itemCarritoRepositorio;

    @Autowired // Inyección de dependencias para ProductoRepositorio
    private ProductoRepositorio productoRepositorio;

    @Autowired // Inyección de dependencias para BeneficiarioRepositorio
    private BeneficiarioRepositorio beneficiarioRepositorio;

    @Override
    // Crear un nuevo carrito
    public Carrito crearCarrito() {
        Carrito carrito = new Carrito();
        carrito.setFechaCreacion(new Date());
        carrito.setFechaActualizacion(new Date());
        carrito.setEstado("activo");
        return carritoRepositorio.save(carrito); // Guarda el carrito en el repositorio
    }

    @Override
    // Obtener todos los carritos
    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoRepositorio.findAll(); // Devuelve una lista de todos los carritos
    }

    @Override
    // Obtener un carrito por ID
    public Carrito obtenerCarritoPorId(Integer id) {
        return carritoRepositorio.findById(id).orElse(null); // Busca un carrito por ID
    }

    @Override
    // Guardar un carrito
    public Carrito guardarCarrito(Carrito carrito) {
        carrito.setFechaCreacion(new Date());
        carrito.setFechaActualizacion(new Date());
        return carritoRepositorio.save(carrito); // Guarda el carrito en el repositorio
    }

    @Override
    // Actualizar un carrito
    public Carrito actualizarCarrito(Integer id, Carrito carritoDetalles) {
        Carrito carrito = obtenerCarritoPorId(id);
        if (carrito != null) {
            carrito.setEstado(carritoDetalles.getEstado());
            carrito.setFechaActualizacion(new Date());
            return carritoRepositorio.save(carrito); // Actualiza y guarda el carrito
        } else {
            throw new IllegalArgumentException("Carrito no existe");
        }
    }

    @Override
    // Eliminar un carrito
    public void eliminarCarrito(Integer id) {
        carritoRepositorio.deleteById(id); // Elimina el carrito por ID
    }

    @Override
    // Agregar un item al carrito
    public Carrito agregarItemAlCarrito(Integer carritoId, ItemCarrito itemCarrito) {
        Optional<Carrito> carritoOpt = carritoRepositorio.findById(carritoId);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            itemCarrito.setCarrito(carrito);
            itemCarritoRepositorio.save(itemCarrito);
            carrito.getItems().add(itemCarrito);
            carrito.setFechaActualizacion(new Date());
            return carritoRepositorio.save(carrito); // Guarda el carrito con el nuevo item
        } else {
            throw new IllegalArgumentException("Carrito no existe");
        }
    }

    @Override
    // Eliminar un item del carrito
    public Carrito eliminarItemDelCarrito(Integer carritoId, Integer itemCarritoId) {
        Optional<Carrito> carritoOpt = carritoRepositorio.findById(carritoId);
        if (carritoOpt.isPresent()) {
            Carrito carrito = carritoOpt.get();
            Optional<ItemCarrito> itemCarritoOpt = itemCarritoRepositorio.findById(itemCarritoId);
            if (itemCarritoOpt.isPresent()) {
                ItemCarrito itemCarrito = itemCarritoOpt.get();
                carrito.getItems().remove(itemCarrito);
                itemCarritoRepositorio.delete(itemCarrito);
                carrito.setFechaActualizacion(new Date());
                return carritoRepositorio.save(carrito); // Guarda el carrito sin el item eliminado
            } else {
                throw new IllegalArgumentException("ItemCarrito no existe");
            }
        } else {
            throw new IllegalArgumentException("Carrito no existe");
        }
    }

    @Override
    // Procesar un carrito
    public Carrito procesarCarrito(Integer id) {
        Carrito carrito = carritoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        if ("procesado".equals(carrito.getEstado())) {
            throw new IllegalStateException("Esta donación ya fue realizada y no se puede modificar.");
        }
        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            if (producto.getCantidad() < item.getCantidadItem()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
            }
            producto.setCantidad(producto.getCantidad() - item.getCantidadItem());
            productoRepositorio.save(producto); // Actualiza la cantidad del producto
        }
        carrito.setEstado("procesado");
        carrito.setFechaActualizacion(new Date());
        return carritoRepositorio.save(carrito); // Guarda el carrito como procesado
    }

    @Override
    // Cancelar un carrito
    public Carrito cancelarCarrito(Integer id) {
        Carrito carrito = obtenerCarritoPorId(id);
        if (carrito != null) {
            carrito.setEstado("cancelado");
            carrito.setFechaActualizacion(new Date());
            return carritoRepositorio.save(carrito); // Guarda el carrito como cancelado
        } else {
            throw new IllegalArgumentException("Carrito no existe");
        }
    }

    @Override
    // Vaciar un carrito
    public void vaciarCarrito(Integer carritoId) {
        Carrito carrito = obtenerCarritoPorId(carritoId);
        if (carrito != null) {
            carrito.getItems().clear();
            carrito.setFechaActualizacion(new Date());
            carritoRepositorio.save(carrito); // Guarda el carrito vacío
        } else {
            throw new IllegalArgumentException("Carrito no existe");
        }
    }

    @Override
    // Obtener carritos por rango de fechas
    public List<Carrito> obtenerCarritosPorFecha(Date fechaInicio, Date fechaFin) {
        return carritoRepositorio.findAll().stream()
                .filter(carrito -> !carrito.getFechaCreacion().before(fechaInicio) && !carrito.getFechaCreacion().after(fechaFin))
                .collect(Collectors.toList()); // Filtra y devuelve los carritos en el rango de fechas
    }

    @Override
    // Asignar un beneficiario a un carrito
    public Carrito asignarBeneficiario(Integer idCarrito, Integer idBeneficiario) {
        Carrito carrito = carritoRepositorio.findById(idCarrito)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        Beneficiario beneficiario = beneficiarioRepositorio.findById(idBeneficiario)
                .orElseThrow(() -> new RuntimeException("Beneficiario no encontrado"));
        carrito.setBeneficiario(beneficiario);
        carrito.setFechaActualizacion(new Date());
        return carritoRepositorio.save(carrito); // Guarda el carrito con el beneficiario asignado
    }
}
