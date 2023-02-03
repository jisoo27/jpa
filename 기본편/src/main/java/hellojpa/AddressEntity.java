package hellojpa;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ADDRESS")
public class AddressEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Address address; // 값 타입으로 한번 래핑

    public AddressEntity(String city, String street, String zipCode) {
        this.address = new Address(city, street, zipCode);
    }
}
