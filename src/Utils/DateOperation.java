package Utils;

import java.text.ParseException;

// class for date operation
public class DateOperation {
    private int year;
    private int month;
    private int day;

    public DateOperation(String dateString) throws ParseException {
        String []dateArray = dateString.split("/");
        day = convert(dateArray[0]);
        month = convert(dateArray[1]);
        year = convert(dateArray[2]);
    }

    // convert string date item to number
    public static int convert(String key) {
        if(key.charAt(0) == '0') {
            return Character.getNumericValue(key.charAt(1));
        }else return Integer.parseInt(key);
    }

    // test date format 'dd/MM/yyyy'
    public static boolean testFormat(String dateString) {
        if(dateString.length() != 10) return false;
        int day = convert(dateString.substring(0,2));
        int month = convert(dateString.substring(3,5));
        int year = convert(dateString.substring(6));
        if(day > 30 || day < 1) return false;
        if(month > 12 || month < 1) return false;
        if(year < 2022) return false;
        return true;
    }

    // convert this date to string
    public String toString() {
        String ds,ms;
        if(day < 10) ds = "0" + day;
        else ds = day + "";
        if(month < 10) ms = "0" + month;
        else ms = month + "";
        return ds + "/" + ms + "/" + year;
    }

    // return date string after adding days
    public String add(int day) {
        if(this.day + day > 30) {
            this.day = this.day + day - 30;
            if(month == 12) {
                month = 1;
                year += 1;
            } else month += 1;
        } else this.day += day;
        return toString();
    }

    // compare this with date string
    public int compare(String dateString) throws ParseException {
        DateOperation x = new DateOperation(dateString);
        if(x.getYear() > year) return -1;
        if(x.getYear() < year) return 1;
        if(x.getMonth() > month) return -1;
        if(x.getMonth() < month) return 1;
        if(x.getDay() > day) return -1;
        if(x.getDay() < day) return 1;
        return 0;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
