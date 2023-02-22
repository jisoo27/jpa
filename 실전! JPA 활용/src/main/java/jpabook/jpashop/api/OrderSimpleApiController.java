package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/* xToOne(ManyToOne, OneToOne) 의 관계에서는 성능 최적화를 어떻게 해야할까?
* Order
* Order -> Member
* Order -> Delivery
* */


@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            //order.getMember().getName(); Lazy 강제 초기화
            //order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        // orderRepository 에서 꺼내온 order 2개
        // N + 1 문제 발생 -> 1 + N(2) : 첫번째 쿼리의 결과로 N번 만큼 추가 실행된다.
        // 첫번째 orders 를 가져오기 위한 쿼리 1개 + 회원 N + 배송 N
        // 너무 많은 쿼리가 발생.
        return orderRepository.findAll(new OrderSearch()).stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
    }

    @Data
    static class SimpleOrderDto { // api 스펙을 명확하게 규정해야 한다.
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) { // DTO 가 Entity 를 파라미터로 받는 것은 크게 문제가 되지 않는다. 왜냐하면 별로 중요하지 않은 곳에서 중요하지 않은 엔티티를 의존하는 것이기 떄문
            orderId = order.getId();
            name = order.getMember().getName(); // LAZY 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }


}
