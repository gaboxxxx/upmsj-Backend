package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Donante;
import upmsj.inventario.productos.respositorio.DonanteRepositorio;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta clase es un servicio de Spring
public class DonanteServicio implements IDonanteServicio {

    @Autowired // Inyección de dependencias de Spring para DonanteRepositorio
    private DonanteRepositorio donanteRepositorio;

    @Override
    public List<Donante> listarDonantes() {
        return this.donanteRepositorio.findAll(); // Devuelve una lista de todos los donantes
    }

    @Override
    public Donante buscarDonantePorId(Integer idDonante) {
        Donante donante = this.donanteRepositorio.findById(idDonante).orElse(null); // Busca un donante por ID
        return donante;
    }

    public Donante buscarDonantePorNombre(String nombre) {
        Optional<Donante> donanteOptional = donanteRepositorio.findByNombreDonante(nombre); // Busca un donante por nombre
        return donanteOptional.orElse(null);
    }

    @Override
    public Donante guardarDonante(Donante donante) {
        this.donanteRepositorio.save(donante); // Guarda un donante en el repositorio
        return donante;
    }

    @Override
    public void eliminarDonantePorId(Integer idDonante) {
        this.donanteRepositorio.deleteById(idDonante); // Elimina un donante por ID
    }
}