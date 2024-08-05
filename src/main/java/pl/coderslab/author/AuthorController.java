package pl.coderslab.author;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {

        Author author = authorService.findById(id);

        return ResponseEntity.ok(author.toString());
    }

    @PostMapping
    public ResponseEntity<String> saveAuthor(@RequestBody Author author) {

        Author savedAuthor = authorService.save(author);

        return ResponseEntity.ok(savedAuthor.toString());
    }

    @PutMapping
    public ResponseEntity<String> updateAuthor(@RequestBody Author author) {

        Author updatedAuthor = authorService.update(author);

        return ResponseEntity.ok(updatedAuthor.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        Author deletedAuthor = authorService.deleteById(id);

        return ResponseEntity.ok(deletedAuthor.toString());
    }
}
