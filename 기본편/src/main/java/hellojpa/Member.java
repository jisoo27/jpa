package hellojpa;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Pk가 뭔지 알려줘야한다.

    @Column(name = "name") // 데이터베이스 컬럼명을 설정해 줄 수 있다.
    private String name;

}
