package com.alexside.service;

import com.alexside.dto.EventDTO;
import com.alexside.model.Event;
import com.alexside.model.Sensor;
import com.alexside.repository.EventRepository;
import com.alexside.repository.SensorRepository;
import com.alexside.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.alexside.utils.EntityUtils.toDTO;
import static com.alexside.utils.EntityUtils.toModel;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Transactional
    public long save(EventDTO dto) {
        Optional<Sensor> sensor = sensorRepository.findById(dto.getSensorId());
        if (!sensor.isPresent()) {
            log.error(format("Sensor with id %s not found", dto.getId()));
            throw new RuntimeException(format("Sensor with id %s not found", dto.getId()));
        }
        Event event = toModel(dto);
        event.setSensor(sensor.get());
        eventRepository.save(event);
        return event.getId();
    }

    public EventDTO getRecord(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (!event.isPresent()) {
            log.error(format("Event with id %s not found", id));
            throw new RuntimeException(format("Event with id %s not found", id));
        }
        return toDTO(event.get());
    }

    public List<EventDTO> getHistory(Long sensorId, Integer fromUtc, Integer toUtc) {
        List<Event> history = eventRepository.findEventsByInterval(sensorId, fromUtc, toUtc);
        return history.stream().map(EntityUtils::toDTO).collect(toList());
    }
}
