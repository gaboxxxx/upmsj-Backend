package upmsj.inventario.productos.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Beneficiario;
import upmsj.inventario.productos.respositorio.BeneficiarioRepositorio;

import java.util.List;

@Service
public class BeneficiarioServicio implements IBeneficiarioServicio {

    @Autowired
    private BeneficiarioRepositorio beneficiarioRepositorio;

    @Override
    public List<Beneficiario> listarBeneficiarios() {
        return this.beneficiarioRepositorio.findAll();
    }

    @Override
    public Beneficiario buscarBeneficiarioPorId(Integer idBeneficiario) {
        Beneficiario beneficiario =
                this.beneficiarioRepositorio.findById(idBeneficiario).orElse(null);
        return beneficiario;
    }


    @Override
    public Beneficiario guardarBeneficiario(Beneficiario beneficiario) {
        this.beneficiarioRepositorio.save(beneficiario);
        return beneficiario;
    }

    @Override
    public void eliminarBeneficiarioPorId(Integer idBeneficiario) {
        this.beneficiarioRepositorio.deleteById(idBeneficiario);
    }
}
