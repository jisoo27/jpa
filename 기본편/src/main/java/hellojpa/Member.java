package hellojpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;


//    @Column(name = "TEAM_ID")
//    private Long teamId;

}
