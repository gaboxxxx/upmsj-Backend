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

@RestController
//http://locahost:8080/inventario-app
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")
public class DonanteControlador {

    private static final Logger logger =
            LoggerFactory.getLogger(DonanteControlador.class);

    @Autowired
    private DonanteServicio donanteServicio;

    //http://locahost:8080/inventario-app/donantes
    @GetMapping("/donantes")
    public List<Donante> obtenerDonantes(){
        List<Donante> donantes = this.donanteServicio.listarDonantes();
        logger.info("Donantes obtenidos:");
        donantes.forEach((donante -> logger.info(donante.toString())));
        return donantes;
    }

    @PostMapping("/donantes")
    public Donante agregarDonante(@RequestBody Donante donante){
        logger.info("Donante a agregar: " + donante);
        return this.donanteServicio.guardarDonante(donante);
    }

    @GetMapping("/donantes/{id}")
    public ResponseEntity<Donante> obtenerDonantePorId(
            @PathVariable int id){
        Donante donante =
                this.donanteServicio.buscarDonantePorId(id);
        if(donante != null)
            return ResponseEntity.ok(donante);
        else
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
    }

    @PutMapping("/donantes/{id}")
    public ResponseEntity<Donante> actualizarDonante(
            @PathVariable int id,
            @RequestBody Donante donanteRecibido){
        Donante donante = this.donanteServicio.buscarDonantePorId(id);
        if(donante == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        donante.setNombreDonante(donanteRecibido.getNombreDonante());
        donante.setDireccionDonante(donanteRecibido.getDireccionDonante());
        this.donanteServicio.guardarDonante(donante);
        return ResponseEntity.ok(donante);
    }

    @DeleteMapping("/donantes/{id}")
    public ResponseEntity<Map<String, Boolean>>
    eliminarDonante(@PathVariable int id){
        Donante donante = donanteServicio.buscarDonantePorId(id);
        if (donante == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        this.donanteServicio.eliminarDonantePorId(donante.getIdDonante());
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
