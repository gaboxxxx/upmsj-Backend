package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Beneficiario;
import upmsj.inventario.productos.respositorio.BeneficiarioRepositorio;

import java.util.List;

@Service // Indica que esta clase es un servicio de Spring
public class BeneficiarioServicio implements IBeneficiarioServicio {

    @Autowired // Inyección de dependencias de Spring para BeneficiarioRepositorio
    private BeneficiarioRepositorio beneficiarioRepositorio;

    @Override
    public List<Beneficiario> listarBeneficiarios() {
        return this.beneficiarioRepositorio.findAll(); // Devuelve una lista de todos los beneficiarios
    }

    @Override
    public Beneficiario buscarBeneficiarioPorId(Integer idBeneficiario) {
        Beneficiario beneficiario = this.beneficiarioRepositorio.findById(idBeneficiario).orElse(null); // Busca un beneficiario por ID
        return beneficiario;
    }

    @Override
    public Beneficiario guardarBeneficiario(Beneficiario beneficiario) {
        this.beneficiarioRepositorio.save(beneficiario); // Guarda un beneficiario en el repositorio
        return beneficiario;
    }

    @Override
    public void eliminarBeneficiarioPorId(Integer idBeneficiario) {
        this.beneficiarioRepositorio.deleteById(idBeneficiario); // Elimina un beneficiario por ID
    }
}
