package org.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reparacion")
public class Reparacion {
    // Atributo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "costo")
    private double costo;

    @ManyToOne
    @JoinColumn(name = "coche_id")
    private Coche coche;

    @ManyToOne
    @JoinColumn(name="empleado_id")
    private Empleado empleado;

    // Constructor
    public Reparacion() {
    }

    // Constructor sin ID
    public Reparacion(String descripcion, LocalDate fecha, double costo, Coche coche, Empleado empleado) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.costo = costo;
        this.coche = coche;
        this.empleado = empleado;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Coche getCoche() {
        return coche;
    }

    public void setCoche(Coche coche) {
        this.coche = coche;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
