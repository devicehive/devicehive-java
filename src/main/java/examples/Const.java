package examples;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Const {


    //    public static final String URL = "http://192.168.168.183/dh/rest";
    public static final String URL = "http://playground.dev.devicehive.com/api/rest/";
    //Auth
    public static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJwYXlsb2FkIjp7InVzZXJJZCI6MSwiYWN0aW9ucyI6WyIqIl0sIm5ldHdvcmtJZHMiOlsiKiJdLCJkZXZpY2VJZHMiOlsiKiJdLCJleHBpcmF0aW9uIjoxNTAwNjI4MzgxNjQ5LCJ0b2tlblR5cGUiOiJBQ0NFU1MifX0.tz4-QETIQTQbIE3k4-ZrR7SukboMwPNZfNJaVG3705M";
    public static final String LOGIN = "dhadmin";
    public static final String PASSWORD = "dhadmin_#911";

    //Device
    public static final String DEVICE_ID = "3d77f31c-bddd-443b-b11c-640946b0581z4123tzxc3";
    public static final String NAME = "TIMER";
    public static final String STATUS = "OFFLINE";
    public static final String DC_NAME = "TIMER";
    public static final String DC_VERSION = "1.0";

    //Command
    public static final String LED_COMMAND = "LED";
    public static final String LED_STATE = "state";

    public static final String ALARM = "ALARM";
    public static final String ON = "ON";
    public static final String OFF = "OFF";


    public static final java.lang.String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final java.lang.String ALARM_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Application name
     */
    public static final String APP_NAME = "Examples set";

    /**
     * If unknown command found or command was used incorrect this message displayed.
     */
    public static final String PARSE_EXCEPTION_MESSAGE = "Unknown command!";

    /**
     * This command identifies that the impl protocol should be preferred
     */
    public static final String USE_SOCKETS = "useSockets";

    /**
     * Description of useSockets command
     */
    public static final String USE_SOCKETS_DESCRIPTION = "If used then prefer the impl protocol";

    /**
     * Description of url option.
     */
    public static final String URL_DESCRIPTION = "REST service url";

    /**
     * This command line parameter stands for server URL.
     */
//    public static final String URL = "http://playground.devicehive.com/api/rest";
    public static String formatTimestamp(DateTime timestamp, String format) {
        return new SimpleDateFormat(format).format(timestamp);
    }

    public static Date getDateFromString(String timestamp) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return sdf.parse(timestamp);
    }


}
