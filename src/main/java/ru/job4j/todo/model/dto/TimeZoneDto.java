package ru.job4j.todo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 3. Мидл
 * 3.3. HibernateТема
 * 3.3.2. Конфигурирование
 * 4. TODO list
 * TimeZoneDto DTO (Data Transfer Object) модель представления TimeZone
 *
 * @author Dmitry Stepanov, user Dima_Nout
 * @since 30.03.2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimeZoneDto implements Comparable<TimeZoneDto> {
    private String id;
    @EqualsAndHashCode.Include
    private String displayName;

    @Override
    public int compareTo(TimeZoneDto o) {
        return displayName.compareTo(o.getDisplayName());
    }
}
