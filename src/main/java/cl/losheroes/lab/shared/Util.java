package cl.losheroes.lab.shared;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Util {

    public static final DateTimeFormatter formatDateTimeISO = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter formatTimeISO = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter formatDateISO = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static String trimBrackets(String message) {
        return message.replaceAll("[\\[\\]]", "");
    }

    public static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        if (i >= minValueInclusive && i <= maxValueInclusive)
            return true;
        else
            return false;
    }

    public static boolean isValidDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(strDate.trim());
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            //throw new IllegalStateException(e);
            log.error("[x] utils method sleep.. {}", e.getMessage());
        }
    }

}
