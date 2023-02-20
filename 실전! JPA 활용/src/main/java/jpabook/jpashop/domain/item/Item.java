package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 싱글테이블이기 때문에 각각 타입을 알아볼 수 있게 해준다.
@Getter @Setter
public abstract class Item { // 상속관계 전략을 부모테이블에 지정해주어야 한다.

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //===핵심 비즈니스 로직==//
    // 도메인 주도 설계에서 엔티티 자체에서 해결할 수 있는 것들이있으면
    // 엔티티 안에 비즈니스 로직을 넣는 것이 좋다.(객체지향적)
    // setter 로 값 변경을 하는 것이 아닌 이러한 핵심 비지니스 메서드를 가지고 변경해야 한다.

    public void addStock(int quantity) { // 재고 수량 증가
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) { // 재고 수량 감소
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
