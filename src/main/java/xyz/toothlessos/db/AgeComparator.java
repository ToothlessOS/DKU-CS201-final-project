package xyz.toothlessos.db;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class AgeComparator implements Comparator<PeopleRecord>, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public int compare(PeopleRecord p1, PeopleRecord p2) {
        // We want to parse the birthdays stored in String first
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date p1_birthday;
        Date p2_birthday;
        try {
            p1_birthday = formatter.parse(p1.getBirthday());
            p2_birthday = formatter.parse(p2.getBirthday());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return p1_birthday.compareTo(p2_birthday);
    }
}
