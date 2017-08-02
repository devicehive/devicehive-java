package examples;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TimeStamp {
    @SerializedName("timestamp")
    private String timestamp;

    @Override
    public String toString() {
        return "{\n\"TimeStamp\":{\n"
                + "\"timestamp\":\"" + timestamp + "\""
                + "}\n}";
    }
}
