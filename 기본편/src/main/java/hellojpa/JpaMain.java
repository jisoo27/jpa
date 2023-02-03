package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setName("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("회원3");
            member3.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

            String query = "select m From Member m join fetch m.team";

            List<Member> result = em.createQuery(query, Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member = " + member.getName() + ", " + "teamName = " + member.getTeam().getName());
            }

            // 회원1, 팀A(SQL)
            // 회원2, 팀A(1차캐시)
            // 회원3, SQl
            // ...
            // 회원 100명 -> N + 1 : 회원을 가져오기 위해 날린 쿼리 1번 =>  100번 돈다
            // 패치조인으로 해결해야 한다.


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
