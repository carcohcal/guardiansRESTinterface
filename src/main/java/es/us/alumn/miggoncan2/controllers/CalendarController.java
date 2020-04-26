package es.us.alumn.miggoncan2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.alumn.miggoncan2.model.entities.Calendar;
import es.us.alumn.miggoncan2.model.entities.Schedule;
import es.us.alumn.miggoncan2.model.repositories.CalendarRepository;

/**
 * The CalendarController will handle all requests related to {@link Calendar}
 * and {@link Schedule}
 * 
 * @author miggoncan
 */
@RestController
@RequestMapping("/calendars")
public class CalendarController {
	@Autowired
	CalendarRepository calendarRepository;

	/**
	 * This method will handle requests for the whole {@link Calendar} list
	 * 
	 * @return The calendars found in the database
	 */
	@GetMapping("")
	public List<Calendar> getCalendars() {
		return calendarRepository.findAll();
	}
}
