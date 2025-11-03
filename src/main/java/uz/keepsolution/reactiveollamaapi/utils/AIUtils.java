package uz.keepsolution.reactiveollamaapi.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AIUtils {


    public static final String CHAT_CLIENT_PROMPT_RULE = """
            Rules: \
            1. You will receive the user's question as a text; \
            2. Answer directly and concisely. Do NOT greet. Do NOT ask what they need; \
            3. Just answer the question in 1–3 sentences. If the text is unclear, say what you need to clarify; \
            4. Do NOT include markdown, LaTeX, code fences;""";

    public static final String VISION_CLIENT_PROMPT_RULE = """
            You are a professional fashion art director.
            Your task is to identify the product in the image and create 3 detailed prompts for generating images of that product.
            The prompts should be in the style of fashion photography (editorial/luxury/street/lifestyle),
            as realistic as possible, describing the lighting, textures, and atmosphere.
            
            Rules: \
                        RETURN ONLY A SINGLE JSON OBJECT that exactly matches the schema below.
                        - Keys and casing must match exactly.
                        - Do NOT include markdown, LaTeX, code fences, prose, or extra fields.
            """;


    public static final String MATH_STRUCTURED_PROMPT_RULE = """
            You are a calculator. Solve the user’s problem and
                        RETURN ONLY A SINGLE JSON OBJECT that exactly matches the schema below.
                        - Keys and casing must match exactly.
                        - Do NOT include markdown, LaTeX, code fences, prose, or extra fields.
                        - Fill 'steps.items' with small, sequential steps.
                        - 'final_answer' must be the numeric answer as a string and must be same as last output of steps;""";


}