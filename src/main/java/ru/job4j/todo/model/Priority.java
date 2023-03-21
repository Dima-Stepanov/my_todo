package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * Priority модель данных описывает приоритет задачи.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 21.03.2023
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
@Entity
@Table(name = "priorities")
public class Priority {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    private int position;
}
