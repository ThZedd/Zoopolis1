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

@RestController
@RequestMapping(path = "/api/favorite")
public class FavoriteController {
    private final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteRepository favoriteRepository;

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

    @GetMapping(path = "/person/{personId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AnimalDTO> getFavoriteAnimalsByPerson(@PathVariable int personId) {
        logger.info("Fetching favorite animals for person with ID {}", personId);
        return favoriteRepository.findAnimalsByPersonId(personId);
    }

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
