package org.main;

import org.model.Cliente;
import org.model.Coche;
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

                            // Actualizar Usuario
                            case "4":
                                System.out.println("Introduce el ID del usuario a actualizar: ");
                                int idActualizar = sc.nextInt();

                                Usuario usuarioActualizar = em.find(Usuario.class, idActualizar);

                                if (usuarioActualizar != null){
                                    System.out.println("Introduce el nuevo username: ");
                                    String usernameNuevo = sc.nextLine();

                                    System.out.println("Introduce la nueva contraseña");
                                    String contrasenaNueva = sc.nextLine();

                                    usuarioActualizar.setUsername(usernameNuevo);
                                    usuarioActualizar.setPassword(contrasenaNueva);

                                    try {
                                        em.getTransaction().begin();
                                        em.persist(usuarioActualizar);
                                        em.getTransaction().commit();
                                        System.out.println("Usuario actualizado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }

                                } else {
                                    System.err.println("\n Usuario no encontrado.");
                                }
                                break;

                            // Eliminar un usuario
                            case "5":
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

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("6"));
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
                                sc.nextLine();

                                Usuario usuarioEncontrado = em.find(Usuario.class, usuarioId);
                                if (usuarioEncontrado != null) {
                                    Empleado empleado = new Empleado(nombre, puesto, salario, usuarioEncontrado);
                                    usuarioEncontrado.setEmpleado(empleado);

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

                            // Actualizar Empleado
                            case "4":
                                System.out.println("Introduce el ID del empleado a actualizar: ");
                                int idActualizar = sc.nextInt();

                                Empleado empleadoActualizar = em.find(Empleado.class, idActualizar);

                                if (empleadoActualizar != null){
                                    System.out.println("Introduce el nuevo nombre: ");
                                    String nombreNuevo = sc.nextLine();
                                    System.out.println("Introduce el nuevo puesto:");
                                    String puestoNuevo = sc.nextLine();
                                    System.out.println("Introduce el nuevo salario:");
                                    double salarioNuevo = sc.nextDouble();

                                    empleadoActualizar.setNombre(nombreNuevo);
                                    empleadoActualizar.setPuesto(puestoNuevo);
                                    empleadoActualizar.setSalario(salarioNuevo);

                                    try {
                                        em.getTransaction().begin();
                                        em.persist(empleadoActualizar);
                                        em.getTransaction().commit();
                                        System.out.println("Empleado actualizado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }

                                } else {
                                    System.err.println("\n Empleado no encontrado.");
                                }
                                break;

                            // Eliminar un empleado
                            case "5":
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

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("5"));
                    break;

                // Cliente
                case "3":
                    do {
                        mostrarMenuOperaciones("Cliente");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Cliente
                            case "1":
                                System.out.println("Introduce el nombre del cliente");
                                String nombre = sc.nextLine();

                                // Validación de email
                                String email;
                                do {
                                    System.out.println("Introduce el email del cliente:");
                                    email = sc.nextLine();
                                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                                        System.out.println("El email introducido no es válido. Intente nuevamente.");
                                    }
                                } while (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));

                                // Validación de teléfono
                                String telefono;
                                do {
                                    System.out.println("Introduce el teléfono del cliente:");
                                    telefono = sc.nextLine();
                                    if (!telefono.matches("^\\d{1,15}$")) {
                                        System.out.println("El teléfono introducido no es válido. Intente nuevamente.");
                                    }
                                } while (!telefono.matches("^\\d{1,15}$"));

                                Cliente cliente = new Cliente(nombre, email, telefono);

                                try {
                                    em.getTransaction().begin();
                                    em.persist(cliente);
                                    em.getTransaction().commit();
                                    System.out.println("Cliente creado con éxito");
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al persistir la entidad: " + e.getMessage());
                                }
                                break;

                            // Buscar Cliente por ID
                            case "2":
                                System.out.println("Introduce el id del cliente");
                                int idCliente = sc.nextInt();
                                sc.nextLine();

                                Cliente clienteEncontrado = em.find(Cliente.class, idCliente);
                                if (clienteEncontrado != null) {
                                    System.out.println(clienteEncontrado);
                                } else {
                                    System.err.println("\nCliente no encontrado.");
                                }
                                break;

                            // Listar todos los Clientes
                            case "3":
                                List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
                                System.out.println(clientes);
                                break;

                            // Actualizar Cliente
                            case "4":
                                System.out.println("Introduce el ID del cliente a actualizar: ");
                                int idActualizar = sc.nextInt();

                                Cliente clienteActualizar = em.find(Cliente.class, idActualizar);

                                if (clienteActualizar != null){
                                    System.out.println("Introduce el nuevo nombre: ");
                                    String nombreNuevo = sc.nextLine();

                                    // Validación de email
                                    String emailNuevo;
                                    do {
                                        System.out.println("Introduce el nuevo email del cliente:");
                                        emailNuevo = sc.nextLine();
                                        if (!emailNuevo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                                            System.out.println("El email introducido no es válido. Intente nuevamente.");
                                        }
                                    } while (!emailNuevo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));

                                    // Validación de teléfono
                                    String telefonoNuevo;
                                    do {
                                        System.out.println("Introduce el nuevo teléfono del cliente:");
                                        telefonoNuevo = sc.nextLine();
                                        if (!telefonoNuevo.matches("^\\d{1,15}$")) {
                                            System.out.println("El teléfono introducido no es válido. Intente nuevamente.");
                                        }
                                    } while (!telefonoNuevo.matches("^\\d{1,15}$"));

                                    clienteActualizar.setNombre(nombreNuevo);
                                    clienteActualizar.setEmail(emailNuevo);
                                    clienteActualizar.setTelefono(telefonoNuevo);

                                    try {
                                        em.getTransaction().begin();
                                        em.persist(clienteActualizar);
                                        em.getTransaction().commit();
                                        System.out.println("Cliente actualizado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }

                                } else {
                                    System.err.println("\n Cliente no encontrado.");
                                }
                                break;

                            // Eliminar un Cliente
                            case "5":
                                System.out.println("Introduce el ID del cliente a eliminar:");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();

                                try {
                                    em.getTransaction().begin();
                                    Cliente clienteEliminar = em.find(Cliente.class, idEliminar);
                                    if (clienteEliminar != null) {
                                        em.remove(clienteEliminar);
                                        em.getTransaction().commit();
                                        System.out.println("\nCliente eliminado con éxito");
                                    } else {
                                        System.err.println("\nCliente no encontrado.");
                                        em.getTransaction().rollback();
                                    }
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("6"));
                    break;

                // Coche
                case "4":
                    do {
                        mostrarMenuOperaciones("Coche");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Coche
                            case "1":
                                System.out.println("Introduce la marca del coche");
                                String marca = sc.nextLine();
                                System.out.println("Introduce el modelo del coche");
                                String modelo = sc.nextLine();
                                System.out.println("Introduce el año del coche");
                                int anio = sc.nextInt();
                                System.out.println("Introduce el precio del coche");
                                double precio = sc.nextDouble();
                                sc.nextLine();

                                System.out.println("Introduce el ID del cliente");
                                int idCliente = sc.nextInt();

                                Cliente clienteEncontrado = em.find(Cliente.class, idCliente);

                                if (clienteEncontrado != null) {
                                    Coche coche = new Coche(marca, modelo, anio, precio);
                                    clienteEncontrado.getCoches().add(coche);
                                    try {
                                        em.getTransaction().begin();
                                        em.persist(coche);
                                        em.persist(clienteEncontrado);
                                        em.getTransaction().commit();
                                        System.out.println("Coche creado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }
                                    break;

                                } else {
                                    System.err.println("\nCliente no encontrado.");

                                }
                                break;

                            // Buscar Coche por ID
                            case "2":
                                System.out.println("Introduce el id del coche");
                                int idCoche = sc.nextInt();
                                sc.nextLine();

                                Coche cocheEncontrado = em.find(Coche.class, idCoche);
                                if (cocheEncontrado != null) {
                                    System.out.println(cocheEncontrado);
                                } else {
                                    System.err.println("\nCoche no encontrado.");
                                }
                                break;

                            // Listar todos los Coches
                            case "3":
                                List<Coche> coches = em.createQuery("SELECT c FROM Coche c", Coche.class).getResultList();
                                System.out.println(coches);
                                break;

                            case "4":
                                System.out.println("Introduce el ID del coche a actualizar: ");
                                int idActualizar = sc.nextInt();

                                Coche cocheActualizar = em.find(Coche.class, idActualizar);

                                if (cocheActualizar != null){
                                    System.out.println("Introduce la nueva marca: ");
                                    String marcaNuevo = sc.nextLine();
                                    System.out.println("Introduce el nuevo modelo: ");
                                    String modeloNuevo = sc.nextLine();
                                    System.out.println("Introdue el nuevo año: ");
                                    int anioNuevo = sc.nextInt();
                                    System.out.println("Introduce el nuevo precio: ");
                                    double precioNuevo = sc.nextDouble();

                                    cocheActualizar.setMarca(marcaNuevo);
                                    cocheActualizar.setModelo(modeloNuevo);
                                    cocheActualizar.setAnio(anioNuevo);
                                    cocheActualizar.setPrecio(precioNuevo);

                                    try {
                                        em.getTransaction().begin();
                                        em.persist(cocheActualizar);
                                        em.getTransaction().commit();
                                        System.out.println("Coche actualizado con éxito");
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                    }

                                } else {
                                    System.err.println("\n Coche no encontrado.");
                                }
                                break;

                            // Eliminar un empleado
                            case "5":
                                System.out.println("Introduce el ID del coche a eliminar:");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();

                                try {
                                    em.getTransaction().begin();
                                    Coche cocheEliminar = em.find(Coche.class, idEliminar);
                                    if (cocheEliminar != null) {
                                        em.remove(cocheEliminar);
                                        em.getTransaction().commit();
                                        System.out.println("\nCoche eliminado con éxito");
                                    } else {
                                        System.err.println("\nCoche no encontrado.");
                                        em.getTransaction().rollback();
                                    }
                                } catch (Exception e) {
                                    em.getTransaction().rollback();
                                    System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("6"));
                    break;

                // Reparacion
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
                    break;
                default:
                    System.out.println("Ingrese una opción válida.");
                    break;
            }
        } while (!opcion.equals("7"));

        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("""
                \n
                ╔═════════════════════════════════════════════════════╗
                ║             GESTIÓN DE TALLER MECÁNICO              ║
                ╠═════════════════════════════════════════════════════╣
                ║ Seleccione una opción de las siguientes:            ║
                ║  1. Usuario.                                        ║
                ║  2. Empleado.                                       ║
                ║  3. Cliente.                                        ║
                ║  4. Coche.                                          ║
                ║  5. Reparación.                                     ║
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
                ║  1. Crear un registro                                ║
                ║  2. Buscar por id                                    ║
                ║  3. Mostrar todos los registros                      ║
                ║   5. Actualizar un registro                          ║
                ║  4. Eliminar un registro                             ║
                ║  5. Salir al menú principal                          ║
                ╚══════════════════════════════════════════════════════╝
                """, entidad.toUpperCase()));

    }

}
