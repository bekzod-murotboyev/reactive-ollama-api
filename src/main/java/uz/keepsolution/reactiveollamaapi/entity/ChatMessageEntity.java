package uz.keepsolution.reactiveollamaapi.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Table("_chat_message")
public class ChatMessageEntity {
    @Id
    private Long id;
    private String question;
    private String response;
    @Column("created_at")
    private LocalDateTime createdAt;

    public ChatMessageEntity(String question, String response) {
        this.question = question;
        this.response = response;
        this.createdAt = LocalDateTime.now();
    }
}
