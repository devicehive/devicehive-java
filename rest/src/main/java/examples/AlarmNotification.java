package examples;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AlarmNotification {
    @SerializedName("message")
    private String message;
}
