package ch.hearc.mbu.service.link;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.note.Note;

public interface LinkService {
    public Link getLink(long id);
    public Link addLink(Link link);
    public Link updateLink(Link link);
    public void deleteLink(long id);
    public Iterable<Link> getLinks();

    Iterable<Link> getLinksOfUser(String apiKey);

    Iterable<Link> getLinksOfNote(Note note);

    Iterable<Link> getOutgoingLinksOfNote(Note note);

    Iterable<Link> getIncomingLinksOfNote(Note note);

    public boolean idExists(long id);
}
