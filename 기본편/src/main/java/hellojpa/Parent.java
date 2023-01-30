package hellojpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Parent {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>(); // parent 가 Persist 될 때 이 컬렉션에 있는 모든 child 또한 자동으로 persist 되도록 해주는 옵션 CascadeType.ALL

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }
}
