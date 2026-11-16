package Task03;
import java.util.List;
import java.util.Optional;

public interface Dao<T,K> {
    int insert(T value);
    Optional<T> findById(K id);
    List<T> findAll();
    int update(T value);
    int delete(K id);
}
