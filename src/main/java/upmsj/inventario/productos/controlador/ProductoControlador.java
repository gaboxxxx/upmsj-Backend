package upmsj.inventario.productos.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.excepcion.DonanteNoEncontradoExcepcion;
import upmsj.inventario.productos.excepcion.RecursoNoEncontradoExcepcion;
import upmsj.inventario.productos.modelo.Donante;
import upmsj.inventario.productos.modelo.Producto;
import upmsj.inventario.productos.servicio.DonanteServicio;
import upmsj.inventario.productos.servicio.ProductoServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventario-app")
@CrossOrigin(value = "http://localhost:4200")


public class ProductoControlador {


    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private DonanteServicio donanteServicio;

    @GetMapping("/productos")
    public List<Producto> obtenerProductos() {
        List<Producto> productos = productoServicio.listarProductos();
        logger.info("Productos obtenidos:");
        productos.forEach((producto -> logger.info(producto.toString())));
        return productos;
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto a agregar: " + producto);

        if (producto.getNombreDonante() != null) {
            Donante donante = donanteServicio.buscarDonantePorNombre(producto.getNombreDonante());
            if (donante != null) {
                producto.setDonante(donante);
            } else {
                throw new DonanteNoEncontradoExcepcion("No se encontró el donante con el nombre: " + producto.getNombreDonante());
            }
        }

        return productoServicio.guardarProducto(producto);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el producto con el id: " + id);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido){
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if(producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        producto.setNombreProducto(productoRecibido.getNombreProducto());
        producto.setCantidad(productoRecibido.getCantidad());
        producto.setUnidadMedida(productoRecibido.getUnidadMedida());
        producto.setObservaciones(productoRecibido.getObservaciones());
        this.productoServicio.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id){
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        this.productoServicio.eliminarProductoPorId(producto.getIdProducto());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}