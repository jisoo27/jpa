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
        tx.begin();

        try {
            Member member = new Member(200L, "member200");
            em.persist(member); // 이 때 영속성 컨텍스트에 member가 담기고 , 쿼리가 쓰기 지연 SQL저장소에 담겨있다.

            em.flush(); // DB에 insert 쿼리가 이 시점에 바로 날아간다. => DB에 반영
            
            // Q. flush를 할 경우 1차캐시가 다 지워지나요?
            // => 아니요! 1차캐쉬는 다 유지가 됩니다.

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