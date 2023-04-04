package ru.job4j.todo.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateConfigurationFromTest
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.04.2023
 */
public class HibernateConfigurationFromTest {
    public static SessionFactory getSFFromTest() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
