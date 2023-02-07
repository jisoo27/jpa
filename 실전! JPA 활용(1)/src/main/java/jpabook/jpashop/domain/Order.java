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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade 는 Persist 를 전파
    private List<OrderItem> orderItems = new ArrayList<>();
    // orderItems에 데이터만 넣어두고 Order를 저장하면 orderItems 도 같이 저장된다.

    private LocalDateTime orderDate; // 따로 매핑하지 않아도 하이버네이트가 자동으로 해준다.

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // oneToOne 일 경우 fk 를 어디다 두냐가 고민이지만 access 를 많이하는 쪽에 두는것이 좋다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
