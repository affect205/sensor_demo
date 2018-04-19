package com.alexside.service;

import com.alexside.dto.SubjectApprox;
import com.alexside.dto.SubjectState;
import com.alexside.model.Subject;
import com.alexside.repository.EventRepository;
import com.alexside.repository.SensorRepository;
import com.alexside.repository.SubjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Текущее значение всех датчиков для заданного объекта
     */
    public SubjectState getState(Long id) {
        List<Object[]> states = eventRepository.findNewestEventsBySubjectId(id);
        SubjectState result = new SubjectState();
        result.setSubjectId(id);
        result.setStates(states.stream().map(s -> new SubjectState.State(
                (Integer)s[0], ((BigDecimal)s[1]).doubleValue())).collect(toList()));
        return result;
    }

    /**
     * Среднее значение для всех текущих значений датчиков по существующим объектам
     */
    public SubjectApprox getApprox() {
        List<SubjectApprox.Approx> approxes = new ArrayList<>();
        List<Subject> subjects = subjectRepository.findAll();
        subjects.forEach(s -> {
            SubjectState state = getState(s.getId());
            Double avg = state.getStates().stream()
                    .mapToDouble(SubjectState.State::getValue)
                    .average()
                    .orElse(0.0);
            approxes.add(new SubjectApprox.Approx(s.getId(), avg));
        });
        return new SubjectApprox(approxes);
    }
}
