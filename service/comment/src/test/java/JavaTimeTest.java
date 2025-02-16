import java.time.Duration;
import java.time.Period;

public class JavaTimeTest
{
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(Period.parse(Duration.ofDays(3).toString()).getDays());
    }
}
