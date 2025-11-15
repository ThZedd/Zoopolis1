package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.repositories.AnimalRepository;


import java.util.Optional;

@RestController
@RequestMapping(path = "/api/animals")
public class AnimalController {
    private final Logger logger = LoggerFactory.getLogger(AnimalController.class);
    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Animal> getAnimal() {
        logger.info("Sending all Animals");

        Iterable<Animal> animals = animalRepository.findAll();
        for (Animal animal : animals) {
            // Gerar URL da imagem dinamicamente, se estiver ausente no banco de dados
            if (animal.getImageUrl() == null || animal.getImageUrl().isEmpty()) {
                String defaultImageName = animal.getName().toLowerCase().replace(" ", "-") + ".jpg";
                animal.setImageUrl("http://localhost:8080/images/" + defaultImageName);
            }
        }

        return animals;
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Animal> getAnimal(@PathVariable int id) {
        logger.info("Sending animal with id {}", id);
        Optional<Animal> animal = animalRepository.findById(id);

        if (animal.isPresent()) {
            Animal foundAnimal = animal.get();

            // Gerar URL da imagem dinamicamente, se estiver ausente no banco de dados
            if (foundAnimal.getImageUrl() == null || foundAnimal.getImageUrl().isEmpty()) {
                String defaultImageName = foundAnimal.getName().toLowerCase().replace(" ", "-") + ".jpg";
                foundAnimal.setImageUrl("http://localhost:8080/images/" + defaultImageName);
            }

            return Optional.of(foundAnimal);
        } else {
            throw new RuntimeException("Animal not found");
        }
    }



}
