package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * 6. Лямбды и шаблон Command [#49295]
 * CrudRepositorty Реализация поведенческого шаблона проектирования "Command".
 * Здесь мы можем применить шаблон проектирования Command.
 * Command -  это поведенческий шаблон программирования.
 * Цель данного шаблона - инкапсулировать запрос в объект.
 * Это нужно для того, чтобы отделить методы от их конкретной реализации.
 * То есть создать общее поведение, в котором будет выполняться абстрактная команда,
 * а реализация этой команды будет вынесена отдельно, при этом мы получаем возможность менять реализацию,
 * не меняя код самого метода (как в паттерне Стратегия).
 * Превращать запросы в объекты полезно, например, для передачи этих запросов в виде параметров методов, постановки их в очередь, логирования или создания поддержки отмены операций. В нашем случае за счет применения этого шаблона мы сократим количество написанного кода. Помимо UserRepository у нас будут и другие репозитории, в которых будут те же самые CRUD-операции, которые мы имеем в UserRepository. Далее мы рассмотрим вариант выноса создания команд в отдельный класс, чтобы ими можно было пользоваться из нескольких репозиториев (по аналогии с паттерном Стратегия), а не дублировать весь код CRUD-операций в каждом из них.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 16.03.2023
 */
@AllArgsConstructor
@Component
@Slf4j
public class CrudRepository {
    private final SessionFactory sessionFactory;

    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    public void run(String query, Map<String, Object> args) {
        Consumer<Session> command = session -> {
            var sessionQuery = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            sessionQuery.executeUpdate();
        };
        run(command);
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sessionQuery = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            return sessionQuery.uniqueResultOptional();
        };
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sessionQuery = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sessionQuery.setParameter(arg.getKey(), arg.getValue());
            }
            return sessionQuery.list();
        };
        return tx(command);
    }

    /**
     * Реализация шаблона WRAPPER
     *
     * @param command Function
     * @param <T>     type
     * @return type
     */
    public <T> T tx(Function<Session, T> command) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T rsl = command.apply(session);
            transaction.commit();
            return rsl;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.warn("Transaction is rollback: {}", transaction.getStatus());
            }
            log.error("Error operation: {}", e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }
}
