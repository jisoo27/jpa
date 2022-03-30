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
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);
            // 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
            // 이때 쿼리를 보내게 될 경우 최적화 할 수 있는 기회조차 없다.
            System.out.println("===============");

            //커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
            tx.commit(); // [트랜잭션] 커밋
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}