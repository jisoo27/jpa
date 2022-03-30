package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // 로딩될 때 jpa를 사용하는 애구나 인식 -> 내가 관리해야겠다 고 인식
public class Member {
    @Id
    private Long id;
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
