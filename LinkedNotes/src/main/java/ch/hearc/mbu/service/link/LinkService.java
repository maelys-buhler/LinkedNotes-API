package ch.hearc.mbu.service.link;

import ch.hearc.mbu.repository.link.Link;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public interface LinkService {
    public Optional<Link> getLink(long id);
    public Long addLink(Link link);
    public void updateLink(Link link);
    public void deleteLink(long id);
    public Iterable<Link> getLinks();
    public boolean idExists(long id);
}
