package br.com.firedroid.controller;

//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;

//@RestController
//@RequestMapping("/images")
public class ImageController {

//    private final String uploadDir = "uploads/parallax";
//
//    @GetMapping("/{filename:.+}")
//    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
//        try {
//            Path file = Paths.get(uploadDir).resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//
//            if (resource.exists() && resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_PNG) // ou detectar dinamicamente
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
}