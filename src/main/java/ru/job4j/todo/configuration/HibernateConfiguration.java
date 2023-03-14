package ru.job4j.todo.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * HibernateConfiguration конфигурация компонентов Hibernate
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 14.03.2023
 */
@Component
public class HibernateConfiguration {

    @Bean(destroyMethod = "close")
    public SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
