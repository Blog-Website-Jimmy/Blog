package Blog.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
public class TimeIntegration {
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime deletedAt;
}
