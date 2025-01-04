package org.main;

import org.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
                                System.out.print("Introduce el username del usuario: ");
                                String nombre = sc.nextLine();
                                System.out.print("Introduce la contraseña del usuario: ");
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
                                try {
                                    System.out.print("Introduce el id del usuario: ");
                                    int idUsuario = sc.nextInt();
                                    sc.nextLine();

                                    Usuario usuarioEncontrado = em.find(Usuario.class, idUsuario);
                                    if (usuarioEncontrado != null) {
                                        System.out.println(usuarioEncontrado);
                                    } else {
                                        System.err.println("\nUsuario no encontrado.");
                                    }
                                } catch (InputMismatchException e ){
                                    System.err.println("Error el la introducción de datos");
                                }
                                break;

                            // Listar todos los Usuarios
                            case "3":
                                List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
                                System.out.println(usuarios);
                                break;

                            // Actualizar Usuario
                            case "4":
                                try {
                                    System.out.print("Introduce el ID del usuario a actualizar: ");
                                    int idActualizar = sc.nextInt();

                                    Usuario usuarioActualizar = em.find(Usuario.class, idActualizar);

                                    if (usuarioActualizar != null){
                                        System.out.print("Introduce el nuevo username: ");
                                        String usernameNuevo = sc.nextLine();

                                        System.out.print("Introduce la nueva contraseña: ");
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
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Eliminar un usuario
                            case "5":
                                try {
                                    System.out.print("Introduce el ID del usuario a eliminar: ");
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

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
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
                                try{
                                    System.out.print("Introduce el nombre del empleado: ");
                                    String nombre = sc.nextLine();
                                    System.out.print("Introduce el puesto del empleado: ");
                                    String puesto = sc.nextLine();
                                    System.out.print("Introduce el salario del empleado: ");
                                    double salario = sc.nextDouble();
                                    System.out.print("Introduce el ID del username del empleado: ");
                                    int usuarioId = sc.nextInt();
                                    sc.nextLine();

                                    Usuario usuarioEncontrado = em.find(Usuario.class, usuarioId);
                                    if (usuarioEncontrado != null) {
                                        Empleado empleado = new Empleado(nombre, puesto, salario, usuarioEncontrado);
                                        usuarioEncontrado.setEmpleado(empleado);

                                        try {
                                            em.getTransaction().begin();
                                            em.persist(empleado);
                                            em.persist(usuarioEncontrado);
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

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                                // Buscar Empleado por ID
                            case "2":
                                try{
                                    System.out.print("Introduce el id del empleado: ");
                                    int idEmpleado = sc.nextInt();
                                    sc.nextLine();

                                    Empleado empleadoEncontrado = em.find(Empleado.class, idEmpleado);
                                    if (empleadoEncontrado != null) {
                                        System.out.println(empleadoEncontrado);
                                    } else {
                                        System.err.println("\nEmpleado no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Listar todos los Empleados
                            case "3":
                                List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
                                System.out.println(empleados);
                                break;

                            // Actualizar Empleado
                            case "4":
                                try {
                                    System.out.print("Introduce el ID del empleado a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Empleado empleadoActualizar = em.find(Empleado.class, idActualizar);

                                    if (empleadoActualizar != null){
                                        System.out.print("Introduce el nuevo nombre: ");
                                        String nombreNuevo = sc.nextLine();
                                        System.out.print("Introduce el nuevo puesto: ");
                                        String puestoNuevo = sc.nextLine();
                                        System.out.print("Introduce el nuevo salario: ");
                                        double salarioNuevo = sc.nextDouble();

                                        System.out.print("Introduce el ID del nuevo usuario: ");
                                        int idUsuario = sc.nextInt();
                                        sc.nextLine();

                                        Usuario usuarioViejo = empleadoActualizar.getUsuario();
                                        Usuario usuarioActualizar = em.find(Usuario.class, idUsuario);

                                        if(usuarioActualizar != null){
                                            usuarioViejo.setEmpleado(null);

                                            empleadoActualizar.setNombre(nombreNuevo);
                                            empleadoActualizar.setPuesto(puestoNuevo);
                                            empleadoActualizar.setSalario(salarioNuevo);
                                            empleadoActualizar.setUsuario(usuarioActualizar);

                                            usuarioActualizar.setEmpleado(empleadoActualizar);

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
                                            System.out.println("No se ha encontrado el usuario.");
                                        }

                                    } else {
                                        System.err.println("\n Empleado no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Eliminar un empleado
                            case "5":
                                try {
                                    System.out.print("Introduce el ID del empleado a eliminar: ");
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

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
                                break;
                        }

                    } while (!opcion.equals("6"));
                    break;

                // Cliente
                case "3":
                    do {
                        mostrarMenuOperaciones("Cliente");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Cliente
                            case "1":
                                System.out.print("Introduce el nombre del cliente: ");
                                String nombre = sc.nextLine();

                                // Validación de email
                                String email;
                                do {
                                    System.out.print("Introduce el email del cliente: ");
                                    email = sc.nextLine();
                                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                                        System.out.println("El email introducido no es válido. Intentelo de nuevo.");
                                    }
                                } while (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));

                                // Validación de teléfono
                                String telefono;
                                do {
                                    System.out.print("Introduce el teléfono del cliente: ");
                                    telefono = sc.nextLine();
                                    if (!telefono.matches("^\\d{1,15}$")) {
                                        System.out.println("El teléfono introducido no es válido. Intentelo de nuevo.");
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
                                try{
                                    System.out.print("Introduce el id del cliente: ");
                                    int idCliente = sc.nextInt();
                                    sc.nextLine();

                                    Cliente clienteEncontrado = em.find(Cliente.class, idCliente);
                                    if (clienteEncontrado != null) {
                                        System.out.println(clienteEncontrado);
                                    } else {
                                        System.err.println("\nCliente no encontrado.");
                                    }

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Listar todos los Clientes
                            case "3":
                                List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
                                System.out.println(clientes);
                                break;

                            // Actualizar Cliente
                            case "4":
                                try{
                                    System.out.print("Introduce el ID del cliente a actualizar: ");
                                    int idActualizar = sc.nextInt();

                                    Cliente clienteActualizar = em.find(Cliente.class, idActualizar);

                                    if (clienteActualizar != null){
                                        System.out.print("Introduce el nuevo nombre: ");
                                        String nombreNuevo = sc.nextLine();

                                        // Validación de email
                                        String emailNuevo;
                                        do {
                                            System.out.print("Introduce el nuevo email del cliente: ");
                                            emailNuevo = sc.nextLine();
                                            if (!emailNuevo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                                                System.out.println("El email introducido no es válido. Intente nuevamente.");
                                            }
                                        } while (!emailNuevo.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"));

                                        // Validación de teléfono
                                        String telefonoNuevo;
                                        do {
                                            System.out.print("Introduce el nuevo teléfono del cliente: ");
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

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Eliminar un Cliente
                            case "5":
                                try{
                                    System.out.print("Introduce el ID del cliente a eliminar: ");
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

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
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
                                try{
                                    System.out.print("Introduce la marca del coche: ");
                                    String marca = sc.nextLine();
                                    System.out.print("Introduce el modelo del coche: ");
                                    String modelo = sc.nextLine();
                                    System.out.print("Introduce el año del coche: ");
                                    int anio = sc.nextInt();
                                    System.out.print("Introduce el precio del coche: ");
                                    double precio = sc.nextDouble();
                                    sc.nextLine();

                                    System.out.print("Introduce el ID del cliente: ");
                                    int idCliente = sc.nextInt();

                                    Cliente clienteEncontrado = em.find(Cliente.class, idCliente);

                                    if (clienteEncontrado != null) {
                                        Coche coche = new Coche(marca, modelo, anio, precio);
                                        clienteEncontrado.getCoches().add(coche);
                                        coche.getClientes().add(clienteEncontrado);

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
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Buscar Coche por ID
                            case "2":
                                try{
                                    System.out.print("Introduce el id del coche: ");
                                    int idCoche = sc.nextInt();
                                    sc.nextLine();

                                    Coche cocheEncontrado = em.find(Coche.class, idCoche);
                                    if (cocheEncontrado != null) {
                                        System.out.println(cocheEncontrado);
                                    } else {
                                        System.err.println("\nCoche no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Listar todos los Coches
                            case "3":
                                List<Coche> coches = em.createQuery("SELECT c FROM Coche c", Coche.class).getResultList();
                                System.out.println(coches);
                                break;

                            case "4":
                                try {
                                    System.out.print("Introduce el ID del coche a actualizar: ");
                                    int idActualizar = sc.nextInt();

                                    Coche cocheActualizar = em.find(Coche.class, idActualizar);

                                    if (cocheActualizar != null){
                                        System.out.print("Introduce la nueva marca: ");
                                        String marcaNuevo = sc.nextLine();
                                        System.out.print("Introduce el nuevo modelo: ");
                                        String modeloNuevo = sc.nextLine();
                                        System.out.print("Introdue el nuevo año: ");
                                        int anioNuevo = sc.nextInt();
                                        System.out.print("Introduce el nuevo precio: ");
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
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            // Eliminar un coche
                            case "5":
                                try{
                                    System.out.print("Introduce el ID del coche a eliminar: ");
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
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
                                break;
                        }

                    } while (!opcion.equals("6"));
                    break;

                // Reparacion
                case "5":
                    do {
                        mostrarMenuOperaciones("Reparacion");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Reparacion
                            case "1":
                                System.out.print("Introduce una breve descripción: ");
                                String descripcion = sc.nextLine();
                                try {
                                    System.out.print("Introduce la fecha de la reparación (aaaa-mm-dd): ");
                                    LocalDate fechaReparacion = LocalDate.parse(sc.nextLine());
                                    System.out.print("Introduce el costo de la reparación: ");
                                    double costo = sc.nextDouble();
                                    sc.nextLine();

                                    // Buscar coche reparado
                                    System.out.print("Introduce el ID del coche reparado: ");
                                    int idCoche = sc.nextInt();
                                    Coche cocheReparado = em.find(Coche.class, idCoche);

                                    // Buscar empleado encargado de la reparacion
                                    System.out.println("Introduce el ID del empleado encargado de la reparación: ");
                                    int idEmpleado = sc.nextInt();
                                    Empleado empleadoReparacion = em.find(Empleado.class, idEmpleado);

                                    if (cocheReparado != null && empleadoReparacion != null) {
                                        if (empleadoReparacion.getPuesto().equals("Mecánico")) {
                                            Reparacion reparacion = new Reparacion(descripcion, fechaReparacion, costo);

                                            cocheReparado.getReparaciones().add(reparacion);
                                            reparacion.setCoche(cocheReparado);

                                            empleadoReparacion.getReparaciones().add(reparacion);
                                            reparacion.setEmpleado(empleadoReparacion);

                                            try {
                                                em.getTransaction().begin();
                                                em.persist(reparacion);
                                                em.persist(cocheReparado);
                                                em.persist(empleadoReparacion);
                                                em.getTransaction().commit();
                                                System.out.println("Reparación creada con éxito");
                                            } catch (Exception e) {
                                                em.getTransaction().rollback();
                                                System.err.println("Error al persistir la entidad: " + e.getMessage());
                                            }
                                        } else {
                                            System.err.println("El empleado no es mecánico. No puede hacer una reparación.");
                                        }
                                    } else {
                                        System.err.println("\nCoche o empleado no encontrado.");
                                    }
                                } catch (DateTimeParseException e ){
                                    System.err.println("Formato de fecha no válido");
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos"); // si se introducen strings cuando se espera números
                                }
                                break;

                            // Buscar la Reparación por ID
                            case "2":
                                System.out.print("Introduce el ID de la reparación: ");
                                int idReparacion = sc.nextInt();
                                sc.nextLine();

                                Reparacion reparacionEncontrada = em.find(Reparacion.class, idReparacion);
                                if (reparacionEncontrada != null) {
                                    System.out.println(reparacionEncontrada);
                                } else {
                                    System.err.println("\nReparacion no encontrada.");
                                }
                                break;

                            // Listar todas las Reparaciones
                            case "3":
                                List<Reparacion> reparaciones = em.createQuery("SELECT r FROM Reparacion r", Reparacion.class).getResultList();
                                System.out.println(reparaciones);
                                break;

                            // Actualizar una Reparacion
                            case "4":
                                try {
                                    System.out.print("Introduce el ID de la reparación a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Reparacion reparacionActualizar = em.find(Reparacion.class, idActualizar);

                                    if (reparacionActualizar != null) {
                                        System.out.print("Introduce la nueva descripcion: ");
                                        String descripcionNueva = sc.nextLine();

                                        System.out.print("Introduce la nueva fecha (aaaa-mm-dd): ");
                                        LocalDate fechaNueva = LocalDate.parse(sc.nextLine());
                                        System.out.print("Introduce el nuevo costo: ");
                                        double costoNuevo = sc.nextDouble();

                                        // Buscar el nuevo empleado y eliminar el viejo
                                        System.out.print("Introduce el ID del nuevo empleado: ");
                                        int idEmpleado = sc.nextInt();

                                        Empleado empleadoActualizar = em.find(Empleado.class, idEmpleado); //nuevo
                                        Empleado empleadoViejo = reparacionActualizar.getEmpleado(); //viejo

                                        if (empleadoActualizar != null) {
                                            // Elimina la reparacion de la lista de reparaciones del empleado viejo
                                            empleadoViejo.getReparaciones().remove(reparacionActualizar);
                                            // Actualiza el empleado de la reparación
                                            reparacionActualizar.setEmpleado(empleadoActualizar);
                                            empleadoActualizar.getReparaciones().add(reparacionActualizar);

                                        } else {
                                            System.err.println("No se ha encontrado el empleado. Se ha dejado el empleado anterior");
                                        }

                                        // Buscar el nuevo coche
                                        System.out.print("Introduce el ID del nuevo coche: ");
                                        int idCoche = sc.nextInt();

                                        Coche cocheActualizar = em.find(Coche.class, idCoche); //nuevo
                                        Coche cocheViejo = reparacionActualizar.getCoche(); //anterior

                                        if (cocheActualizar != null) {
                                            // Elimina la reparacion de la lista de reparaciones del coche anterior
                                            cocheViejo.getReparaciones().remove(reparacionActualizar);

                                            reparacionActualizar.setCoche(cocheActualizar);
                                            cocheActualizar.getReparaciones().add(reparacionActualizar);

                                        } else {
                                            System.err.println("No se ha encontrado el coche. Se ha dejado el coche anterior");
                                        }

                                        reparacionActualizar.setDescripcion(descripcionNueva);
                                        reparacionActualizar.setFecha(fechaNueva);
                                        reparacionActualizar.setCosto(costoNuevo);

                                        try {
                                            em.getTransaction().begin();
                                            em.persist(cocheActualizar);
                                            em.persist(cocheViejo);
                                            em.persist(empleadoActualizar);
                                            em.persist(empleadoViejo);
                                            em.persist(reparacionActualizar);
                                            em.getTransaction().commit();
                                            System.out.println("Reparación actualizada con éxito");
                                        } catch (Exception e) {
                                            em.getTransaction().rollback();
                                            System.err.println("Error al persistir la entidad: " + e.getMessage());
                                        }

                                    } else {
                                        System.err.println("\n Coche no encontrado.");

                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos"); // si se introducen strings cuando se espera ints
                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido");
                                }
                            break;

                            // Eliminar un reparacion
                            case "5":
                                try {
                                    System.out.print("Introduce el ID de la reparación a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    try {
                                        em.getTransaction().begin();
                                        Reparacion reparacionEliminar = em.find(Reparacion.class, idEliminar);
                                        if (reparacionEliminar != null) {
                                            em.remove(reparacionEliminar);
                                            em.getTransaction().commit();
                                            System.out.println("\nReparación eliminada con éxito");
                                        } else {
                                            System.err.println("\nReparacion no encontrada.");
                                            em.getTransaction().rollback();
                                        }
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
                                break;
                        }
                    } while (!opcion.equals("6"));
                    break;
                // Venta
                case "6":
                    do {
                        mostrarMenuOperaciones("Venta");
                        opcion = sc.nextLine();
                        switch (opcion) {
                            // Crear Venta
                            case "1":
                                try {
                                    System.out.print("Introduce la fecha de la venta (aaaa-mm-dd): ");
                                    LocalDate fechaVenta = LocalDate.parse(sc.nextLine());
                                    System.out.print("Introduce el monto de la venta: ");
                                    double monto = sc.nextDouble();
                                    sc.nextLine();

                                    // Empleado encargado de la venta
                                    System.out.print("Introduce el ID del empleado vendedor: ");
                                    int idEmpleado = sc.nextInt();
                                    Empleado empleadoVenta = em.find(Empleado.class, idEmpleado);

                                    // Cliente de la venta
                                    System.out.println("Introduce el ID del cliente: ");
                                    int idCliente = sc.nextInt();

                                    Cliente clienteVenta = em.find(Cliente.class, idCliente);

                                    if (empleadoVenta != null && clienteVenta != null) {
                                        if (empleadoVenta.getPuesto().equals("Vendedor")) {
                                            Venta venta = new Venta(fechaVenta, monto, empleadoVenta, clienteVenta);

                                            // Buscar coche vendido
                                            String respuesta = "";
                                            do {
                                                System.out.print("Introduce el ID del coche vendido: ");
                                                int idCoche = sc.nextInt();
                                                sc.nextLine();
                                                Coche cocheVendido = em.find(Coche.class, idCoche);

                                                if (cocheVendido != null) {
                                                    venta.getCoches().add(cocheVendido);
                                                    cocheVendido.getVentas().add(venta);

                                                    empleadoVenta.getVentas().add(venta);
                                                    clienteVenta.getVentas().add(venta);

                                                    try {
                                                        em.getTransaction().begin();
                                                        em.persist(venta);
                                                        em.persist(cocheVendido);
                                                        em.persist(clienteVenta);
                                                        em.persist(empleadoVenta);
                                                        em.getTransaction().commit();
                                                        System.out.println("Venta añadida con éxito");
                                                    } catch (Exception e) {
                                                        em.getTransaction().rollback();
                                                        System.err.println("Error al persistir la entidad: " + e.getMessage());
                                                    }
                                                } else {
                                                    System.out.println("Coche no encontrado");
                                                }
                                                System.out.println("¿Quieres añadir otro coche a la venta? (S/N)");
                                                respuesta = sc.nextLine();
                                            } while (respuesta.equalsIgnoreCase("S"));

                                        } else {
                                            System.out.println("El empleado no es un vendedor");
                                        }
                                    } else {
                                        System.out.println("No se encontró el empleado o el cliente");
                                    }

                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido");
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos"); // si se introducen strings cuando se espera números
                                }
                                break;

                            // Buscar la venta por ID
                            case "2":
                                System.out.print("Introduce el ID de la venta: ");
                                int idVenta = sc.nextInt();
                                sc.nextLine();

                                Venta ventaEncontrada = em.find(Venta.class, idVenta);
                                if (ventaEncontrada != null) {
                                    System.out.println(ventaEncontrada);
                                } else {
                                    System.err.println("\nVenta no encontrada.");
                                }
                                break;

                            // Listar todas las Reparaciones
                            case "3":
                                List<Venta> ventas = em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList();
                                System.out.println(ventas);
                                break;

                            // Actualizar una Reparacion
                            case "4":
                                try {
                                    System.out.print("Introduce el ID de la venta a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Venta ventaActualizar = em.find(Venta.class, idActualizar);

                                    if (ventaActualizar != null) {

                                        System.out.print("Introduce la nueva fecha (aaaa-mm-dd): ");
                                        LocalDate fechaNueva = LocalDate.parse(sc.nextLine());
                                        System.out.print("Introduce el nuevo monto: ");
                                        double montoNuevo = sc.nextDouble();

                                        // Buscar el nuevo empleado y eliminar el viejo
                                        System.out.print("Introduce el ID del nuevo empleado: ");
                                        int idEmpleado = sc.nextInt();

                                        Empleado empleadoActualizar = em.find(Empleado.class, idEmpleado); //nuevo
                                        Empleado empleadoViejo = ventaActualizar.getEmpleado(); //viejo

                                        System.out.println("Introduce el ID del cliente nuevo: ");
                                        int idCliente = sc.nextInt();

                                        Cliente clienteActualizar = em.find(Cliente.class, idCliente);
                                        Cliente clienteViejo = ventaActualizar.getCliente();

                                        if (empleadoActualizar != null && clienteActualizar != null) {
                                            if (empleadoActualizar.getPuesto().equals("Vendedor")) {
                                                ventaActualizar.getCoches().clear(); // elimino la lista de coches anteriores

                                                // Buscar coche vendido
                                                String respuesta = "";
                                                do {
                                                    System.out.print("Introduce el ID del coche vendido: ");
                                                    int idCoche = sc.nextInt();
                                                    sc.nextLine();
                                                    Coche cocheVendido = em.find(Coche.class, idCoche);

                                                    if (cocheVendido != null) {
                                                        // añadimos los coches nuevos
                                                        ventaActualizar.getCoches().add(cocheVendido);
                                                        cocheVendido.getVentas().add(ventaActualizar);

                                                        ventaActualizar.setEmpleado(empleadoActualizar);
                                                        ventaActualizar.setCliente(clienteActualizar);

                                                        empleadoActualizar.getVentas().add(ventaActualizar);
                                                        clienteActualizar.getVentas().add(ventaActualizar);

                                                        try {
                                                            em.getTransaction().begin();
                                                            em.persist(ventaActualizar);
                                                            em.persist(cocheVendido);
                                                            em.persist(empleadoViejo);
                                                            em.persist(empleadoActualizar);
                                                            em.persist(clienteViejo);
                                                            em.persist(clienteActualizar);
                                                            em.getTransaction().commit();
                                                            System.out.println("Venta añadida con éxito");
                                                        } catch (Exception e) {
                                                            em.getTransaction().rollback();
                                                            System.err.println("Error al persistir la entidad: " + e.getMessage());
                                                        }
                                                    } else {
                                                        System.out.println("Coche no encontrado");
                                                    }
                                                    System.out.println("¿Quieres añadir otro coche a la venta? (S/N)");
                                                    respuesta = sc.nextLine();
                                                } while (respuesta.equalsIgnoreCase("S"));

                                            } else {
                                                System.out.println("El empleado no es un vendendor");
                                            }
                                        } else {
                                            System.out.println("No se encontró el empleado o el cliente");
                                        }

                                    } else {
                                        System.out.println("No se encontró la venta");
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos"); // si se introducen strings cuando se espera ints
                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido");
                                }
                                break;

                            // Eliminar una venta
                            case "5":
                                try {
                                    System.out.print("Introduce el ID de la venta a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    try {
                                        em.getTransaction().begin();
                                        Venta ventaEliminar = em.find(Venta.class, idEliminar);
                                        if (ventaEliminar != null) {
                                            em.remove(ventaEliminar);
                                            em.getTransaction().commit();
                                            System.out.println("\nVenta eliminada con éxito");
                                        } else {
                                            System.err.println("\nVenta no encontrada.");
                                            em.getTransaction().rollback();
                                        }
                                    } catch (Exception e) {
                                        em.getTransaction().rollback();
                                        System.err.println("Error al eliminar la entidad: " + e.getMessage());
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos.");
                                }
                                break;

                            case "6":
                                System.out.println("Saliendo al menu principal...");
                                break;

                            default:
                                System.out.println("Introduce una opción válida.");
                                break;
                        }
                    } while (!opcion.equals("6"));
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
                ║  6. Venta                                           ║
                ║  7. Salir                                           ║
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
                ║  4. Actualizar un registro                           ║
                ║  5. Eliminar un registro                             ║
                ║  6. Salir al menú principal                          ║
                ╚══════════════════════════════════════════════════════╝
                """, entidad.toUpperCase()));

    }

}
