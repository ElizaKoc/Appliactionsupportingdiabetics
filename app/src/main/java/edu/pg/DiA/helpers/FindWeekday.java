package edu.pg.DiA.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FindWeekday {

    public String findWeekday(String selectedDate) throws ParseException {
        String weekday = "";
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case(1):
                weekday = "niedziela";
                break;
            case(2):
                weekday = "poniedziałek";
                break;
            case(3):
                weekday = "wtorek";
                break;
            case(4):
                weekday = "środa";
                break;
            case(5):
                weekday = "czwartek";
                break;
            case(6):
                weekday = "piątek";
                break;
            case(7):
                weekday = "sobota";
                break;
            default:
                break;
        }
        return weekday;
    }

    public int findWeekdayId(String weekday) {
        int weekdayId = 0;

        switch (weekday) {
            case("niedziela"):
                weekdayId = 1;
                break;
            case("poniedziałek"):
                weekdayId = 2;
                break;
            case("wtorek"):
                weekdayId = 3;
                break;
            case("środa"):
                weekdayId = 4;
                break;
            case("czwartek"):
                weekdayId = 5;
                break;
            case("piątek"):
                weekdayId = 6;
                break;
            case("sobota"):
                weekdayId = 7;
                break;
            default:
                break;
        }
        return weekdayId;
    }
}
