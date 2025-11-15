package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.models.Enclosure;
import pt.iade.Zoopolis.models.EnclosureDTO;
import pt.iade.Zoopolis.models.repositories.EnclosureRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/enclosuresDTO")
public class EnclosureDTOController {
    private final Logger logger = LoggerFactory.getLogger(EnclosureDTOController.class);

    @Autowired
    private EnclosureRepository enclosureRepository;

    // Endpoint to retrieve all enclosures as EnclosureDTO
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnclosureDTO> getAllEnclosures() {
        logger.info("Sending all Enclosures as DTO");

        return ((List<Enclosure>) enclosureRepository.findAll()).stream()
                .map(enclosure -> new EnclosureDTO(
                        enclosure.getId(),
                        enclosure.getName(),
                        enclosure.getAnimalClass(),
                        enclosure.getSupportedAmount(),
                        enclosure.getLatitude(),
                        enclosure.getLongitude()
                ))
                .collect(Collectors.toList());
    }

    // Endpoint to retrieve a single enclosure as EnclosureDTO by ID
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EnclosureDTO getEnclosure(@PathVariable int id) {
        logger.info("Sending enclosure with id {} as DTO", id);

        return enclosureRepository.findById(id)
                .map(enclosure -> new EnclosureDTO(
                        enclosure.getId(),
                        enclosure.getName(),
                        enclosure.getAnimalClass(),
                        enclosure.getSupportedAmount(),
                        enclosure.getLatitude(),
                        enclosure.getLongitude()
                ))
                .orElseThrow(() -> new RuntimeException("Enclosure not found"));
    }
}
