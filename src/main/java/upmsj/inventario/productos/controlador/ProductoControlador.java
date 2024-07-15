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

@RestController // Indica que esta clase es un controlador de Spring REST
@RequestMapping("/inventario-app") // Define la ruta base para los endpoints de este controlador
@CrossOrigin(value = "http://localhost:4200") // Permite solicitudes CORS desde la URL especificada

public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class); // Logger para la clase

    @Autowired // Inyección de dependencias para ProductoServicio
    private ProductoServicio productoServicio;

    @Autowired // Inyección de dependencias para DonanteServicio
    private DonanteServicio donanteServicio;

    @GetMapping("/productos") // Define el endpoint para obtener todos los productos
    public List<Producto> obtenerProductos() {
        List<Producto> productos = productoServicio.listarProductos();
        logger.info("Productos obtenidos:");
        productos.forEach((producto -> logger.info(producto.toString()))); // Loguea cada producto obtenido
        return productos;
    }

    @PostMapping("/productos") // Define el endpoint para agregar un nuevo producto
    public Producto agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto a agregar: " + producto);

        if (producto.getNombreDonante() != null) {
            Donante donante = donanteServicio.buscarDonantePorNombre(producto.getNombreDonante());
            if (donante != null) {
                producto.setDonante(donante); // Asocia el donante encontrado con el producto
            } else {
                throw new DonanteNoEncontradoExcepcion("No se encontró el donante con el nombre: " + producto.getNombreDonante());
            }
        }

        return productoServicio.guardarProducto(producto); // Guarda el producto en el repositorio
    }

    @GetMapping("/productos/{id}") // Define el endpoint para obtener un producto por ID
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto); // Retorna el producto si se encuentra
        } else {
            throw new RecursoNoEncontradoExcepcion("No se encontró el producto con el id: " + id);
        }
    }

    @PutMapping("/productos/{id}") // Define el endpoint para actualizar un producto por ID
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido) {
        Producto producto = this.productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        producto.setNombreProducto(productoRecibido.getNombreProducto());
        producto.setCantidad(productoRecibido.getCantidad());
        producto.setUnidadMedida(productoRecibido.getUnidadMedida());
        producto.setObservaciones(productoRecibido.getObservaciones());
        this.productoServicio.guardarProducto(producto); // Guarda el producto actualizado
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}") // Define el endpoint para eliminar un producto por ID
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id) {
        Producto producto = productoServicio.buscarProductoPorId(id);
        if (producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        this.productoServicio.eliminarProductoPorId(producto.getIdProducto()); // Elimina el producto por ID
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE); // Retorna una respuesta indicando que el producto fue eliminado
        return ResponseEntity.ok(respuesta);
    }
}