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
@Table(name="donante")

public class Donante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idDonante;
    String nombreDonante;
    String direccionDonante;
    String telefonoDonante;
    String emailDonante;
    String tipoDonante;
    String observacionesDonante;


}
