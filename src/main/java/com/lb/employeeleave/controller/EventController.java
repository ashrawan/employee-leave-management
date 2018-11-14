package com.lb.employeeleave.controller;

import com.lb.employeeleave.dto.EventDTO;
import com.lb.employeeleave.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(final EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllEvents(@PageableDefault(page = 0, size = 10, sort = "eventId", direction = Sort.Direction.DESC) Pageable pageable){

        LOGGER.info("API Retrieve all Events");
        return new ResponseEntity<>(eventService.getAllEvents(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveEvent(@PathVariable long id){

        LOGGER.info("API Retrieve single Event");
        return new ResponseEntity<>(eventService.getEventsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO){

        LOGGER.info("API Create Event");
        return new ResponseEntity<>( eventService.createEvent(eventDTO), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO){

        LOGGER.info("API Update Event");
        return new ResponseEntity<>(eventService.updateEvent(eventDTO), HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public ResponseEntity<?> retrieveLeaveAndEventsByDate(
            @RequestParam("date1") String date1,
            @RequestParam("date2") String date2){
        LOGGER.info("API Retrieve Event By Date");
        return new ResponseEntity<>(eventService.retrieveLeaveAndEventsByDate(date1, date2), HttpStatus.OK);
    }

}
