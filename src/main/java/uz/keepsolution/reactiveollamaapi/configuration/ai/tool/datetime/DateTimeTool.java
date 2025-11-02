package uz.keepsolution.reactiveollamaapi.configuration.ai.tool.datetime;

import java.time.LocalDateTime;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DateTimeTool {

    public static final String CHAT_HISTORY_DESCRIPTION = "Get the current date and time in the user's timezone";

    @Tool(description = CHAT_HISTORY_DESCRIPTION)
    String getCurrentDateTime() {
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }
}
