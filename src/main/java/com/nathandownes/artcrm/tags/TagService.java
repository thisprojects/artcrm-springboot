package com.nathandownes.artcrm.tags;

import com.nathandownes.artcrm.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository TagRepository) {
        this.tagRepository = TagRepository;
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public void addNewTag(Tag tag) {
        Optional<Tag> TagByEmail = tagRepository
                .findTagByName(tag.getName());
        if (TagByEmail.isPresent()) {
            throw new IllegalStateException("Tag Already Exists");
        } else {
            tagRepository.save(tag);
        }
    }
}
