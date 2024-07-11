package upmsj.inventario.productos.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer idProducto;

    @NotNull
    private String nombreProducto;

    @NotNull
    private String categoria;

    @NotNull
    private Integer cantidad;

    @NotNull
    private String unidadMedida;

    @NotNull
    private String personaRecibe;

    @NotNull
    private String personaRegistro;

    @NotNull
    private Boolean estado;

    private String observaciones;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;

    private Boolean estadoEntrega;

    @NotNull
    @Column(name = "idDonante") // Mapea al campo en la base de datos
    private Integer idDonante; // Campo adicional para almacenar el ID del donante

    @ManyToOne
    @JoinColumn(name = "idDonante", insertable = false, updatable = false) // No insertable ni actualizable ya que solo es para mapear la relación
    private Donante donante;


    public String getNombreDonante() {
        return donante != null ? donante.getNombreDonante() : null;
    }
}