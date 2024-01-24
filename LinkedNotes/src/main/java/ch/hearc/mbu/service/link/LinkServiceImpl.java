package ch.hearc.mbu.service.link;

import ch.hearc.mbu.repository.link.Link;
import ch.hearc.mbu.repository.link.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService{

    @Autowired
    private LinkRepository linkRepository;
    @Override
    public Optional<Link> getLink(long id) {
        return Optional.ofNullable(linkRepository.findById(id).orElse(null));
    }

    @Override
    public Long addLink(Link link) {
        return linkRepository.save(link).getId();
    }

    //TODO check if useful or not, if not delete
    @Override
    public void updateLink(Link link) {
        if(linkRepository.existsById(link.getId()))
        {
            linkRepository.save(link);
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
    public boolean idExists(long id) {
        return linkRepository.existsById(id);
    }
}
