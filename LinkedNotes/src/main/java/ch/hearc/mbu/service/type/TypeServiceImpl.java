package ch.hearc.mbu.service.type;

import ch.hearc.mbu.repository.type.Type;
import ch.hearc.mbu.repository.type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired
    private TypeRepository typeRepository;
    @Override
    public Type getType(long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    public Type addType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type updateType(Type type) {
        return null;
    }

    @Override
    public void deleteType(long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Iterable<Type> getTypes() {
        return typeRepository.findAll();
    }

    @Override
    public boolean idExists(long id) {
        return typeRepository.existsById(id);
    }
}
