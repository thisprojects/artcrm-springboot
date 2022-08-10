package com.nathandownes.artcrm.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;


@RestController
@RequestMapping(path = "api/v1/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService TagService) {
        this.tagService = TagService;
    }

    @GetMapping(path = "/getAll")
    @CrossOrigin(origins = "*")
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @GetMapping(path = "/getSingle/{tagId}")
    @CrossOrigin(origins = "*")
    public Tag getSingleTag(@PathVariable("tagId") UUID tagId) {
        return tagService.getSingleTag(tagId);
    }

    @PostMapping(path = "/create")
    @CrossOrigin(origins = "*")
    public void registerNewTag(@RequestBody Tag tag) {
        tagService.addNewTag(tag);
    }

    @DeleteMapping(path = "/delete/{tagId}")
    @CrossOrigin(origins = "*")
    public void deleteTag(@PathVariable("tagId") UUID tagId) {
        tagService.deleteTag(tagId);
    }

    @DeleteMapping(path = "/deleteMulti")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> deleteMultipleTgs(@RequestBody Set<UUID> tagIds) {
        try {
            tagIds.forEach(tagService::deleteTag);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(path = "/updatejson/{tagId}")
    @CrossOrigin(origins = "*")
    public void updateTagJSON(@PathVariable("tagId") UUID tagId, @RequestBody Tag org) {
        tagService.updateTagJson(tagId, org);
    }
}