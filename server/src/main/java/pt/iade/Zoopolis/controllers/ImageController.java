package pt.iade.Zoopolis.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${image.path}")
    private String imagePath;

    @GetMapping("/{imageName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imageName) {
        try {
            File imageFile = new File(imagePath + imageName);
            if (imageFile.exists()) {
                InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // ou MediaType.IMAGE_PNG, etc.
                        .body(resource);
            }
        } catch (Exception e) {
            // Logar o erro seria uma boa prática
        }
        return ResponseEntity.notFound().build();
    }
}
