package pt.iade.Zoopolis.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    // Caminho onde as imagens estão armazenadas
    @Value("${image.path}")  // Defina a propriedade no application.properties
    private String imagePath;

    // Endpoint para acessar a imagem
    @GetMapping("/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imageLocation = Paths.get(imagePath).resolve(imageName);
        Resource imageResource = new UrlResource(imageLocation.toUri());

        if (imageResource.exists() || imageResource.isReadable()) {
            // Retorna a imagem com o tipo de conteúdo adequado
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)  // Ou o tipo adequado para a imagem
                    .body(imageResource);
        } else {
            // Se a imagem não existir ou não puder ser lida
            return ResponseEntity.notFound().build();
        }
    }
}
