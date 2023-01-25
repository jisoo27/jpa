package hellojpa;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 로딩 시점에 하나만 생성해야 한다.

        EntityManager em = emf.createEntityManager(); // 일괄적인 단위마다 생성해주어야 한다. 마치 자바 컬렉션 이해하자. 객체를 대신 저장해주는 놈

        EntityTransaction tx = em.getTransaction(); //JPA 에서 데이터를 변경하는 모든 작업은 꼭 transation에서 작업을 해야한다.
        tx.begin();

        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // em.persist 하면 무조건 pk가 세팅 된다

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            Team findTeam = findMember.getTeam();

            System.out.println("findTeam.getName() = " + findTeam.getName());
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
