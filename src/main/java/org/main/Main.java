package org.main;

import org.model.*;
import org.services.*;
import org.servicesImpl.*;

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
        // Conexión
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadDePersistencia");
        EntityManager em = emf.createEntityManager();

        // DAOs
        ClienteDAO clienteDAO = new ClienteDAOImpl(em);
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(em);
        CocheDAO cocheDAO = new CocheDAOImpl(em);
        ReparacionDAO reparacionDAO = new ReparacionDAOImpl(em);
        VentaDAO ventaDAO = new VentaDAOImpl(em);

        Scanner sc = new Scanner(System.in);
        String opcion;

        do {
            mostrarMenuPrincipal();
            opcion = sc.nextLine();
            switch (opcion) {
                // Usuario
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

                                usuarioDAO.save(usuario);
                                break;

                            // Buscar Usuario por ID
                            case "2":
                                try {
                                    System.out.print("Introduce el id del usuario: ");
                                    int idUsuario = sc.nextInt();
                                    sc.nextLine();

                                    Usuario usuarioEncontrado = usuarioDAO.findById(idUsuario);

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
                                List<Usuario> usuarios = usuarioDAO.findAll();
                                System.out.println(usuarios);
                                break;

                            // Actualizar Usuario
                            case "4":
                                try {
                                    System.out.print("Introduce el ID del usuario a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Usuario usuarioActualizar = em.find(Usuario.class, idActualizar);

                                    if (usuarioActualizar != null){
                                        System.out.print("Introduce el nuevo username: ");
                                        String usernameNuevo = sc.nextLine();

                                        System.out.print("Introduce la nueva contraseña: ");
                                        String contrasenaNueva = sc.nextLine();

                                        usuarioActualizar.setUsername(usernameNuevo);
                                        usuarioActualizar.setPassword(contrasenaNueva);

                                        usuarioDAO.update(usuarioActualizar);

                                    } else {
                                        System.err.println("\n Usuario no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Eliminar un usuario
                            case "5":
                                try {
                                    System.out.print("Introduce el ID del usuario a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    usuarioDAO.delete(idEliminar);

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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

                                    Usuario usuarioEncontrado = usuarioDAO.findById(usuarioId);
                                    if (usuarioEncontrado != null) {
                                        Empleado empleado = new Empleado(nombre, puesto, salario, usuarioEncontrado);
                                        usuarioEncontrado.setEmpleado(empleado);

                                        empleadoDAO.save(empleado);
                                        usuarioDAO.update(usuarioEncontrado);

                                    } else {
                                        System.err.println("\nUsuario no encontrado.");
                                    }

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                                // Buscar Empleado por ID
                            case "2":
                                try{
                                    System.out.print("Introduce el id del empleado: ");
                                    int idEmpleado = sc.nextInt();
                                    sc.nextLine();

                                    Empleado empleadoEncontrado = empleadoDAO.findById(idEmpleado);
                                    if (empleadoEncontrado != null) {
                                        System.out.println(empleadoEncontrado);
                                    } else {
                                        System.err.println("\nEmpleado no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Listar todos los Empleados
                            case "3":
                                List<Empleado> empleados = empleadoDAO.findAll();
                                System.out.println(empleados);
                                break;

                            // Actualizar Empleado
                            case "4":
                                try {
                                    System.out.print("Introduce el ID del empleado a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Empleado empleadoActualizar = empleadoDAO.findById(idActualizar);

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
                                        Usuario usuarioNuevo = usuarioDAO.findById(idUsuario);

                                        if(usuarioNuevo != null){
                                            usuarioViejo.setEmpleado(null); // borrar el empleado del usuario viejo

                                            empleadoActualizar.setNombre(nombreNuevo);
                                            empleadoActualizar.setPuesto(puestoNuevo);
                                            empleadoActualizar.setSalario(salarioNuevo);
                                            empleadoActualizar.setUsuario(usuarioNuevo);

                                            usuarioNuevo.setEmpleado(empleadoActualizar);

                                            usuarioDAO.update(usuarioViejo);
                                            usuarioDAO.update(usuarioNuevo);
                                            empleadoDAO.update(empleadoActualizar);

                                        } else {
                                            System.out.println("No se ha encontrado el usuario.");
                                        }

                                    } else {
                                        System.err.println("\n Empleado no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Eliminar un empleado
                            case "5":
                                try {
                                    System.out.print("Introduce el ID del empleado a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    empleadoDAO.delete(idEliminar);

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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

                                clienteDAO.save(cliente);

                                break;

                            // Buscar Cliente por ID
                            case "2":
                                try{
                                    System.out.print("Introduce el id del cliente: ");
                                    int idCliente = sc.nextInt();
                                    sc.nextLine();

                                    Cliente clienteEncontrado = clienteDAO.findById(idCliente);
                                    if (clienteEncontrado != null) {
                                        System.out.println(clienteEncontrado);
                                    } else {
                                        System.err.println("\nCliente no encontrado.");
                                    }

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Listar todos los Clientes
                            case "3":
                                List<Cliente> clientes = clienteDAO.findAll();
                                System.out.println(clientes);
                                break;

                            // Actualizar Cliente
                            case "4":
                                try{
                                    System.out.print("Introduce el ID del cliente a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Cliente clienteActualizar = clienteDAO.findById(idActualizar);

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

                                       clienteDAO.update(clienteActualizar);

                                    } else {
                                        System.err.println("\n Cliente no encontrado.");
                                    }

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Eliminar un Cliente
                            case "5":
                                try{
                                    System.out.print("Introduce el ID del cliente a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    clienteDAO.delete(idEliminar);

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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
                                    sc.nextLine();

                                    Cliente clienteEncontrado = clienteDAO.findById(idCliente);

                                    if (clienteEncontrado != null) {
                                        Coche coche = new Coche(marca, modelo, anio, precio);
                                        clienteEncontrado.getCoches().add(coche); // añade el coche al cliente
                                        coche.getClientes().add(clienteEncontrado); // añade el cliente al coche

                                        cocheDAO.save(coche);
                                        clienteDAO.update(clienteEncontrado);

                                    } else {
                                        System.err.println("\nCliente no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Buscar Coche por ID
                            case "2":
                                try{
                                    System.out.print("Introduce el id del coche: ");
                                    int idCoche = sc.nextInt();
                                    sc.nextLine();

                                    Coche cocheEncontrado = cocheDAO.findById(idCoche);
                                    if (cocheEncontrado != null) {
                                        System.out.println(cocheEncontrado);
                                    } else {
                                        System.err.println("\nCoche no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Listar todos los Coches
                            case "3":
                                List<Coche> coches = cocheDAO.findAll();
                                System.out.println(coches);
                                break;

                            case "4":
                                try {
                                    System.out.print("Introduce el ID del coche a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Coche cocheActualizar = cocheDAO.findById(idActualizar);

                                    if (cocheActualizar != null){
                                        System.out.print("Introduce la nueva marca: ");
                                        String marcaNuevo = sc.nextLine();
                                        System.out.print("Introduce el nuevo modelo: ");
                                        String modeloNuevo = sc.nextLine();
                                        System.out.print("Introdue el nuevo año: ");
                                        int anioNuevo = sc.nextInt();
                                        System.out.print("Introduce el nuevo precio: ");
                                        double precioNuevo = sc.nextDouble();
                                        sc.nextLine();

                                        cocheActualizar.setMarca(marcaNuevo);
                                        cocheActualizar.setModelo(modeloNuevo);
                                        cocheActualizar.setAnio(anioNuevo);
                                        cocheActualizar.setPrecio(precioNuevo);

                                        cocheDAO.update(cocheActualizar);

                                    } else {
                                        System.err.println("\n Coche no encontrado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
                                }
                                break;

                            // Eliminar un coche
                            case "5":
                                try{
                                    System.out.print("Introduce el ID del coche a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    cocheDAO.delete(idEliminar);

                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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
                                    sc.nextLine();
                                    Coche cocheReparado = cocheDAO.findById(idCoche);

                                    // Buscar empleado encargado de la reparacion
                                    System.out.print("Introduce el ID del empleado encargado de la reparación: ");
                                    int idEmpleado = sc.nextInt();
                                    sc.nextLine();
                                    Empleado empleadoReparacion = empleadoDAO.findById(idEmpleado);

                                    if (cocheReparado != null && empleadoReparacion != null) {
                                        if (empleadoReparacion.getPuesto().equals("Mecánico")) {
                                            Reparacion reparacion = new Reparacion(descripcion, fechaReparacion, costo);

                                            cocheReparado.getReparaciones().add(reparacion);
                                            reparacion.setCoche(cocheReparado);

                                            empleadoReparacion.getReparaciones().add(reparacion);
                                            reparacion.setEmpleado(empleadoReparacion);

                                            reparacionDAO.save(reparacion);
                                            cocheDAO.update(cocheReparado);
                                            empleadoDAO.update(empleadoReparacion);

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
                                    sc.nextLine();
                                }
                                break;

                            // Buscar la Reparación por ID
                            case "2":
                                System.out.print("Introduce el ID de la reparación: ");
                                int idReparacion = sc.nextInt();
                                sc.nextLine();

                                Reparacion reparacionEncontrada = reparacionDAO.findById(idReparacion);
                                if (reparacionEncontrada != null) {
                                    System.out.println(reparacionEncontrada);
                                } else {
                                    System.err.println("\nReparación no encontrada.");
                                }
                                break;

                            // Listar todas las Reparaciones
                            case "3":
                                List<Reparacion> reparaciones = reparacionDAO.findAll();
                                System.out.println(reparaciones);
                                break;

                            // Actualizar una Reparacion
                            case "4":
                                try {
                                    System.out.print("Introduce el ID de la reparación a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Reparacion reparacionActualizar = reparacionDAO.findById(idActualizar);

                                    if (reparacionActualizar != null) {
                                        System.out.print("Introduce la nueva descripcion: ");
                                        String descripcionNueva = sc.nextLine();

                                        System.out.print("Introduce la nueva fecha (aaaa-mm-dd): ");
                                        LocalDate fechaNueva = LocalDate.parse(sc.nextLine());

                                        System.out.print("Introduce el nuevo costo: ");
                                        double costoNuevo = sc.nextDouble();
                                        sc.nextLine();

                                        // Buscar el nuevo empleado y eliminar el viejo
                                        System.out.print("Introduce el ID del nuevo empleado: ");
                                        int idEmpleado = sc.nextInt();
                                        sc.nextLine();

                                        Empleado empleadoActualizar = empleadoDAO.findById(idEmpleado);
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
                                        sc.nextLine();

                                        Coche cocheActualizar = cocheDAO.findById(idCoche); //nuevo
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

                                        reparacionDAO.update(reparacionActualizar);

                                    } else {
                                        System.err.println("\n Coche no encontrado.");

                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error en la introducción de datos");
                                    sc.nextLine();// si se introducen strings cuando se espera ints
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

                                    reparacionDAO.delete(idEliminar);

                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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
                                    Empleado empleadoVenta = empleadoDAO.findById(idEmpleado);

                                    // Cliente de la venta
                                    System.out.println("Introduce el ID del cliente comprador: ");
                                    int idCliente = sc.nextInt();
                                    Cliente clienteVenta = clienteDAO.findById(idCliente);

                                    if (empleadoVenta != null && clienteVenta != null) {
                                        if (empleadoVenta.getPuesto().equals("Vendedor")) {
                                            Venta venta = new Venta(fechaVenta, monto, empleadoVenta, clienteVenta);

                                            // Buscar coche vendido
                                            String respuesta = "";
                                            do {
                                                System.out.print("Introduce el ID del coche vendido: ");
                                                int idCoche = sc.nextInt();
                                                sc.nextLine();
                                                Coche cocheVendido = cocheDAO.findById(idCoche);

                                                System.out.println(cocheVendido.getClientes().getLast());

                                                if (cocheVendido != null) {
                                                    venta.getCoches().add(cocheVendido);
                                                    cocheVendido.getVentas().add(venta);

                                                    clienteVenta.getCoches().add(cocheVendido);
                                                    ventaDAO.save(venta);



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
                                    sc.nextLine();
                                }
                                break;

                            // Buscar la venta por ID
                            case "2":
                                System.out.print("Introduce el ID de la venta: ");
                                int idVenta = sc.nextInt();
                                sc.nextLine();

                                Venta ventaEncontrada = ventaDAO.findById(idVenta);
                                if (ventaEncontrada != null) {
                                    System.out.println(ventaEncontrada);
                                } else {
                                    System.err.println("\nVenta no encontrada.");
                                }
                                break;

                            // Listar todas las Reparaciones
                            case "3":
                                List<Venta> ventas = ventaDAO.findAll();
                                System.out.println(ventas);
                                break;

                            // Actualizar una Venta
                            case "4":
                                try {
                                    System.out.print("Introduce el ID de la venta a actualizar: ");
                                    int idActualizar = sc.nextInt();
                                    sc.nextLine();

                                    Venta ventaActualizar = ventaDAO.findById(idActualizar);

                                    if (ventaActualizar != null) {
                                        System.out.print("Introduce la nueva fecha (aaaa-mm-dd): ");
                                        LocalDate fechaNueva = LocalDate.parse(sc.nextLine());
                                        System.out.print("Introduce el nuevo monto: ");
                                        double montoNuevo = sc.nextDouble();
                                        sc.nextLine();

                                        // Actualizar atributos básicos
                                        ventaActualizar.setFecha(fechaNueva);
                                        ventaActualizar.setMonto(montoNuevo);

                                        // Actualizar empleado
                                        System.out.print("Introduce el ID del nuevo empleado: ");
                                        int idEmpleado = sc.nextInt();
                                        sc.nextLine();

                                        Empleado empleadoActualizar = empleadoDAO.findById(idEmpleado);
                                        if (empleadoActualizar == null || !empleadoActualizar.getPuesto().equals("Vendedor")) {
                                            System.out.println("El empleado no existe o no es un vendedor.");
                                            break;
                                        }
                                        ventaActualizar.setEmpleado(empleadoActualizar);

                                        // Actualizar cliente
                                        System.out.print("Introduce el ID del nuevo cliente: ");
                                        int idCliente = sc.nextInt();
                                        sc.nextLine();

                                        Cliente clienteActualizar = clienteDAO.findById(idCliente);
                                        if (clienteActualizar != null) {
                                            ventaActualizar.setCliente(clienteActualizar);

                                            // Actualizar coches vendidos
                                            ventaActualizar.getCoches().clear(); // Eliminar coches anteriores
                                            String respuesta;
                                            do {
                                                System.out.print("Introduce el ID del coche vendido: ");
                                                int idCoche = sc.nextInt();
                                                sc.nextLine();

                                                Coche cocheVendido = cocheDAO.findById(idCoche);
                                                if (cocheVendido != null) {
                                                    ventaActualizar.getCoches().add(cocheVendido);
                                                    cocheVendido.getVentas().add(ventaActualizar);
                                                } else {
                                                    System.out.println("Coche no encontrado.");
                                                }

                                                System.out.print("¿Quieres añadir otro coche a la venta? (S/N): ");
                                                respuesta = sc.nextLine();
                                            } while (respuesta.equalsIgnoreCase("S"));

                                            ventaDAO.update(ventaActualizar);
                                            System.out.println("Venta actualizada con éxito.");
                                        } else {
                                            System.out.println("Cliente no encontrado.");
                                        }
                                    } else {
                                        System.out.println("No se encontró la venta.");
                                    }
                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos."); // Si se introducen strings cuando se espera un número
                                    sc.nextLine();
                                } catch (DateTimeParseException e) {
                                    System.err.println("Formato de fecha no válido.");
                                }
                                break;

                            // Eliminar una venta
                            case "5":
                                try {
                                    System.out.print("Introduce el ID de la venta a eliminar: ");
                                    int idEliminar = sc.nextInt();
                                    sc.nextLine();

                                    ventaDAO.delete(idEliminar);

                                } catch (InputMismatchException e) {
                                    System.err.println("Error en la introducción de datos.");
                                    sc.nextLine();
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

                case "7":
                    do{
                        mostrarMenuConsultas();
                        opcion = sc.nextLine();

                        switch (opcion){
                            case "1":
                                // Listar todos los coches vendidos en un rango de fechas
                                try {
                                    System.out.print("Ingrese la fecha de inicio (aaaa-mm-dd): ");
                                    LocalDate fechaInicio = LocalDate.parse(sc.nextLine());
                                    System.out.print("Ingrese la fecha de fin (aaaa-mm-dd): ");
                                    LocalDate fechaFin = LocalDate.parse(sc.nextLine());

                                    List<Coche> resultados = cocheDAO.cochesVendidosFecha(fechaInicio, fechaFin);

                                    System.out.println("\nCoches vendidos en el rango de fechas:");
                                    System.out.println(resultados);
                                } catch (DateTimeParseException e){
                                    System.err.println("Formato de fecha incorrecto");
                                }
                                break;
                            case "2":
                                // Mostrar las reparaciones realizadas por un mecánico específico
                                try{
                                    System.out.print("Ingrese el ID del mecánico: ");
                                    int idEmpleado = sc.nextInt();
                                    sc.nextLine();
                                    Empleado empleadoReparaciones = empleadoDAO.findById(idEmpleado);
                                    if(empleadoReparaciones != null){
                                        if(empleadoReparaciones.getPuesto().equals("Mecánico")){
                                            List<Reparacion> resultados = reparacionDAO.reparacionesById(idEmpleado);
                                            System.out.println("\nReparaciones realizadas por el mecánico:");
                                            System.out.println(resultados);
                                        } else {
                                            System.out.println("El empleado no es mecánico. No tiene reparaciones.");
                                        }
                                    } else {
                                        System.out.println("No se encontró el empleado.");
                                    }
                                } catch (InputMismatchException e){
                                    System.err.println("Error al introducir datos");
                                }
                                break;

                            case "3":
                                // Obtener los coches que ha comprado un cliente en particular.
                                try{
                                    System.out.print("Ingrese el ID del cliente: ");
                                    int idCliente = sc.nextInt();
                                    sc.nextLine();

                                    if(clienteDAO.findById(idCliente) != null){
                                        List<Coche> resultados = cocheDAO.cochesByIdCliente(idCliente);
                                        System.out.println("\nCoches comprados por el cliente:");
                                        System.out.println(resultados);
                                    } else {
                                        System.out.println("No se encontró el cliente.");
                                    }

                                } catch (InputMismatchException e){
                                    System.err.println("Error al introducir datos");
                                    sc.nextLine();
                                }
                                break;

                            case "4":
                                // Calcular el total de ingresos generados por ventas en un mes.
                                try {
                                    System.out.print("Ingrese el mes (1-12): ");
                                    int mes = sc.nextInt();
                                    System.out.print("Ingrese el año: ");
                                    int anio = sc.nextInt();
                                    sc.nextLine();

                                    Double ingresos = ventaDAO.obtenerIngresosPorMesYAnio(mes, anio);
                                    System.out.println("\nIngresos totales en el mes: " + ingresos);

                                } catch (InputMismatchException e){
                                    System.err.println("Error al introducir datos");
                                    sc.nextLine();

                                }
                                break;
                            case "5":
                                System.out.println("Saliendo al menu principal...");
                                break;
                        }

                    } while (!opcion.equals("5"));

                // Salir
                case "8":
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Ingrese una opción válida.");
                    break;
            }
        } while (!opcion.equals("8"));

        sc.close();
    }

    /**
     *  Muestra un menú principal con opciones
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("""
                \n
                ═══════════════════════════════════════════════════════
                             GESTIÓN DE TALLER MECÁNICO
                ═══════════════════════════════════════════════════════
                     Seleccione una opción de las siguientes:
                      1. Usuario.
                      2. Empleado.                                       
                      3. Cliente.                                        
                      4. Coche.                                          
                      5. Reparación.                                     
                      6. Venta                                           
                      7. Consultas avanzadas                             
                      8. Salir                                          
                ═══════════════════════════════════════════════════════
                """);
    }

    /**
     * Muestra un menú para cada entidad
     * @param entidad cada entidad en la BBDD
     */
    private static void mostrarMenuOperaciones(String entidad) {
        System.out.println(String.format("""
                \n
                ════════════════════════════════════════════════════════
                                       %s
                ════════════════════════════════════════════════════════
                     Seleccione una opción de las siguientes:             
                      1. Crear un registro                                
                      2. Buscar por id                                    
                      3. Mostrar todos los registros                      
                      4. Actualizar un registro                           
                      5. Eliminar un registro                             
                      6. Salir al menú principal                          
                ════════════════════════════════════════════════════════
                """, entidad.toUpperCase()));

    }

    /**
     * Muestra un menú con consultas avanzadas
     */
    private static void mostrarMenuConsultas() {
        System.out.println("""
                \n
                ═══════════════════════════════════════════════════════════════════════════════
                                            Consultas Avanzadas
                ═══════════════════════════════════════════════════════════════════════════════
                    Seleccione una opción de las siguientes:             
                      1. Listar todos los coches vendidos en un rango de fechas.                            
                      2. Mostrar las reparaciones realizadas por un mecánico específico.                         
                      3. Obtener los coches que ha comprado un cliente en particular.                  
                      4. Calcular el total de ingresos generados por ventas en un mes.                      
                      5. Salir al menú principal                          
                ═══════════════════════════════════════════════════════════════════════════════
                """);
    }
}
