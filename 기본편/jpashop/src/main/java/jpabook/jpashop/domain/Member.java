package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter // 예제이기 때문에
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>(); // 비지니스 적으로 꼭 필요한지 생각해봐야한다.

    private String name;

    private String city;

    private String street;

    private String zipcode;


}
