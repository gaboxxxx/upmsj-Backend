package upmsj.inventario.productos.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.excepcion.RecursoNoEncontradoExcepcion;
import upmsj.inventario.productos.modelo.Donante;
import upmsj.inventario.productos.servicio.DonanteServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador de Spring REST
// http://localhost:8080/inventario-app
@RequestMapping("inventario-app") // Define la ruta base para los endpoints de este controlador
@CrossOrigin(value = "http://localhost:4200") // Permite solicitudes CORS desde la URL especificada
public class DonanteControlador {

    private static final Logger logger = LoggerFactory.getLogger(DonanteControlador.class); // Logger para la clase

    @Autowired // Inyección de dependencias para DonanteServicio
    private DonanteServicio donanteServicio;

    // http://localhost:8080/inventario-app/donantes
    @GetMapping("/donantes") // Define el endpoint para obtener todos los donantes
    public List<Donante> obtenerDonantes() {
        List<Donante> donantes = this.donanteServicio.listarDonantes();
        logger.info("Donantes obtenidos:");
        donantes.forEach((donante -> logger.info(donante.toString()))); // Loguea cada donante obtenido
        return donantes;
    }

    @PostMapping("/donantes") // Define el endpoint para agregar un nuevo donante
    public Donante agregarDonante(@RequestBody Donante donante) {
        logger.info("Donante a agregar: " + donante);
        return this.donanteServicio.guardarDonante(donante); // Guarda el donante en el repositorio
    }

    @GetMapping("/donantes/{id}") // Define el endpoint para obtener un donante por ID
    public ResponseEntity<Donante> obtenerDonantePorId(@PathVariable int id) {
        Donante donante = this.donanteServicio.buscarDonantePorId(id);
        if (donante != null)
            return ResponseEntity.ok(donante); // Retorna el donante si se encuentra
        else
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
    }

    @PutMapping("/donantes/{id}") // Define el endpoint para actualizar un donante por ID
    public ResponseEntity<Donante> actualizarDonante(
            @PathVariable int id,
            @RequestBody Donante donanteRecibido) {
        Donante donante = this.donanteServicio.buscarDonantePorId(id);
        if (donante == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        donante.setNombreDonante(donanteRecibido.getNombreDonante());
        donante.setDireccionDonante(donanteRecibido.getDireccionDonante());
        this.donanteServicio.guardarDonante(donante); // Guarda el donante actualizado
        return ResponseEntity.ok(donante);
    }

    @DeleteMapping("/donantes/{id}") // Define el endpoint para eliminar un donante por ID
    public ResponseEntity<Map<String, Boolean>> eliminarDonante(@PathVariable int id) {
        Donante donante = donanteServicio.buscarDonantePorId(id);
        if (donante == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        this.donanteServicio.eliminarDonantePorId(donante.getIdDonante()); // Elimina el donante por ID
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE); // Retorna una respuesta indicando que el donante fue eliminado
        return ResponseEntity.ok(respuesta);
    }
}
