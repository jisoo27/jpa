package hellojpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR",
sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
initialValue = 1, allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;// Pk가 뭔지 알려줘야한다.

    @Column(name = "name") // 데이터베이스 컬럼명을 설정해 줄 수 있다.
    private String name;

}
