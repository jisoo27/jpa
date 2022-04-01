package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속상태가 되면 무조건 pk 값이 세팅됨

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team); // 단방향 연관관계 설정, 참조 저장
            em.persist(member);

            em.flush();
            em.clear(); // 이 2개의 과정을 거쳐줘야 DB에서 깔끔한 값을 가져온다.

            // 조회
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getName());
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