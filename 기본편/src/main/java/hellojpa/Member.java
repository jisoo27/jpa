package hellojpa;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    private Long id;// Pk가 뭔지 알려줘야한다.
    private String name;
}
