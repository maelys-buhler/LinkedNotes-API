package ch.hearc.mbu.service.link;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.link.LinkRepository;
import ch.hearc.mbu.repository.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService{

    @Autowired
    private LinkRepository linkRepository;
    @Override
    public Link getLink(long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @Override
    public Link addLink(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public Link updateLink(Link link) {
        Link actualLink = linkRepository.findById(link.getId()).orElse(null);
        if(linkRepository.existsById(link.getId()))
        {
            actualLink.setName(link.getName());
            actualLink.setColor(link.getColor());
            actualLink.setType(link.getType());
            linkRepository.save(actualLink);
            return actualLink;
        }
        else
        {
            throw new IllegalArgumentException("Link does not exist");
        }
    }

    @Override
    public void deleteLink(long id) {
        linkRepository.deleteById(id);
    }

    @Override
    public Iterable<Link> getLinks() {
        return linkRepository.findAll();
    }

    @Override
    public Iterable<Link> getLinksOfUser(String apiKey) {
        return linkRepository.findAllByUser(apiKey);
    }

    @Override
    public Iterable<Link> getLinksOfNote(Note note) {
        return linkRepository.findAllLinkOfNote(note);
    }

    @Override
    public Iterable<Link> getOutgoingLinksOfNote(Note note) {
        return linkRepository.findAllOutgoingLinkOfNote(note);
    }

    @Override
    public Iterable<Link> getIncomingLinksOfNote(Note note) {
        return linkRepository.findAllIncomingLinkOfNote(note);
    }

    @Override
    public boolean idExists(long id) {
        return linkRepository.existsById(id);
    }
}
