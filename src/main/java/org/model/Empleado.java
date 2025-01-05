package org.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleado")
public class Empleado {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "puesto")
    private String puesto;

    @Column(name = "salario")
    private double salario;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Reparacion> reparaciones = new ArrayList<>();

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();


    // Constructor
    public Empleado() {
    }

    public Empleado(String nombre, String puesto, double salario, Usuario usuario) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
        this.usuario = usuario;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(List<Reparacion> reparaciones) {
        this.reparaciones = reparaciones;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------------------------------------------------------------------------------------\n" +
                "EMPLEADO: " + "\n" +
                "ID:              " + id + "\n" +
                "Nombre:          " + nombre + "\n" +
                "Puesto:          " + puesto + "\n" +
                "Salario:         " + salario + "\n" +
                "Usuario:         " + usuario + "\n" +
                "Reparaciones:     " + reparaciones + "\n" +
                "Ventas:          " + ventas + "\n";
    }
}

