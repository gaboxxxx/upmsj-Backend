package upmsj.inventario.productos.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.modelo.Carrito;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.servicio.ICarritoServicio;
import upmsj.inventario.productos.servicio.ReportePDFServicio;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@RestController // Indica que esta clase es un controlador de Spring REST
@RequestMapping("/inventario-app/carrito") // Define la ruta base para los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:4200") // Permite solicitudes CORS desde la URL especificada
public class CarritoControlador {

    @Autowired // Inyección de dependencias para ICarritoServicio
    private ICarritoServicio carritoServicio;

    @Autowired // Inyección de dependencias para ReportePDFServicio
    private ReportePDFServicio reportePDFServicio;

    @PostMapping // Define el endpoint para crear un nuevo carrito
    public Carrito crearCarrito() {
        return carritoServicio.crearCarrito(); // Crea y devuelve un nuevo carrito
    }

    @GetMapping // Define el endpoint para obtener todos los carritos
    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoServicio.obtenerTodosLosCarritos(); // Devuelve una lista de todos los carritos
    }

    @GetMapping("/{id}") // Define el endpoint para obtener un carrito por ID
    public Carrito obtenerCarritoPorId(@PathVariable Integer id) {
        return carritoServicio.obtenerCarritoPorId(id); // Devuelve el carrito con el ID especificado
    }

    @PutMapping("/{id}") // Define el endpoint para actualizar un carrito por ID
    public Carrito actualizarCarrito(@PathVariable Integer id, @RequestBody Carrito carritoDetalles) {
        return carritoServicio.actualizarCarrito(id, carritoDetalles); // Actualiza y devuelve el carrito
    }

    @DeleteMapping("/{id}") // Define el endpoint para eliminar un carrito por ID
    public void eliminarCarrito(@PathVariable Integer id) {
        carritoServicio.eliminarCarrito(id); // Elimina el carrito con el ID especificado
    }

    @PostMapping("/{carritoId}/items") // Define el endpoint para agregar un item al carrito
    public Carrito agregarItemAlCarrito(@PathVariable Integer carritoId, @RequestBody ItemCarrito itemCarrito) {
        return carritoServicio.agregarItemAlCarrito(carritoId, itemCarrito); // Agrega y devuelve el carrito con el nuevo item
    }

    @DeleteMapping("/{carritoId}/items/{itemCarritoId}") // Define el endpoint para eliminar un item del carrito
    public Carrito eliminarItemDelCarrito(@PathVariable Integer carritoId, @PathVariable Integer itemCarritoId) {
        return carritoServicio.eliminarItemDelCarrito(carritoId, itemCarritoId); // Elimina el item y devuelve el carrito actualizado
    }

    @PutMapping("/{id}/procesar") // Define el endpoint para procesar un carrito
    public Carrito procesarCarrito(@PathVariable Integer id) {
        return carritoServicio.procesarCarrito(id); // Procesa y devuelve el carrito
    }

    @PutMapping("/{id}/cancelar") // Define el endpoint para cancelar un carrito
    public Carrito cancelarCarrito(@PathVariable Integer id) {
        return carritoServicio.cancelarCarrito(id); // Cancela y devuelve el carrito
    }

    @PutMapping("/{id}/vaciar") // Define el endpoint para vaciar un carrito
    public void vaciarCarrito(@PathVariable Integer id) {
        carritoServicio.vaciarCarrito(id); // Vacía el carrito con el ID especificado
    }

    @PutMapping("/{idCarrito}/beneficiario/{idBeneficiario}") // Define el endpoint para asignar un beneficiario a un carrito
    public Carrito asignarBeneficiario(@PathVariable Integer idCarrito, @PathVariable Integer idBeneficiario) {
        return carritoServicio.asignarBeneficiario(idCarrito, idBeneficiario); // Asigna el beneficiario y devuelve el carrito
    }

    @GetMapping("/fechas") // Define el endpoint para obtener carritos por rango de fechas
    public List<Carrito> obtenerCarritosPorFecha(@RequestParam Date fechaInicio, @RequestParam Date fechaFin) {
        return carritoServicio.obtenerCarritosPorFecha(fechaInicio, fechaFin); // Devuelve una lista de carritos en el rango de fechas
    }

    @GetMapping("/{id}/reporte") // Define el endpoint para generar un reporte PDF de un carrito
    public ResponseEntity<InputStreamResource> generarReporte(@PathVariable Integer id) {
        try {
            ByteArrayInputStream bis = reportePDFServicio.generarReportePDF(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reporte_donacion_" + id + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis)); // Devuelve el reporte PDF
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null); // Maneja cualquier excepción y devuelve una respuesta de error
        }
    }
}
