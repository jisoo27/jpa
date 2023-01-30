package hellojpa;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("hello");
            
            em.persist(member);
            
            em.flush();
            em.clear();
            
            
//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId()); // 가짜 프록시 객체 조회
            System.out.println("findMember.getName() = " + findMember.getName()); // 내부적으로 영속성 컨텍스트에 요청을 하여 내부에 실제 target에 대한 값을 알게 되면서 getName의 값을 조회할 수 있다.


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
