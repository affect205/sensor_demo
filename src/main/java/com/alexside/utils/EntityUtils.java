package com.alexside.utils;

import com.alexside.dto.EventDTO;
import com.alexside.dto.SensorDTO;
import com.alexside.dto.SubjectDTO;
import com.alexside.model.Event;
import com.alexside.model.Sensor;
import com.alexside.model.Subject;

import java.util.Date;

import static java.util.stream.Collectors.toList;

public class EntityUtils {
    public static SubjectDTO toDTO(Subject model) {
        if (model != null) {
            SubjectDTO dto = new SubjectDTO();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setSensors(model.getSensors().stream().map(EntityUtils::toDTO).collect(toList()));
            return dto;
        }
        return null;
    }

    public static Subject toModel(SubjectDTO dto) {
        if (dto != null) {
            Subject model = new Subject();
            model.setName(dto.getName());
            model.setSensors(dto.getSensors().stream().map(EntityUtils::toModel).collect(toList()));
            return model;
        }
        return null;
    }

    public static SensorDTO toDTO(Sensor model) {
        if (model != null) {
            SensorDTO dto = new SensorDTO();
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setEvents(model.getEvents().stream().map(EntityUtils::toDTO).collect(toList()));
            return dto;
        }
        return null;
    }

    public static Sensor toModel(SensorDTO dto) {
        if (dto != null) {
            Sensor model = new Sensor();
            model.setName(dto.getName());
            model.setEnabled(dto.getEnabled());
            model.setMin(dto.getMin());
            model.setMax(dto.getMax());
            model.setEvents(dto.getEvents().stream().map(EntityUtils::toModel).collect(toList()));
            return model;
        }
        return null;
    }

    public static EventDTO toDTO(Event model) {
        if (model != null) {
            EventDTO dto = new EventDTO();
            dto.setId(model.getId());
            dto.setSensorId(model.getSensorId());
            dto.setUtc((int)(model.getUtc().getTime() / 1000L));
            dto.setValue(model.getValue());
            return dto;
        }
        return null;
    }

    public static Event toModel(EventDTO dto) {
        if (dto != null) {
            Event model = new Event();
            model.setUtc(new Date(dto.getUtc() * 1000L));
            model.setValue(dto.getValue());
            return model;
        }
        return null;
    }
}
