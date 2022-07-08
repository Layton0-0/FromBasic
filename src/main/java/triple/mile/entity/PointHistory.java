package triple.mile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(indexes = @Index(name = "i_point", columnList = "createDate", unique = true))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    private int pointValue;

    // 포인트 변동 분야(REVIEW)
    private String pointSection;

    // 포인트 변동된 분야 아이디
    @Column(columnDefinition = "BINARY(16)")
    private UUID sectionId;

    // 왜 포인트 변동이 일어났는지
    private String pointType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public PointHistory(int pointValue, String pointSection, UUID sectionId, String pointType, User user) {
        this.pointValue = pointValue;
        this.pointSection = pointSection;
        this.sectionId = sectionId;
        this.pointType = pointType;
        this.user = user;
    }
}
