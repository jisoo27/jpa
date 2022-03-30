package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        // 엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
        tx.begin(); // [트랜잭션] 시작

        try {
            Member member = em.find(Member.class, 150L); // 찾아온다음
            member.setName("zzzzz"); // 수정

            //em.persist(member); JPA 목적은 자바 collection 다루듯이 객체를 다루는 것 인데 생각해보면 List에서 값을 꺼내고 값을 변경하고 다시 집어넣는가? X

            System.out.println("=============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}