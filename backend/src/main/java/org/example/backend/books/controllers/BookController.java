package org.example.backend.books.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.books.dto.request.BookFilterParamsDTO;
import org.example.backend.books.dto.request.CreateBookDTO;
import org.example.backend.books.dto.request.UpdateBookDetailsDTO;
import org.example.backend.books.dto.response.BookResponseDTO;
import org.example.backend.books.models.Book;
import org.example.backend.books.services.BookService;
import org.example.backend.security.services.SecurityPrincipalService;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookResponseDTO> add(@Valid @RequestBody CreateBookDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.save(dto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Book>> find(BookFilterParamsDTO params,
                                           Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.findAll(params, pageable));
    }

    @GetMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Book>> findFavorite(BookFilterParamsDTO params,
                                                   Pageable pageable) {
        params.setUserUuid(SecurityPrincipalService.getPrincipalUUID());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.findAll(params, pageable));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.findById(id));
    }

    @PatchMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateBookDetailsDTO dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.update(id, dto));
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}/add-to-favorite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponseDTO> addToFavorite(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.addToFavorite(id));
    }

    @DeleteMapping("/{id}/delete-from-favorite")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BookResponseDTO> deleteFromFavorite(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.deleteFromFavorite(id));
    }
}
