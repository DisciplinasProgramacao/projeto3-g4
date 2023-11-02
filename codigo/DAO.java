import java.io.IOException;
import java.net.IDN;

public interface DAO<T extends IDataToText> {

    public T getNext();

    public T[] getAll() throws IOException;

    public void add(T dado) throws IOException;

    public void addAll(T[] dado);

}
