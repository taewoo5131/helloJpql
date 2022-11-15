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

            makeData(em);

            List<Member> select_m_from_member_m = em.createQuery("select m from Member m" +
                    " WHERE m.memberType = jpql.MemberType.PLAYER", Member.class).getResultList();
            for (Member member : select_m_from_member_m) {
                System.out.println(member.toString());
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

    private static void makeData(EntityManager em) {
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
        member.setMemberType(MemberType.PLAYER);
        em.persist(member);

        Member member2 = new Member();
        member2.setUsername("칼럼윌슨");
        member2.setAge(18);
        member2.setTeam(newCastle);
        member2.setMemberType(MemberType.PLAYER);
        em.persist(member2);

        Member member3 = new Member();
        member3.setUsername("베르너");
        member3.setAge(30);
        member3.setTeam(chelsea);
        member3.setMemberType(MemberType.PLAYER);
        em.persist(member3);

        Member member4 = new Member();
        member4.setUsername("지단");
        member4.setAge(50);
        member4.setTeam(chelsea);
        member4.setMemberType(MemberType.COACH);
        em.persist(member4);
    }
}

