package com.alexside.repository;

import com.alexside.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value = "select * from sensor_events " +
            "where sensor_id = ?1 and utc between to_timestamp(?2) and to_timestamp(?3)", nativeQuery = true)
    List<Event> findEventsByInterval(Long sensorId, Integer fromUtc, Integer toUtc);

    @Query(value = "select distinct on (sensor_id) sensor_id, value " +
            "from sensor_events " +
            "join sensors on sensors.id = sensor_id " +
            "where sensors.subject_id = ?1 " +
            "order by sensor_id, utc DESC;" , nativeQuery = true)
    List<Object[]> findNewestEventsBySubjectId(Long subjectId);
}
