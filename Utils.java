import java.util.concurrent.TimeUnit;
public class Utils {
    // Sleep for "num" seconds
    public static int sleep(int num) throws InterruptedException {
        TimeUnit.SECONDS.sleep(num);
        return 0;
    }
}

