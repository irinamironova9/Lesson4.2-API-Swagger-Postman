package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/students/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadStudentsAvatar(
            @PathVariable long id,
            @RequestParam MultipartFile avatar) throws IOException {

        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("Слишком большой файл");
        }
        avatarService.uploadStudentsAvatar(id, avatar);
        return ResponseEntity.ok().body("Аватар успешно загружен");
    }

    @GetMapping("/{id}/from-local-disc")
    public void getStudentsAvatarFromLocalDisc(
            @PathVariable long id,
            HttpServletResponse response) throws IOException {

        Avatar avatar = avatarService.findStudentsAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/{id}/from-database")
    public ResponseEntity<byte[]> getStudentsAvatarFromDB(@PathVariable Long id) {

        Avatar avatar = avatarService.findStudentsAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping
    public Collection<Avatar> getAllAvatars(
            @RequestParam("page") int pageNumber,
            @RequestParam("size") int pageSize) {

        return avatarService.getAllAvatars(pageNumber, pageSize);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.badRequest().build();
    }
}
