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

@RestController
@RequestMapping("/inventario-app/carrito")
@CrossOrigin(origins = "http://localhost:4200")
public class CarritoControlador {

    @Autowired
    private ICarritoServicio carritoServicio;

    @Autowired
    private ReportePDFServicio reportePDFServicio;

    @PostMapping
    public Carrito crearCarrito() {
        return carritoServicio.crearCarrito();
    }

    @GetMapping
    public List<Carrito> obtenerTodosLosCarritos() {
        return carritoServicio.obtenerTodosLosCarritos();
    }

    @GetMapping("/{id}")
    public Carrito obtenerCarritoPorId(@PathVariable Integer id) {
        return carritoServicio.obtenerCarritoPorId(id);
    }

    @PutMapping("/{id}")
    public Carrito actualizarCarrito(@PathVariable Integer id, @RequestBody Carrito carritoDetalles) {
        return carritoServicio.actualizarCarrito(id, carritoDetalles);
    }

    @DeleteMapping("/{id}")
    public void eliminarCarrito(@PathVariable Integer id) {
        carritoServicio.eliminarCarrito(id);
    }

    @PostMapping("/{carritoId}/items")
    public Carrito agregarItemAlCarrito(@PathVariable Integer carritoId, @RequestBody ItemCarrito itemCarrito) {
        return carritoServicio.agregarItemAlCarrito(carritoId, itemCarrito);
    }

    @DeleteMapping("/{carritoId}/items/{itemCarritoId}")
    public Carrito eliminarItemDelCarrito(@PathVariable Integer carritoId, @PathVariable Integer itemCarritoId) {
        return carritoServicio.eliminarItemDelCarrito(carritoId, itemCarritoId);
    }

    @PutMapping("/{id}/procesar")
    public Carrito procesarCarrito(@PathVariable Integer id) {
        return carritoServicio.procesarCarrito(id);
    }

    @PutMapping("/{id}/cancelar")
    public Carrito cancelarCarrito(@PathVariable Integer id) {
        return carritoServicio.cancelarCarrito(id);
    }

    @PutMapping("/{id}/vaciar")
    public void vaciarCarrito(@PathVariable Integer id) {
        carritoServicio.vaciarCarrito(id);
    }

    @PutMapping("/{idCarrito}/beneficiario/{idBeneficiario}")
    public Carrito asignarBeneficiario(@PathVariable Integer idCarrito, @PathVariable Integer idBeneficiario) {
        return carritoServicio.asignarBeneficiario(idCarrito, idBeneficiario);
    }

    @GetMapping("/fechas")
    public List<Carrito> obtenerCarritosPorFecha(@RequestParam Date fechaInicio, @RequestParam Date fechaFin) {
        return carritoServicio.obtenerCarritosPorFecha(fechaInicio, fechaFin);
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<InputStreamResource> generarReporte(@PathVariable Integer id) {
        try {
            ByteArrayInputStream bis = reportePDFServicio.generarReportePDF(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=reporte_donacion_" + id + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }
}