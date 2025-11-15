package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.Service.AnimalDTOService;
import pt.iade.Zoopolis.models.*;
import pt.iade.Zoopolis.models.repositories.AERepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/ae")
public class AEController {
    private final Logger logger = LoggerFactory.getLogger(AEController.class);

    @Autowired
    private AERepository aeRepository;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AEDTO> getAllAE() {
        logger.info("Sending all AE records with AnimalDTO and EnclosureDTO");
        List<AEDTO> aeDTOList = new ArrayList<>();
        Iterable<AE> aeList = aeRepository.findAll();

        for (AE ae : aeList) {
            AnimalDTO animalDTO = null;
            EnclosureDTO enclosureDTO = null;

            // Map Animal to AnimalDTO
            if (ae.getAnimal() != null) {
                animalDTO = new AnimalDTO(
                        ae.getAnimal().getId(),
                        ae.getAnimal().getName(),
                        ae.getAnimal().getCiName(),
                        ae.getAnimal().getDescription(),
                        ae.getAnimal().getImageUrl()
                );
            }

            // Map Enclosure to EnclosureDTO
            if (ae.getEnclosure() != null) {
                Enclosure enclosure = ae.getEnclosure();
                enclosureDTO = new EnclosureDTO(
                        enclosure.getId(),
                        enclosure.getName(),
                        enclosure.getAnimalClass(),
                        enclosure.getSupportedAmount(),
                        enclosure.getLatitude(),
                        enclosure.getLongitude()
                );
            }

            // Create AEDTO object
            AEDTO aeDTO = new AEDTO(
                    ae.getId(),
                    ae.getDateIn(),
                    ae.getDateOut(),
                    ae.getCode(),
                    animalDTO,
                    enclosureDTO
            );

            aeDTOList.add(aeDTO);
        }

        return aeDTOList;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AEDTO getAEById(@PathVariable int id) {
        logger.info("Sending AE with id {}", id);
        return aeRepository.findById(id)
                .map(ae -> {
                    AnimalDTO animalDTO = null;
                    EnclosureDTO enclosureDTO = null;

                    // Map Animal to AnimalDTO
                    if (ae.getAnimal() != null) {
                        animalDTO = new AnimalDTO(
                                ae.getAnimal().getId(),
                                ae.getAnimal().getName(),
                                ae.getAnimal().getCiName(),
                                ae.getAnimal().getDescription(),
                                ae.getAnimal().getImageUrl()
                        );
                    }

                    // Map Enclosure to EnclosureDTO
                    if (ae.getEnclosure() != null) {
                        Enclosure enclosure = ae.getEnclosure();
                        enclosureDTO = new EnclosureDTO(
                                enclosure.getId(),
                                enclosure.getName(),
                                enclosure.getAnimalClass(),
                                enclosure.getSupportedAmount(),
                                enclosure.getLatitude(),
                                enclosure.getLongitude()
                        );
                    }

                    // Return AEDTO
                    return new AEDTO(
                            ae.getId(),
                            ae.getDateIn(),
                            ae.getDateOut(),
                            ae.getCode(),
                            animalDTO,
                            enclosureDTO
                    );
                })
                .orElseThrow(() -> new RuntimeException("AE with id " + id + " not found"));
    }

    @Autowired
    private AnimalDTOService animalDTOService;

    @GetMapping(path = "/animal/{animalId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AEDTO> getAEByAnimalId(@PathVariable int animalId) {
        logger.info("Fetching AE records for animalId {}", animalId);
        List<AEDTO> aeDTOList = new ArrayList<>();
        Iterable<AE> aeList = aeRepository.findByAnimalId(animalId); // Adjust to use a method that filters by animalId

        for (AE ae : aeList) {
            AnimalDTO animalDTO = null;
            EnclosureDTO enclosureDTO = null;

            // Map Animal to AnimalDTO
            if (ae.getAnimal() != null) {
                animalDTO = new AnimalDTO(
                        ae.getAnimal().getId(),
                        ae.getAnimal().getName(),
                        ae.getAnimal().getCiName(),
                        ae.getAnimal().getDescription(),
                        ae.getAnimal().getImageUrl()
                );
            }

            // Map Enclosure to EnclosureDTO
            if (ae.getEnclosure() != null) {
                Enclosure enclosure = ae.getEnclosure();
                enclosureDTO = new EnclosureDTO(
                        enclosure.getId(),
                        enclosure.getName(),
                        enclosure.getAnimalClass(),
                        enclosure.getSupportedAmount(),
                        enclosure.getLatitude(),
                        enclosure.getLongitude()
                );
            }

            // Create AEDTO object
            AEDTO aeDTO = new AEDTO(
                    ae.getId(),
                    ae.getDateIn(),
                    ae.getDateOut(),
                    ae.getCode(),
                    animalDTO,
                    enclosureDTO
            );

            aeDTOList.add(aeDTO);
        }

        return aeDTOList;
    }



}
