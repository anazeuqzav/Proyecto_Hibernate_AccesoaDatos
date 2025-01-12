package org.utils;

import org.model.*;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestDataGenerator {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        try {
            // Crear usuarios
            Usuario usuario1 = new Usuario("juanperez", "contrasena123");
            Usuario usuario2 = new Usuario("mariagonzalez", "seguro456");
            Usuario usuario3 = new Usuario("carloslopez", "clave789");
            Usuario usuario4 = new Usuario("anaruiz", "pass321");
            Usuario usuario5 = new Usuario("pedrosanchez", "admin111");
            Usuario usuario6 = new Usuario("lauragomez", "mecanica222");
            Usuario usuario7 = new Usuario("josemartin", "mecanico333");
            Usuario usuario8 = new Usuario("sofiatorres", "mecanica444");
            Usuario usuario9 = new Usuario("diegofernandez", "recepcion555");
            Usuario usuario10 = new Usuario("elenamoreno", "contadora666");

            em.persist(usuario1);
            em.persist(usuario2);
            em.persist(usuario3);
            em.persist(usuario4);
            em.persist(usuario5);
            em.persist(usuario6);
            em.persist(usuario7);
            em.persist(usuario8);
            em.persist(usuario9);
            em.persist(usuario10);

            // Crear empleados
            Empleado empleado1 = new Empleado("Juan Pérez", "Mecánico", 2500.00, usuario1);
            Empleado empleado2 = new Empleado("María González", "Recepcionista", 1800.00, usuario2);
            Empleado empleado3 = new Empleado("Carlos López", "Mecánico", 2600.00, usuario3);
            Empleado empleado4 = new Empleado("Ana Ruiz", "Vendedor", 2200.00, usuario4);
            Empleado empleado5 = new Empleado("Pedro Sánchez", "Jefe de Taller", 3500.00, usuario5);
            Empleado empleado6 = new Empleado("Laura Gómez", "Mecánico", 2400.00, usuario6);
            Empleado empleado7 = new Empleado("José Martín", "Mecánico", 2300.00, usuario7);
            Empleado empleado8 = new Empleado("Sofía Torres", "Mecánico", 2500.00, usuario8);
            Empleado empleado9 = new Empleado("Diego Fernández", "Vendedor", 1900.00, usuario9);
            Empleado empleado10 = new Empleado("Elena Moreno", "Vendedor", 2800.00, usuario10);

            em.persist(empleado1);
            em.persist(empleado2);
            em.persist(empleado3);
            em.persist(empleado4);
            em.persist(empleado5);
            em.persist(empleado6);
            em.persist(empleado7);
            em.persist(empleado8);
            em.persist(empleado9);
            em.persist(empleado10);

            // Crear clientes
            Cliente cliente1 = new Cliente("Lucía Pérez", "lucia@example.com", "123456789");
            Cliente cliente2 = new Cliente("Martín Gómez", "martin@example.com", "987654321");
            Cliente cliente3 = new Cliente("Andrea López", "andrea@example.com", "654321987");
            Cliente cliente4 = new Cliente("Roberto Torres", "roberto@example.com", "321987654");
            Cliente cliente5 = new Cliente("Camila Fernández", "camila@example.com", "456789123");
            em.persist(cliente1);
            em.persist(cliente2);
            em.persist(cliente3);
            em.persist(cliente4);
            em.persist(cliente5);

            // Crear coches
            Coche coche1 = new Coche("Toyota", "Corolla", 2020, 15000.00);
            Coche coche2 = new Coche("Ford", "Focus", 2018, 12000.00);
            Coche coche3 = new Coche("Honda", "Civic", 2019, 14000.00);
            Coche coche4 = new Coche("Chevrolet", "Spark", 2021, 11000.00);
            Coche coche5 = new Coche("Nissan", "Sentra", 2022, 16000.00);
            em.persist(coche1);
            em.persist(coche2);
            em.persist(coche3);
            em.persist(coche4);
            em.persist(coche5);

            // Relacionar clientes y coches
            cliente1.getCoches().add(coche1);
            cliente2.getCoches().add(coche2);
            cliente3.getCoches().add(coche3);
            cliente4.getCoches().add(coche4);
            cliente5.getCoches().add(coche5);
            coche1.getClientes().add(cliente1);
            coche2.getClientes().add(cliente2);
            coche3.getClientes().add(cliente3);
            coche4.getClientes().add(cliente4);
            coche5.getClientes().add(cliente5);
            em.persist(cliente1);
            em.persist(cliente2);
            em.persist(cliente3);
            em.persist(cliente4);
            em.persist(cliente5);

            // Crear reparaciones
            Reparacion reparacion1 = new Reparacion("Cambio de aceite", LocalDate.of(2024, 1, 15), 50.00, coche1, empleado1);
            Reparacion reparacion2 = new Reparacion("Cambio de neumáticos", LocalDate.of(2024, 2, 10), 200.00, coche2, empleado3);
            Reparacion reparacion3 = new Reparacion("Alineación de dirección", LocalDate.of(2024, 3, 5), 100.00, coche3, empleado6);
            Reparacion reparacion4 = new Reparacion("Revisión general", LocalDate.of(2024, 4, 20), 150.00, coche4, empleado7);
            Reparacion reparacion5 = new Reparacion("Cambio de frenos", LocalDate.of(2024, 5, 25), 250.00, coche5, empleado8);
            Reparacion reparacion6 = new Reparacion("Cambio de batería", LocalDate.of(2024, 6, 30), 120.00, coche1, empleado1);
            Reparacion reparacion7 = new Reparacion("Reparación de motor", LocalDate.of(2024, 7, 15), 500.00, coche2, empleado3);
            Reparacion reparacion8 = new Reparacion("Cambio de filtro de aire", LocalDate.of(2024, 8, 10), 80.00, coche3, empleado6);
            Reparacion reparacion9 = new Reparacion("Revisión de suspensión", LocalDate.of(2024, 9, 5), 180.00, coche4, empleado7);
            Reparacion reparacion10 = new Reparacion("Limpieza de inyectores", LocalDate.of(2024, 10, 20), 130.00, coche5, empleado5); // Jefe de Taller

            em.persist(reparacion1);
            em.persist(reparacion2);
            em.persist(reparacion3);
            em.persist(reparacion4);
            em.persist(reparacion5);
            em.persist(reparacion6);
            em.persist(reparacion7);
            em.persist(reparacion8);
            em.persist(reparacion9);
            em.persist(reparacion10);

            // Crear ventas
            Venta venta1 = new Venta(LocalDate.of(2024, 3, 1), 15000.00, empleado4, cliente1);
            Venta venta2 = new Venta(LocalDate.of(2024, 6, 15), 12000.00, empleado9, cliente2);
            Venta venta3 = new Venta(LocalDate.of(2024, 9, 30), 14000.00, empleado10, cliente3);
            Venta venta4 = new Venta(LocalDate.of(2024, 12, 15), 20000.00, empleado5, cliente4); // Jefe de Taller
            em.persist(venta1);
            em.persist(venta2);
            em.persist(venta3);
            em.persist(venta4);

            // Relacionar ventas y coches (Venta_Coche)
            venta1.getCoches().add(coche1);
            venta2.getCoches().add(coche2);
            venta3.getCoches().add(coche3);
            coche1.getVentas().add(venta1);
            coche2.getVentas().add(venta2);
            coche3.getVentas().add(venta3);
            coche4.getVentas().add(venta4);

            em.persist(venta1);
            em.persist(venta2);
            em.persist(venta3);
            em.persist(venta4);

            // Confirmar transacción
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
