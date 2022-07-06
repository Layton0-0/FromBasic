package triple.mile.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"userId"})
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    public User(UUID userId) {
        this.userId = userId;
    }
}
