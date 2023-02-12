package Utils;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

// file operation for member
public class MemberOperation extends BasicOperation<Member>{

    private Scanner reader;
    private FileWriter writer;
    private String filepath;

    public MemberOperation() throws IOException {
        // read filepath from config file -> normal.properties
        InputStream input = new FileInputStream("./normal.properties");
        Properties prop = new Properties();
        prop.load(input);
        filepath = prop.getProperty("member_path");
    }

    // delete member
    public void delete(Member deleteRow) throws IOException, ParseException {
        List<Member> all = getAllRows();
        all.removeIf(member -> member.equals(deleteRow));
        writeAllRows(all);
    }

    // insert new member
    public void insert(Member newColumn) throws IOException, ParseException {
        if(checkLogin(newColumn.getUsername()) != null) return;
        List<Member> all = getAllRows();
        all.add(newColumn);
        writeAllRows(all);
    }

    // update member
    public void update(Member updateRow, int index) throws IOException, ParseException {
        List<Member> all = getAllRows();
        all.set(index, updateRow);
        writeAllRows(all);
    }

    // search members
    public List<Member> search(String keyword) throws IOException {
        List<Member> result = new ArrayList<>();
        List<Member> all = getAllRows();
        for (Member item: all) {
            if(item.getName().contains(keyword) || item.getUsername().contains(keyword)) {
                result.add(item);
            }
        }
        return result;
    }

    // check member username when login
    public Member checkLogin(String keyword) throws IOException, ParseException {
        List<Member> all = getAllRows();
        for (Member item: all) {
            if(item.getUsername().equals(keyword)) {
                return item;
            }
        }
        return null;
    }

    // get member key in members
    public int getRowKey(Member row) throws IOException {
        List<Member> all = getAllRows();
        for(Member member:all) {
            if(member.equals(row)) return all.indexOf(member);
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

    // get all members
    public List<Member> getAllRows() throws IOException {
        int columnsNumber = getColumnNames().length;
        reader = new Scanner(new File(filepath));
        List<Member> members = new ArrayList<>();
        int index = 0;
        while(reader.hasNextLine()) {
            if(index > 0) {
                String dataSting = reader.nextLine();
                String[] newItem = dataSting.split(",");
                String[] item = new String[columnsNumber];
                System.arraycopy(newItem, 0, item, 0, newItem.length);
                Member member;
                if(item[3].equals("UG")){
                    member = new Undergraduate();
                }
                if(item[3].equals("PG")){
                    member = new Postgraduate();
                }
                else{
                    member = new Staff();
                }
                member.setUsername(item[0]);
                member.setName(item[1]);
                member.setEmail(item[2]);
                member.setLevel(item[3]);
                member.setExpireDate(item[4]);
                members.add(member);
            }else{
                reader.nextLine();
            }
            index++;
        }
        reader.close();
        return members;
    }

    // overwrite file with new data body
    public void writeAllRows(List<Member> newBody) throws IOException {
        String[] columns = getColumnNames();
        writer = new FileWriter(filepath, false);
        writer.write("");
        writer.flush();
        writer.close();
        writer = new FileWriter(filepath, true);
        writer.write(String.join(",", columns));
        for (Member member:newBody) {
            writer.write("\n");
            ArrayList<String> item = new ArrayList<>();
            item.add(member.getUsername());
            item.add(member.getName());
            item.add(member.getEmail());
            item.add(member.getLevel());
            item.add(member.getExpireDate());
            writer.write(String.join(",", item));
        }
        writer.close();
    }
}
