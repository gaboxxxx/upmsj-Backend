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

@RestController
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class BeneficiarioControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(BeneficiarioControlador.class);

    @Autowired
    private BeneficiarioServicio beneficiarioServicio;

    @GetMapping("/beneficiarios")
    public List<Beneficiario> obtenerBeneficiarios(){
        List<Beneficiario> beneficiarios = this.beneficiarioServicio.listarBeneficiarios();
        logger.info("Beneficiarios obtenidos:");
        beneficiarios.forEach((beneficiario -> logger.info(beneficiario.toString())));
        return beneficiarios;
    }

    @PostMapping("/beneficiarios")
    public Beneficiario agregarBeneficiario(@RequestBody Beneficiario beneficiario){
        logger.info("Beneficiario a agregar: " + beneficiario);
        return this.beneficiarioServicio.guardarBeneficiario(beneficiario);
    }

    @GetMapping("/beneficiarios/{id}")
    public ResponseEntity<Beneficiario> obtenerBeneficiarioPorId(
            @PathVariable int id){
        Beneficiario beneficiario =
                this.beneficiarioServicio.buscarBeneficiarioPorId(id);
        if(beneficiario != null)
            return ResponseEntity.ok(beneficiario);
        else
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
    }

    @PutMapping("/beneficiarios/{id}")
    public ResponseEntity<Beneficiario> actualizarBeneficiario(
            @PathVariable int id,
            @RequestBody Beneficiario beneficiarioRecibido){
        Beneficiario beneficiario = this.beneficiarioServicio.buscarBeneficiarioPorId(id);
        if(beneficiario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        beneficiario.setNombreBeneficiario(beneficiarioRecibido.getNombreBeneficiario());
        beneficiario.setCoordinadorBeneficiario(beneficiarioRecibido.getCoordinadorBeneficiario());
        this.beneficiarioServicio.guardarBeneficiario(beneficiario);
        return ResponseEntity.ok(beneficiario);
    }

    @DeleteMapping("/beneficiarios/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarBeneficiario(@PathVariable int id){
        Beneficiario beneficiario = beneficiarioServicio.buscarBeneficiarioPorId(id);
        if (beneficiario == null)
            throw new RecursoNoEncontradoExcepcion("No se encontró el id: " + id);
        this.beneficiarioServicio.eliminarBeneficiarioPorId(beneficiario.getIdBeneficiario());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
