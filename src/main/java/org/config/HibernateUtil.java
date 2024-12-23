package org.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    // Bloque estático para inicializar la SessionFactory solo una vez.
    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error al crear la SessionFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    // Método para obtener una nueva sesión
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // Método para cerrar la SessionFactory cuando se termina
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
