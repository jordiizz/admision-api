package sv.edu.ues.occ.ingenieria.tpi135_2026.entity;

import java.io.Serializable;
import java.time.LocalDate;
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

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "documento_identidad")
    private String documentoIdentidad;

    @Column(name = "correo")
    private String correo;

    @Column(name = "id_carrera")
    private String idCarrera;

    @OneToMany(mappedBy = "idAspirante")
    private List<AspiranteJornada> listAspiranteJornada;

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

    public String getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }

    @JsonbTransient
    public List<AspiranteJornada> getListAspiranteJornada() {
        return listAspiranteJornada;
    }

    public void setListAspiranteJornada(List<AspiranteJornada> listAspiranteJornada) {
        this.listAspiranteJornada = listAspiranteJornada;
    }

    

    
}
