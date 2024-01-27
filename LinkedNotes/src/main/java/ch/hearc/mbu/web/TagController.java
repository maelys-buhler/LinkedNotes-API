package ch.hearc.mbu.web;

import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping(value = "/")
    public ResponseEntity<Iterable<Tag>> getTags() {
        Iterable<Tag> tags = tagService.getTags();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(tags);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable long id) {
        Optional<Tag> tag = tagService.getTag(id);
        if(tag.isPresent())
        {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(tag.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/", consumes = "application/json")
    public ResponseEntity<String> addTag(@RequestBody Tag tag) {
        if (tag.getName() == null) {
            return ResponseEntity.badRequest().body("Name is null");
        }
        try {
            tagService.addTag(tag);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Tag already exists");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"id\": " + tag.getId() + "}");
    }
}
