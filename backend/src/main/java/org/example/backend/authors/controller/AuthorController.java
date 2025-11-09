package org.example.backend.authors.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.authors.dto.request.CreateAuthorDTO;
import org.example.backend.authors.dto.response.AuthorResponseDTO;
import org.example.backend.authors.models.Author;
import org.example.backend.authors.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorResponseDTO> add(@Valid @RequestBody CreateAuthorDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorService.addAuthor(dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Author>> find(@RequestParam(required = false) String fullName, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.findAll(fullName, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.findById(id));
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
