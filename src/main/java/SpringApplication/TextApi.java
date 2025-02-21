package SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class TextApi {

    private final MessageSource messageSource;

    @Autowired
    public TextApi(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // Endpoint to get a localized message
    @GetMapping("/hello")
    public String sayHello(@RequestParam String name) {
        return messageSource.getMessage("welcome", new Object[]{name}, Locale.ENGLISH);
    }
}
