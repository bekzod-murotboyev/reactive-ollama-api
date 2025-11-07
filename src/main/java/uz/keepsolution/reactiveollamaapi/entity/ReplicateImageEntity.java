package uz.keepsolution.reactiveollamaapi.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("_replicate_image")
public class ReplicateImageEntity implements Persistable<String> {
    @Id
    private String id;
    private String prompt;
    @Column("content_url")
    private String contentUrl;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("completed_at")
    private LocalDateTime completedAt;

    @Transient
    private boolean isNew = false;

    public ReplicateImageEntity insertable() {
        this.isNew = true;
        return this;
    }
}
