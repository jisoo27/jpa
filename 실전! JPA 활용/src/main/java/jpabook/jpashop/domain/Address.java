package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address { // Value Object : 하나의 타입이라고 생각하면 된다.

    private String city;
    private String street;
    private String zipcode;
}
