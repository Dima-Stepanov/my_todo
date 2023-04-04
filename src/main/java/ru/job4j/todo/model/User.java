package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * User модель данных хранения пользователей системы.
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 19.03.2023
 */
@Entity
@Table(name = "todo_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "user_zone")
    private String timeZone;
}
