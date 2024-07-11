package upmsj.inventario.productos.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.servicio.IItemCarritoServicio;

import java.util.List;
import java.util.Map;

@RestController
//http://locahost:8080/inventario-app
@RequestMapping("/inventario-app/items")
@CrossOrigin(value = "http://localhost:4200")

public class ItemCarritoControlador {

    @Autowired
    private IItemCarritoServicio itemCarritoServicio;

    @GetMapping
    public List<ItemCarrito> listarItemsCarrito() {
        return itemCarritoServicio.listarItemsCarrito();
    }

    @GetMapping("/{id}")
    public ItemCarrito buscarItemCarritoPorId(@PathVariable Integer id) {
        return itemCarritoServicio.buscarItemCarritoPorId(id);
    }

    @PostMapping
    public ItemCarrito guardarItemCarrito(@RequestBody ItemCarrito itemCarrito) {
        return itemCarritoServicio.guardarItemCarrito(itemCarrito);
    }

    @PutMapping("/{id}")
    public ItemCarrito actualizarItemCarrito(
            @PathVariable Integer id,
            @RequestBody ItemCarrito itemCarritoDetalles) {
        return itemCarritoServicio.actualizarItemCarrito(id, itemCarritoDetalles);
    }

    @DeleteMapping("/{id}")
    public void eliminarItemCarritoPorId(@PathVariable Integer id) {
        itemCarritoServicio.eliminarItemCarritoPorId(id);
    }

    @PutMapping("/{id}/cantidad")
    public ItemCarrito actualizarCantidadItemCarrito(@PathVariable Integer id, @RequestBody Map<String, Integer> update) {
        Integer nuevaCantidad = update.get("nuevaCantidad");
        ItemCarrito itemCarrito = itemCarritoServicio.buscarItemCarritoPorId(id);
        itemCarrito.setCantidadItem(nuevaCantidad);
        return itemCarritoServicio.actualizarItemCarrito(id, itemCarrito);
    }
}