package com.alexside.rest;

import com.alexside.dto.EventDTO;
import com.alexside.dto.SubjectApprox;
import com.alexside.dto.SubjectState;
import com.alexside.service.EventService;
import com.alexside.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sensor/api/")
public class SensorAPI {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
    public ResponseEntity<EventDTO> saveEvent(@RequestBody EventDTO event) {
        Long id = eventService.save(event);
        return ResponseEntity.ok(eventService.getRecord(id));
    }

    @RequestMapping(value = "/getHistory", method = RequestMethod.GET)
    public ResponseEntity<List<EventDTO>> getHistory(@RequestParam("sensorId") Long sensorId,
                                                     @RequestParam("fromUtc") Integer fromUtc,
                                                     @RequestParam("toUtc") Integer toUtc) {
        List<EventDTO> history = eventService.getHistory(sensorId, fromUtc, toUtc);
        return ResponseEntity.ok(history);
    }

    @RequestMapping(value = "/getState", method = RequestMethod.GET)
    public ResponseEntity<SubjectState> getState(@RequestParam("subjectId") Long subjectId) {
        SubjectState state = subjectService.getState(subjectId);
        return ResponseEntity.ok(state);
    }

    @RequestMapping(value = "/getApprox", method = RequestMethod.GET)
    public ResponseEntity<SubjectApprox> getApprox() {
        SubjectApprox approx = subjectService.getApprox();
        return ResponseEntity.ok(approx);
    }
}