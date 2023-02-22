package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/* xToOne(ManyToOne, OneToOne) 의 관계에서는 성능 최적화를 어떻게 해야할까?
* Order
* Order -> Member
* Order -> Delivery
* */

// 응답의 경우 List로 응답하지말고 response로 한번 감싸서 내보내야 한다.


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
    public List<OrderSimpleQueryDto> ordersV2() {
        // orderRepository 에서 꺼내온 order 2개
        // N + 1 문제 발생 -> 1 + N(2) : 첫번째 쿼리의 결과로 N번 만큼 추가 실행된다.
        // 첫번째 orders 를 가져오기 위한 쿼리 1개 + 회원 N + 배송 N
        // 너무 많은 쿼리가 발생.
        return orderRepository.findAll(new OrderSearch()).stream()
                .map(order -> new OrderSimpleQueryDto(order))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
        .map(order -> new OrderSimpleQueryDto(order))
        .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }


}
