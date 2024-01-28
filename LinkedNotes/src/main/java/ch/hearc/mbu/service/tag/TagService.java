package ch.hearc.mbu.service.tag;

import ch.hearc.mbu.repository.tag.Tag;

import java.util.Optional;

public interface TagService {
    public Tag getTag(long id);
    public Tag addTag(Tag tag);
    public void updateTag(Tag tag);
    public void deleteTag(long id);
    public Iterable<Tag> getTags();
    public boolean idExists(long id);
}
