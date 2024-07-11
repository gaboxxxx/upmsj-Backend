package upmsj.inventario.productos.servicio;

import upmsj.inventario.productos.modelo.Beneficiario;

import java.util.List;

public interface IBeneficiarioServicio {
    public List<Beneficiario> listarBeneficiarios();

    public Beneficiario buscarBeneficiarioPorId(Integer idBeneficiario);

    public Beneficiario guardarBeneficiario(Beneficiario beneficiario);

    public void eliminarBeneficiarioPorId(Integer idBeneficiario);
}