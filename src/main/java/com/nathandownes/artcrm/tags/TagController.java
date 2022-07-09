package com.nathandownes.artcrm.tags;

import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService TagService) {
        this.tagService = TagService;
    }

    @GetMapping(path = "/get")
    public List<Tag> getTags() {
        return tagService.getTags();
    }

    @PostMapping(path = "/add")
    public void registerNewTag(@RequestBody Tag Tag) {
        tagService.addNewTag(Tag);
    }
}