package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    // EAGER 일 경우 order를 조회할 때 member를 join해서 한번에 가져오는 경우가 있는데 => em.find()해서 한건 조회할 경우
    // JPQL 로 select o From order o; 로 가져오게 되면 그대로 sql로 번역하게 된다. -> SQL select * from order
    // 그럼 order가 100개가 있다면 100개를 다 가져오게 되고 member가 EAGER로 되어 있다면 100개가 100번 member를 가져오기 위해 단방쿼리가 100개 날아간다.
    // 이것이 n+1(처음에 order를 가져오기 위한 쿼리) 문제이다. => 첫번째 날린 쿼리가 가져온 결과가 100개이면 n을 100으로 치환하면 된다.

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate; // 따로 매핑하지 않아도 하이버네이트가 자동으로 해준다.

    @OneToOne // oneToOne 일 경우 fk 를 어디다 두냐가 고민이지만 access 를 많이하는 쪽에 두는것이 좋다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
