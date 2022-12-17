package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/students/{id}/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadStudentsAvatar(
            @PathVariable long id,
            @RequestParam MultipartFile avatar) throws IOException {

        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("Слишком большой файл");
        }
        avatarService.uploadStudentsAvatar(id, avatar);
        return ResponseEntity.ok().body("Аватар успешно установлен");
    }

//    @GetMapping
//    public void getStudentsAvatar(@PathVariable long id) {
//        avatarService.getStudentsAvatar(id);
//    }
//
//    @GetMapping("/from-database")
//    public void getStudentsAvatarPreview(@PathVariable long id) {
//        avatarService.getStudentsAvatarPreview(id);
//    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity handleException() {
//        return ResponseEntity.badRequest().build();
//    }
}
