package org.main;

import org.model.Empleado;
import org.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();

        Scanner sc = new Scanner(System.in);
        String opcion;

        do {
            mostrarMenuPrincipal();

            opcion = sc.nextLine();

            switch (opcion) {
                // Cliente
                case "1":
                    do {
                        mostrarMenuOperaciones("Usuario");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Usuario
                            case "1":
                                System.out.println("Introduce el username del usuario");
                                String nombre = sc.nextLine();
                                System.out.println("Introduce la contraseña del usuario");
                                String contrasena = sc.nextLine();

                                Usuario usuario = new Usuario(nombre, contrasena);

                                try {
                                    em.getTransaction().begin();
                                    em.persist(usuario);
                                    em.getTransaction().commit();
                                    System.out.println("Usuario creado con éxito");
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al persistir la entidad: " + e.getMessage());
                                }
                                break;
                            // Buscar Usuario por ID
                            case "2":
                                System.out.println("Introduce el id del usuario");
                                int idUsuario = sc.nextInt();
                                sc.nextLine();

                                Usuario usuarioEncontrado = em.find(Usuario.class, idUsuario);
                                if (usuarioEncontrado != null) {
                                    System.out.println(usuarioEncontrado);
                                } else {
                                    System.err.println("\nUsuario no encontrado.");
                                }
                                break;

                            // Listar todos los Usuarios
                            case "3":
                                List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
                                System.out.println(usuarios);
                                break;

                            // Eliminar un usuario
                            case "4":
                                System.out.println("Introduce el ID del usuario a eliminar:");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();

                                try {
                                    em.getTransaction().begin();
                                    Usuario usuarioEliminar = em.find(Usuario.class, idEliminar);
                                    if (usuarioEliminar != null) {
                                        em.remove(usuarioEliminar);
                                        em.getTransaction().commit();
                                        System.out.println("Usuario eliminado con éxito");
                                    } else {
                                        System.err.println("\nUsuario no encontrado.");
                                        em.getTransaction().rollback();
                                    }
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                }
                                break;

                            case "5":
                                System.out.println("Saliendo al menu principal...");
                                return;
                        }

                    } while (!opcion.equals("5"));
                    break;

                // Empleado
                case "2":
                    do {
                        mostrarMenuOperaciones("Empleado");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Empleado
                            case "1":
                                System.out.println("Introduce el nombre del empleado");
                                String nombre = sc.nextLine();
                                System.out.println("Introduce el puesto del empleado");
                                String puesto = sc.nextLine();
                                System.out.println("Introduce el salario del empleado");
                                double salario = sc.nextDouble();
                                System.out.println("Introduce el ID del username del empleado");
                                int usuarioId = sc.nextInt();

                                Usuario usuarioEncontrado = em.find(Usuario.class, usuarioId);
                                if (usuarioEncontrado != null) {
                                    Empleado empleado = new Empleado(nombre, puesto, salario, usuarioEncontrado);
                                    usuarioEncontrado.setEmpleado(empleado); // COMO ASIGNO EL EMPLEADO AL USUARIO EN LA BBDD?

                                    try {
                                        em.getTransaction().begin();
                                        em.persist(empleado);
                                        em.getTransaction().commit();
                                        System.out.println("Empleado creado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }
                                    break;

                                } else {
                                    System.err.println("\nUsuario no encontrado.");
                                }
                                break;

                                // Buscar Empleado por ID
                            case "2":
                                System.out.println("Introduce el id del empleado");
                                int idEmpleado = sc.nextInt();
                                sc.nextLine();

                                Empleado empleadoEncontrado = em.find(Empleado.class, idEmpleado);
                                if (empleadoEncontrado != null) {
                                    System.out.println(empleadoEncontrado);
                                } else {
                                    System.err.println("\nEmpleado no encontrado.");
                                }
                                break;

                            // Listar todos los Empleados
                            case "3":
                                List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
                                System.out.println(empleados);
                                break;

                            // Eliminar un empleado
                            case "4":
                                System.out.println("Introduce el ID del empleado a eliminar:");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();

                                try {
                                    em.getTransaction().begin();
                                    Empleado empleadoEliminar = em.find(Empleado.class, idEliminar);
                                    if (empleadoEliminar != null) {
                                        em.remove(empleadoEliminar);
                                        em.getTransaction().commit();
                                        System.out.println("\nEmpleado eliminado con éxito");
                                    } else {
                                        System.err.println("\nEmpleado no encontrado.");
                                        em.getTransaction().rollback();
                                    }
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                }
                                break;

                            case "5":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("5"));
                    break;

                // Cliente
                case "3":
                    mostrarMenuOperaciones("Cliente");
                    opcion = sc.nextLine();
                    switch (opcion) {
                        case "1":
                            break;

                        case "2":
                            break;

                        case "3":
                            break;

                        case "4":
                            break;
                    }
                    break;

                // Reparación
                case "4":
                    mostrarMenuOperaciones("Reparación");
                    opcion = sc.nextLine();
                    switch (opcion) {
                        case "1":
                            break;

                        case "2":
                            break;

                        case "3":
                            break;

                        case "4":
                            break;
                    }
                    break;

                // Usuario
                case "5":
                    mostrarMenuOperaciones("Usuario");
                    opcion = sc.nextLine();
                    switch (opcion) {
                        case "1":
                            break;

                        case "2":
                            break;

                        case "3":
                            break;

                        case "4":
                            break;
                    }
                    break;
                // Venta
                case "6":
                    mostrarMenuOperaciones("Venta");
                    opcion = sc.nextLine();
                    switch (opcion) {
                        case "1":
                            break;

                        case "2":
                            break;

                        case "3":
                            break;

                        case "4":
                            break;
                    }
                    break;

                // Salir
                case "7":
                    System.out.println("Saliendo del programa...");
                    opcion = sc.nextLine();
                    switch (opcion) {
                        case "1":
                            break;

                        case "2":
                            break;

                        case "3":
                            break;

                        case "4":
                            break;
                    }
                    break;

                default:
                    System.out.println("Ingrese una opción válida.");
            }
        } while (opcion.equals("7"));

        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("""
                \n
                ╔═════════════════════════════════════════════════════╗
                ║             GESTIÓN DE TALLER MECÁNICO              ║
                ╠═════════════════════════════════════════════════════╣
                ║ Seleccione una opción de las siguientes:            ║
                ║                                                     ║
                ║  1 Usuario.                                         ║
                ║  2 Empleado.                                        ║
                ║  3 Cliente.                                         ║
                ║  4 Reparacion.                                      ║
                ║  5 Usuario.                                         ║
                ║  6 Venta                                            ║
                ║  7 Salir                                            ║
                ╚═════════════════════════════════════════════════════╝
                """);
    }

    private static void mostrarMenuOperaciones(String entidad) {
        System.out.println(String.format("""
                \n
                                       %s
                ╔══════════════════════════════════════════════════════╗
                ║ Seleccione una opción de las siguientes:             ║
                ║       1. Crear un registro                           ║
                ║       2. Buscar por id                               ║
                ║       3. Mostrar todos los registros                 ║
                ║       4. Eliminar un registro                        ║
                ║       5. Salir                                       ║
                ╚══════════════════════════════════════════════════════╝
                """, entidad.toUpperCase()));

    }

}
