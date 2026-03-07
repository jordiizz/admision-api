package sv.edu.ues.occ.ingenieria.tpi135_2026.admision_api.webapp.core.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "aspirante")
public class Aspirante implements Serializable{

    @Id
    @Column(name = "id_aspirante")
    private UUID idAspirante;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "fecha_creacion", nullable = false)
    private OffsetDateTime fechaCreacion;

    @OneToMany(mappedBy = "idAspirante")
    private List<AspiranteOpcion> listAspiranteOpcion;

    public Aspirante(UUID idAspirante) {
        this.idAspirante = idAspirante;
    }

    public Aspirante(){}

    public UUID getIdAspirante() {
        return idAspirante;
    }

    public void setIdAspirante(UUID idAspirante) {
        this.idAspirante = idAspirante;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonbTransient
    public List<AspiranteOpcion> getListAspiranteOpcion() {
        return listAspiranteOpcion;
    }

    public void setListAspiranteOpcion(List<AspiranteOpcion> listAspiranteOpcion) {
        this.listAspiranteOpcion = listAspiranteOpcion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAspirante == null) ? 0 : idAspirante.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aspirante other = (Aspirante) obj;
        if (idAspirante == null) {
            if (other.idAspirante != null)
                return false;
        } else if (!idAspirante.equals(other.idAspirante))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Aspirante [idAspirante=" + idAspirante + ", nombres=" + nombres + ", apellidos=" + apellidos + 
               ", fechaNacimiento=" + fechaNacimiento + ", documentoIdentidad=" + documentoIdentidad + 
               ", correo=" + correo + ", fechaCreacion=" + fechaCreacion + "]";
    }
}
