package examples;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerExample {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);


        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TimerDevice device = new TimerDevice();
                try {
                    device.run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                TimerClient client = new TimerClient();
                client.run();
            }
        });

    }
}
