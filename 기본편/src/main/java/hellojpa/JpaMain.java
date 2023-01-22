package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 로딩 시점에 하나만 생성해야 한다.

        EntityManager em = emf.createEntityManager(); // 일괄적인 단위마다 생성해주어야 한다. 마치 자바 컬렉션 이해하자. 객체를 대신 저장해주는 놈

        EntityTransaction tx = em.getTransaction(); //JPA 에서 데이터를 변경하는 모든 작업은 꼭 transation에서 작업을 해야한다.
        tx.begin();

        try {
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5) // 5~10 을 가져와라 -> 페이지네이션
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member.getName() = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
