package upmsj.inventario.productos.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    private String username;
    private String password;
    private Integer intentosFallidos;
    @Column(columnDefinition = "TINYINT")
    private Byte bloqueado;

}