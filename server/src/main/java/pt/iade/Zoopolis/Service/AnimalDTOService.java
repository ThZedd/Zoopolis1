package pt.iade.Zoopolis.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.iade.Zoopolis.models.Animal;
import pt.iade.Zoopolis.models.AnimalDTO;
import pt.iade.Zoopolis.models.repositories.AnimalRepository;

import java.text.Normalizer;
import java.util.Optional;

@Service
public class AnimalDTOService {
    private final Logger logger = LoggerFactory.getLogger(AnimalDTOService.class);

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

    // Retorna um AnimalDTO pelo ID
    public Optional<AnimalDTO> getAnimalDTOById(int id) {
        return animalRepository.findById(id).map(animal -> {
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
        });
    }
}
