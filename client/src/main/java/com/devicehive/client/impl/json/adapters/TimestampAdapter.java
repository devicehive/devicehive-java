package com.devicehive.client.impl.json.adapters;


import com.devicehive.client.impl.util.Messages;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Adapter for conversion timestamp into JSON string with format "yyyy-MM-dd'T'HH:mm:ss" and JSON into timestamp
 */
public class TimestampAdapter extends TypeAdapter<Timestamp> {


    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").withZoneUTC();

    public static Timestamp parseTimestamp(String input) throws IllegalArgumentException {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        input = input.trim();
        int pos = input.indexOf(".");
        String dateSeconds = pos >= 0 ? input.substring(0, pos) : input;

        Timestamp timestamp = new Timestamp(FORMATTER.parseMillis(dateSeconds));

        int microseconds = 0;
        if (pos >= 0) {
            String micro = input.substring(pos + 1);
            if (micro.isEmpty() || micro.length() > 6) {
                throw new IllegalArgumentException(Messages.PARSING_MICROSECONDS_ERROR);
            }
            micro += "000000".substring(0, 6 - micro.length());
            try {
                microseconds = Integer.parseInt(micro);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(Messages.PARSING_MICROSECONDS_ERROR, ex);
            }
        }

        timestamp.setNanos(microseconds * 1000);
        return timestamp;
    }

    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        StringBuilder res = new StringBuilder(FORMATTER.print(new DateTime(timestamp.getTime())))
                .append(".");
        int microseconds = timestamp.getNanos() / 1000;

        if (microseconds == 0) {
            return res.append("0").toString();
        }

        String micro = Integer.toString(microseconds);

        micro = "000000".substring(0, 6 - micro.length()) + micro;

        int index = 5;
        while (micro.charAt(index) == '0') {
            index--;
        }
        return res.append(micro.substring(0, index + 1)).toString();
    }

    @Override
    public void write(JsonWriter out, Timestamp timestamp) throws IOException {
        if (timestamp == null) {
            out.nullValue();
        } else {
            out.value(formatTimestamp(timestamp));
        }
    }

    @Override
    public Timestamp read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();
        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            String timestampString = in.nextString();
            try {
                return parseTimestamp(timestampString);
            } catch (RuntimeException e) {
                throw new IOException(String.format(Messages.INCORRECT_TIMESTAMP_FORMAT, timestampString), e);
            }
        }
    }
}
