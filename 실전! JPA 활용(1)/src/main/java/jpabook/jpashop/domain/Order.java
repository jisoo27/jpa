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

    // == 연관관계 편의 메서드 <양방향일 경우 사용>(양쪽 세팅을 위한 것.이 메서드 하나로 완료)
    // 이 편의 메서드의 위치는 양쪽 중 핵심적으로 controller 하는 쪽이 들고 있는 것이 좋다.
    public void setMember(Member meber) {
        this.member = meber;
        meber.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


}
