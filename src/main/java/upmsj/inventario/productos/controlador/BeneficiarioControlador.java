package upmsj.inventario.productos.controlador;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.excepcion.RecursoNoEncontradoExcepcion;
import upmsj.inventario.productos.modelo.Beneficiario;
import upmsj.inventario.productos.servicio.BeneficiarioServicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador de Spring REST
@RequestMapping("inventario-app") // Define la ruta base para los endpoints de este controlador
@CrossOrigin(value = "http://localhost:4200") // Permite solicitudes CORS desde la URL especificada
public class BeneficiarioControlador {

    private static final Logger logger = LoggerFactory.getLogger(BeneficiarioControlador.class); // Logger para la clase

    @Autowired // Inyección de dependencias para BeneficiarioServicio
    private BeneficiarioServicio beneficiarioServicio;

    @GetMapping("/beneficiarios") // Define el endpoint para obtener todos los beneficiarios
    public List<Beneficiario> obtenerBeneficiarios() {
        List<Beneficiario> beneficiarios = this.beneficiarioServicio.listarBeneficiarios();
        logger.info("Beneficiarios obtenidos:");
        beneficiarios.forEach((beneficiario -> logger.info(beneficiario.toString()))); // Loguea cada beneficiario obtenido
        return beneficiarios;
    }

    @PostMapping("/beneficiarios") // Define el endpoint para agregar un nuevo beneficiario
    public Beneficiario agregarBeneficiario(@RequestBody Beneficiario beneficiario) {
        logger.info("Beneficiario a agregar: " + beneficiario);
        return this.beneficiarioServicio.guardarBeneficiario(beneficiario); // Guarda el beneficiario en el repositorio
    }

    @GetMapping("/beneficiarios/{id}") // Define el endpoint para obtener un beneficiario por ID
    public ResponseEntity<Beneficiario> obtenerBeneficiarioPorId(@PathVariable int id) {
        Beneficiario beneficiario = this.beneficiarioServicio.buscarBeneficiarioPorId(id);
        if (beneficiario != null)
            return ResponseEntity.ok(beneficiario); // Retorna el beneficiario si se encuentra
        else
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
    }

    @PutMapping("/beneficiarios/{id}") // Define el endpoint para actualizar un beneficiario por ID
    public ResponseEntity<Beneficiario> actualizarBeneficiario(
            @PathVariable int id,
            @RequestBody Beneficiario beneficiarioRecibido) {
        Beneficiario beneficiario = this.beneficiarioServicio.buscarBeneficiarioPorId(id);
        if (beneficiario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        beneficiario.setNombreBeneficiario(beneficiarioRecibido.getNombreBeneficiario());
        beneficiario.setCoordinadorBeneficiario(beneficiarioRecibido.getCoordinadorBeneficiario());
        this.beneficiarioServicio.guardarBeneficiario(beneficiario); // Guarda el beneficiario actualizado
        return ResponseEntity.ok(beneficiario);
    }

    @DeleteMapping("/beneficiarios/{id}") // Define el endpoint para eliminar un beneficiario por ID
    public ResponseEntity<Map<String, Boolean>> eliminarBeneficiario(@PathVariable int id) {
        Beneficiario beneficiario = beneficiarioServicio.buscarBeneficiarioPorId(id);
        if (beneficiario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        this.beneficiarioServicio.eliminarBeneficiarioPorId(beneficiario.getIdBeneficiario()); // Elimina el beneficiario por ID
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE); // Retorna una respuesta indicando que el beneficiario fue eliminado
        return ResponseEntity.ok(respuesta);
    }
}