package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Donante;
import upmsj.inventario.productos.respositorio.DonanteRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class DonanteServicio implements IDonanteServicio{

    @Autowired
    private DonanteRepositorio donanteRepositorio;



    @Override
    public List<Donante> listarDonantes() {
        return this.donanteRepositorio.findAll();
    }

    @Override
    public Donante buscarDonantePorId(Integer idDonante) {
        Donante donante =
                this.donanteRepositorio.findById(idDonante).orElse(null);
        return donante;
    }

    public Donante buscarDonantePorNombre(String nombre) {
        Optional<Donante> donanteOptional = donanteRepositorio.findByNombreDonante(nombre);
        return donanteOptional.orElse(null);
    }




    @Override
    public Donante guardarDonante(Donante donante) {
        this.donanteRepositorio.save(donante);
        return donante;
    }

    @Override
    public void eliminarDonantePorId(Integer idDonante) {
        this.donanteRepositorio.deleteById(idDonante);
    }
}