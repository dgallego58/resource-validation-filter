package co.com.bancolombia;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;

@Slf4j
public class SolidExample {


    public static void main(String[] args) {
        var user = new User("David", "+57314", "david@example.com");
        String message = "Test message";

        ServiceNotification serviceNotification = ServiceNotification.create();
        serviceNotification.sendEmail(message, user.email());
        serviceNotification.sendSns(message, user.phoneNumber());

    }
}


record User(String name, String phoneNumber, String email) {
}

@Slf4j
@NoArgsConstructor(staticName = "create")
class ServiceNotification {
    private final BiConsumer<String, String> sendEmailNotification = (message, email) -> log.info(
            "Message: {}\nSent to: {}",
            message, email);

    private final BiConsumer<String, String> sendSnsNotification = (message, phoneNumber) -> log.info(
            "Message: {}\nSent to SNS: {}",
            message, phoneNumber);

    public void sendEmail(String message, String email) {
        sendEmailNotification.accept(message, email);
    }

    public void sendSns(String message, String phone) {
        sendSnsNotification.accept(message, phone);
    }
}
