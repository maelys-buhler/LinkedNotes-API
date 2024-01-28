package ch.hearc.mbu.service.tag;

import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag getTag(long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Tag tag) {
        Tag actualTag = tagRepository.findById(tag.getId()).orElse(null);
        if(actualTag != null)
        {
            actualTag.setName(tag.getName());
            tagRepository.save(actualTag);
            return actualTag;
        }
        else
        {
            throw new IllegalArgumentException("Tag does not exist");
        }
    }

    @Override
    public void deleteTag(long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Iterable<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public boolean idExists(long id) {
        return tagRepository.existsById(id);
    }
}
