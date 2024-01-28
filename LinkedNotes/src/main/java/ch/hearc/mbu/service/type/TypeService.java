package ch.hearc.mbu.service.type;

import ch.hearc.mbu.repository.type.Type;

import java.util.Objects;
import java.util.Optional;

public interface TypeService {
    public Type getType(long id);
    public Type addType(Type type);
    public Type updateType(Type type);
    public void deleteType(long id);
    public Iterable<Type> getTypes();
    public boolean idExists(long id);
}
