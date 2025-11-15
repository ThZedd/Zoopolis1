package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.AnimalDTO;
import pt.iade.Zoopolis.models.repositories.AnimalRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.stream.Collectors;
import java.text.Normalizer;
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping(path = "/api/animalsDTO")
public class AnimalDTOController {
    private final Logger logger = LoggerFactory.getLogger(AnimalDTOController.class);

    @Autowired
    private AnimalRepository animalRepository;

    // Gera URL da imagem dinamicamente

    private String generateImageUrl(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String url = "http://10.0.2.2:8081/api/images/" + normalized.toLowerCase().replace(" ", "-") + ".jpg";
        logger.info("Generated Image URL: {}", url);
        return url;
    }



    // Endpoint que retorna todos os animais como AnimalDTO
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnimalDTO> getAllAnimals() {
        logger.info("Sending all Animals as DTO");

        return ((List<Animal>) animalRepository.findAll()).stream()
                .map(animal -> {
                    String imageUrl = animal.getImageUrl();
                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = generateImageUrl(animal.getName());
                    }

                    return new AnimalDTO(
                            animal.getId(),
                            animal.getName(),
                            animal.getCiName(),
                            animal.getDescription(),
                            imageUrl
                    );
                })
                .collect(Collectors.toList());
    }

    // Endpoint que retorna um único animal como AnimalDTO
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AnimalDTO getAnimal(@PathVariable int id) {
        logger.info("Sending animal with id {} as DTO", id);

        return animalRepository.findById(id)
                .map(animal -> {
                    String imageUrl = animal.getImageUrl();
                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = generateImageUrl(animal.getName());
                    }

                    return new AnimalDTO(
                            animal.getId(),
                            animal.getName(),
                            animal.getCiName(),
                            animal.getDescription(),
                            imageUrl
                    );
                })
                .orElseThrow(() -> new RuntimeException("Animal not found"));
    }
}






