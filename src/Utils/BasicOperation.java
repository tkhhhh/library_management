package Utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

// file operation abstract class
public abstract class BasicOperation<T> {
    public abstract List<T> getAllRows() throws IOException , ParseException;

    public abstract List<T> search(String keyword) throws IOException, ParseException;

    public abstract String[] getColumnNames() throws IOException;
}
