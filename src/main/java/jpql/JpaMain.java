package jpql;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Team newCastle = new Team();
            newCastle.setName("뉴캐슬");
            em.persist(newCastle);

            Team chelsea = new Team();
            chelsea.setName("첼시");
            em.persist(chelsea);

            Member member = new Member();
            member.setUsername("존조셸비");
            member.setAge(20);
            member.setTeam(newCastle);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("칼럼윌슨");
            member2.setAge(18);
            member2.setTeam(newCastle);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("베르너");
            member3.setAge(30);
            member3.setTeam(chelsea);
            em.persist(member3);


            // inner join
            String sql = "select m from Member m inner join Team t on m.team.id = t.id where t.name = '뉴캐슬'";
            List<Member> resultList = em.createQuery(sql , Member.class)
                    .getResultList();

            for (Member findMember : resultList) {
                System.out.println("findMember >> " + findMember);
            }

            System.out.println("-------------------------------------------------------");

            // left join
            String sql2 = "select m from Member m left join Team t on m.team.id = t.id";
            List<Member> resultList2 = em.createQuery(sql2, Member.class)
                    .getResultList();

            for (Member findMember : resultList2) {
                System.out.println("findMember >> " + findMember);
            }

            System.out.println("-------------------------------------------------------");

            // theta join
            String sql3 = "select m from Member m , Team t where m.team.id = t.id";
            List<Member> resultList3 = em.createQuery(sql3, Member.class)
                    .getResultList();

            for (Member findMember : resultList3) {
                System.out.println("findMember >> " + findMember);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}

