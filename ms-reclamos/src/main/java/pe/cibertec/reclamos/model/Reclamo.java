package pe.cibertec.reclamos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reclamo")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numreclamo;

    private LocalDate fechareclamo;

    @Column(nullable = false, length = 100)
    private String nomcliente;

    @Column(nullable = false, length = 4)
    private String ultimos4tarjeta;

    @Column(nullable = false, length = 255)
    private String motivo;

    @Column(name = "nombre_analista", length = 100)
    private String nombreAnalista = "No asignado";

    private Integer estado = 1; // 1 pendiente, 2 procedente, 3 improcedente

    @Column(length = 255)
    private String observacion = "por evaluar";

    @PrePersist
    void antesDeGuardar() {
        if (fechareclamo == null) fechareclamo = LocalDate.now();
        if (nombreAnalista == null) nombreAnalista = "No asignado";
        if (estado == null) estado = 1;
        if (observacion == null) observacion = "por evaluar";
    }
}
