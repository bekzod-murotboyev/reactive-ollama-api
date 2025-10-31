package uz.keepsolution.reactiveollamaapi.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AIUtils {


    public static final String CHAT_CLIENT_PROMPT_RULE = """
            
            Rules: \
            1. You will receive the user's question as a text; \
            2. Answer directly and concisely. Do NOT greet. Do NOT ask what they need; \
            3. Just answer the question in 1–3 sentences. If the text is unclear, say what you need to clarify;""";
}