package org.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venta")
public class Venta {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "monto")
    private double monto;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado; // relacion many to one con empleado

    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente; // relacion many to one con cliente

    @ManyToMany
    @JoinTable(
            name = "venta_coche",
            joinColumns = @JoinColumn(name = "venta_id"),
            inverseJoinColumns = @JoinColumn(name = "coche_id")
    )
    private List<Coche> coches = new ArrayList<>();

    // Constructor
    public Venta() {
    }

    public Venta(LocalDate fecha, double monto, Empleado empleado, Cliente cliente) {
        this.fecha = fecha;
        this.monto = monto;
        this.empleado = empleado;
        this.cliente = cliente;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Coche> getCoches() {
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n• VENTA ")
                .append("\n | ID: ").append(id)
                .append(" | Fecha: ").append(fecha)
                .append(" | Monto: ").append(monto)
                .append(" | ID Empleado: ").append(empleado.getId())
                .append(" | ID Cliente: ").append(cliente.getId())
                .append("\n Coches en la venta:");
                for (Coche c : coches) {
                sb.append("\n   - ID Coche: ").append(c.getId())
                        .append(" | Modelo: ").append(c.getModelo())
                        .append(" | Marca: ").append(c.getMarca())
                        .append("\n");
            }

        return sb.toString();
    }
}
