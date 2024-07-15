package upmsj.inventario.productos.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.servicio.IItemCarritoServicio;

import java.util.List;
import java.util.Map;

@RestController // Indica que esta clase es un controlador de Spring REST
// http://localhost:8080/inventario-app
@RequestMapping("/inventario-app/items") // Define la ruta base para los endpoints de este controlador
@CrossOrigin(value = "http://localhost:4200") // Permite solicitudes CORS desde la URL especificada

public class ItemCarritoControlador {

    @Autowired // Inyección de dependencias para IItemCarritoServicio
    private IItemCarritoServicio itemCarritoServicio;

    @GetMapping // Define el endpoint para listar todos los items del carrito
    public List<ItemCarrito> listarItemsCarrito() {
        return itemCarritoServicio.listarItemsCarrito(); // Devuelve una lista de todos los items del carrito
    }

    @GetMapping("/{id}") // Define el endpoint para buscar un item del carrito por ID
    public ItemCarrito buscarItemCarritoPorId(@PathVariable Integer id) {
        return itemCarritoServicio.buscarItemCarritoPorId(id); // Devuelve el item del carrito con el ID especificado
    }

    @PostMapping // Define el endpoint para guardar un nuevo item del carrito
    public ItemCarrito guardarItemCarrito(@RequestBody ItemCarrito itemCarrito) {
        return itemCarritoServicio.guardarItemCarrito(itemCarrito); // Guarda y devuelve el item del carrito
    }

    @PutMapping("/{id}") // Define el endpoint para actualizar un item del carrito por ID
    public ItemCarrito actualizarItemCarrito(
            @PathVariable Integer id,
            @RequestBody ItemCarrito itemCarritoDetalles) {
        return itemCarritoServicio.actualizarItemCarrito(id, itemCarritoDetalles); // Actualiza y devuelve el item del carrito
    }

    @DeleteMapping("/{id}") // Define el endpoint para eliminar un item del carrito por ID
    public void eliminarItemCarritoPorId(@PathVariable Integer id) {
        itemCarritoServicio.eliminarItemCarritoPorId(id); // Elimina el item del carrito con el ID especificado
    }

    @PutMapping("/{id}/cantidad") // Define el endpoint para actualizar la cantidad de un item del carrito por ID
    public ItemCarrito actualizarCantidadItemCarrito(@PathVariable Integer id, @RequestBody Map<String, Integer> update) {
        Integer nuevaCantidad = update.get("nuevaCantidad");
        ItemCarrito itemCarrito = itemCarritoServicio.buscarItemCarritoPorId(id);
        itemCarrito.setCantidadItem(nuevaCantidad); // Actualiza la cantidad del item del carrito
        return itemCarritoServicio.actualizarItemCarrito(id, itemCarrito); // Guarda y devuelve el item del carrito actualizado
    }
}
