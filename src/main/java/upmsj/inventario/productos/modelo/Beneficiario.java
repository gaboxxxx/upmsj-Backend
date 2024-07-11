package upmsj.inventario.productos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="beneficiarios")
public class Beneficiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idBeneficiario;
    String nombreBeneficiario;
    String servicioBeneficiario;
    String coordinadorBeneficiario;
    String eventoenBeficiario;
    String observacionesBeneficiario;


}
