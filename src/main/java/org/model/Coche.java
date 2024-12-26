package org.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coche")
public class Coche {
    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "año")
    private int anio;

    @Column(name = "precio")
    private double precio;

    @ManyToMany(mappedBy = "coches", cascade = CascadeType.ALL)
    private List<Venta> ventas = new ArrayList<>();

    @OneToMany(mappedBy = "coche", cascade = CascadeType.ALL)
    private List<Reparacion> reparaciones = new ArrayList<>();

    @ManyToMany(mappedBy = "coches", cascade = CascadeType.ALL)
    private List<Cliente> clientes = new ArrayList<>();


    // Constructor
    public Coche() {
    }

    public Coche(String marca, String modelo, int anio, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String toString() {
        return "---------------------------------------------\n" +
                "Coche: " + "\n" +
                "ID:              " + id + "\n" +
                "Marca:           " + marca + "\n" +
                "Modelo:          " + modelo + "\n" +
                "Año:             " + anio + "\n" +
                "Precio:          " + precio + "\n";
    }
}
