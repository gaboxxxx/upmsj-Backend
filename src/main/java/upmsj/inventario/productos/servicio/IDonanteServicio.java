package upmsj.inventario.productos.servicio;

import upmsj.inventario.productos.modelo.Donante;

import java.util.List;

public interface IDonanteServicio {


    List<Donante> listarDonantes();

    Donante buscarDonantePorId(Integer idDonante);

    Donante guardarDonante(Donante donante);

    public void eliminarDonantePorId(Integer idDonante);
}
