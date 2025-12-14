package pt.iade.Zoopolis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.Favorite;
import pt.iade.Zoopolis.models.Person;
import pt.iade.Zoopolis.models.AnimalDTO;
import pt.iade.Zoopolis.models.repositories.FavoriteRepository;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/favorite")
public class FavoriteController {
    private final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

    // --- LÓGICA DE GERAR IMAGEM ADICIONADA ---
    private String generateImageUrl(String name) {
        if (name == null) return null;
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String url = "http://10.0.2.2:8081/api/images/" + normalized.toLowerCase().replace(" ", "-") + ".jpg";
        logger.info("Generated Image URL for Favorite: {}", url);
        return url;
    }
    // ------------------------------------------

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Favorite> getFavorites() {
        logger.info("Sending all favorites");
        return favoriteRepository.findAll();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Favorite> getFavorite(@PathVariable int id) {
        logger.info("Fetching favorite with id {}", id);
        return favoriteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MÉTODO ALTERADO PARA CORRIGIR O NULL ---
    @GetMapping(path = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnimalDTO> getFavoriteAnimalsByPerson(@PathVariable int personId) {
        logger.info("Fetching favorite animals for person with ID {}", personId);

        // Obtém a lista do repositório
        List<AnimalDTO> favorites = (List<AnimalDTO>) favoriteRepository.findAnimalsByPersonId(personId);

        // Processa cada animal para garantir que a imagem não é null
        return favorites.stream()
                .map(animal -> {
                    String imageUrl = animal.getImageUrl();

                    // Se a imagem for null ou vazia, gera o link automaticamente
                    if (imageUrl == null || imageUrl.isEmpty()) {
                        imageUrl = generateImageUrl(animal.getName());
                    }

                    // Recria o DTO com o URL correto (assumindo que tens este construtor no AnimalDTO)
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
    // --------------------------------------------

    @GetMapping(path = "/isFavorite", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean isFavorite(@RequestParam int personId, @RequestParam int animalId) {
        logger.info("Checking if animal {} is favorite for person {}", animalId, personId);
        return favoriteRepository.isFavorite(personId, animalId);
    }

    // ✅ Adicionar favorito corretamente com EntityManager implícito
    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> addFavorite(@RequestParam int personId, @RequestParam int animalId) {
        logger.info("Adding animal {} to favorites for person {}", animalId, personId);

        if (favoriteRepository.isFavorite(personId, animalId)) {
            return ResponseEntity.badRequest().body("Animal is already a favorite for this person.");
        }

        Person person = new Person();
        person.setId(personId);

        Animal animal = new Animal();
        animal.setId(animalId);

        Favorite favorite = new Favorite();
        favorite.setPerson(person);
        favorite.setAnimal(animal);

        favoriteRepository.save(favorite);
        return ResponseEntity.ok("Animal added to favorites successfully.");
    }

    @DeleteMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> removeFavorite(@RequestParam int personId, @RequestParam int animalId) {
        logger.info("Removing animal {} from favorites for person {}", animalId, personId);

        if (!favoriteRepository.isFavorite(personId, animalId)) {
            return ResponseEntity.status(404).body("Animal is not a favorite for this person.");
        }

        favoriteRepository.removeFavorite(personId, animalId);
        return ResponseEntity.ok("Animal removed from favorites successfully.");
    }
}