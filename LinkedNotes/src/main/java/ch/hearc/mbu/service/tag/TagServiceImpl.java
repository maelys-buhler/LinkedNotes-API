package ch.hearc.mbu.service.tag;

import ch.hearc.mbu.repository.tag.Tag;
import ch.hearc.mbu.repository.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Optional<Tag> getTag(long id) {
        return Optional.ofNullable(tagRepository.findById(id).orElse(null));
    }

    @Override
    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    //TODO check if useful or not, if not delete
    @Override
    public void updateTag(Tag tag) {
        if(tagRepository.existsById(tag.getId()))
        {
            tagRepository.save(tag);
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
