package Utils;

import java.io.*;
import java.net.URL;
import java.util.*;

//file operation for book
public class BookOperation extends BasicOperation<Book>{

    private Scanner reader;
    private FileWriter writer;
    private String filepath;

    public BookOperation() throws IOException {
        // read filepath from config file -> normal.properties
        InputStream input = new FileInputStream("./normal.properties");
        Properties prop = new Properties();
        prop.load(input);
        filepath = prop.getProperty("book_path");
    }

    // delete book
    public void delete(Book deleteRow) throws IOException {
        List<Book> all = getAllRows();
        all.removeIf(book -> book.equals(deleteRow));
        writeAllRows(all);
    }

    // update book
    public void update(Book updateRow, int index) throws IOException {
        List<Book> all = getAllRows();
        all.set(index, updateRow);
        writeAllRows(all);
    }

    // change book storage
    public void changeStorage(Book book, int num) throws IOException {
        List<Book> all = getAllRows();
        for (Book item: all) {
            if(item.equals(book)) {
                item.setNumber(item.getNumber() + num);
            }
        }
        writeAllRows(all);
    }

    // insert new book
    public void insert(Book newColumn) throws IOException {
        if(getRowKey(newColumn) != -1) {
            changeStorage(newColumn, newColumn.getNumber());
        } else {
            List<Book> all = getAllRows();
            all.add(newColumn);
            writeAllRows(all);
        }
    }

    // search books
    public List<Book> search(String keyword) throws IOException {
        List<Book> result = new ArrayList<>();
        List<Book> all = getAllRows();
        for (Book item: all) {
            if(item.getTitle().contains(keyword)) {
                result.add(item);
            }
        }
        return result;
    }

    // get book key in books
    public int getRowKey(Book row) throws IOException {
        List<Book> all = getAllRows();
        for(Book book:all) {
            if(book.equals(row)) return all.indexOf(book);
        }
        return -1;
    }

    // get column name from file
    public String[] getColumnNames() throws IOException {
        reader = new Scanner(new File(filepath));
        String dataSting = reader.nextLine();
        String[] item = dataSting.split(",");
        reader.close();
        return item;
    }

    // get all books
    public List<Book> getAllRows() throws IOException {
        int columnsNumber = getColumnNames().length;
        reader = new Scanner(new File(filepath));
        List<Book> books = new ArrayList<>();
        int index = 0;
        while(reader.hasNextLine()){
            if(index > 0) {
                String dataSting = reader.nextLine();
                String[] newItem = dataSting.split(",");
                String[] item = new String[columnsNumber];
                System.arraycopy(newItem, 0, item, 0, newItem.length);
                Book book = new Book();
                if(item[1] != null) book.setAuthor(item[1]);
                else book.setAuthor("");
                if(item[4] != null) book.setISBN(item[4]);
                else book.setISBN("");
                if(item[2] != null) book.setPublisher(item[2]);
                else book.setPublisher("");
                if(item[0] != null) book.setTitle(item[0]);
                else book.setTitle("");
                if(item[3] != null) book.setPublishedYear(item[3]);
                else book.setPublishedYear("");
                if(item[5] == null) book.setNumber(0);
                else book.setNumber(Integer.parseInt(item[5]));
                books.add(book);
            }else{
                reader.nextLine();
            }
            index++;
        }
        reader.close();
        for (int i = 0;i < books.size();i++) {
            for(int j = 0;j < books.size();j++) {
                if(i != j) {
                    if (books.get(i).equals(books.get(j))) {
                        books.get(i).setNumber(books.get(i).getNumber() + books.get(j).getNumber());
                        books.remove(books.get(j));
                    }
                }
            }
        }
        return books;
    }

    // overwrite file with new data body
    public void writeAllRows(List<Book> newBody) throws IOException {
        String[] columns = getColumnNames();
        writer = new FileWriter(filepath, false);
        writer.write("");
        writer.flush();
        writer.close();
        writer = new FileWriter(filepath, true);
        writer.write(String.join(",", columns));
        for (Book book:newBody) {
            writer.write("\n");
            ArrayList<String> item = new ArrayList<>();
            item.add(book.getTitle());
            item.add(book.getAuthor());
            item.add(book.getPublisher());
            item.add(book.getPublishedYear());
            item.add(book.getISBN());
            item.add(Integer.toString(book.getNumber()));
            writer.write(String.join(",", item));
        }
        writer.close();
    }
}
