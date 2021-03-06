package pt.iade.myiade.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.iade.myiade.models.Presence;
import pt.iade.myiade.models.repositories.PresenceRepository;
import pt.iade.myiade.models.responses.Response;
import pt.iade.myiade.models.views.ScheduleIDView;

@RestController
@RequestMapping(path = "/api/presences")
public class PresenceController{
    
    private Logger logger = LoggerFactory.getLogger(PresenceController.class);
    @Autowired
    private PresenceRepository presenceRepository;
    @PostMapping(path = "/{presenceStudentID}/{presenceScheduleID}")
    public Response registerPresence(@PathVariable int presenceStudentID, @PathVariable int presenceScheduleID) {
    
        logger.info("Registering presence ");

        Integer inserted = presenceRepository.registerPresence(presenceScheduleID, presenceStudentID, LocalDate.now());
        return new Response(" presence registered -> ", inserted);
    }

    @PostMapping(path= "/body", produces = MediaType.APPLICATION_JSON_VALUE)
    public Presence savePresence(@RequestBody Presence presence)
    {
        Presence savedPresence = presenceRepository.save(presence);
        return savedPresence;
    }

    @GetMapping(path="/scheduleid/{qr}")
    public Iterable<ScheduleIDView> getScheduleIDByQRCode(@PathVariable int qr) 
    {
        logger.info("Returning schedule id with QR Code -> " + qr);
        return presenceRepository.findScheduleIDByQRCode(qr);
    }
}
