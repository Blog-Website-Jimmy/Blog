package BlogBackned.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity extends TimeIntegration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String token;
}
