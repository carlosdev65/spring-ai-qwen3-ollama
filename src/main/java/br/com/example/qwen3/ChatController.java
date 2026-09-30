package br.com.example.qwen3;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ChatController {

    private final ChatModel chatModel;

    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String, String> generate(
            @RequestParam(defaultValue = "Por que o céu é azul. Responsa em português") String message) {

        String answer = chatModel.call(message);

        return Map.of("generation", answer);
    }
}
