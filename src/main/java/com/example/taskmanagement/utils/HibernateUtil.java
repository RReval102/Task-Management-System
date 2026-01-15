package com.example.taskmanagement.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class responsible for building and providing a singleton
 * {@link SessionFactory}.  This class reads configuration from
 * {@code hibernate.cfg.xml} located on the classpath.  It should be
 * shut down when tests or the application finish using it to free
 * resources.
 */
public final class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
        // utility class
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception to help with debugging
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Close caches and connection pools.  This method should be
     * invoked when the application or tests end.
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}