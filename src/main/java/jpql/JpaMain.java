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

            Member member = new Member();
            member.setUsername("taewoo");
            member.setAge(20);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("jihye");
            member2.setAge(18);
            em.persist(member2);

            em.flush();
            em.clear();

//            TypedQuery<Member> result = em.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = result.getResultList();
//
//            TypedQuery<Member> result2 = em.createQuery("select m from Member m", Member.class);
//            Member singleResult = result2.getSingleResult();

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username =:username", Member.class);
            query.setParameter("username","taewoo");
            Member singleResult1 = query.getSingleResult();
            TypedQuery<Member> query1 =
                    em.createQuery("select m from Member m where m.username =?1 and m.age =?2", Member.class);
            query1.setParameter(1, "jihye");
            query1.setParameter(2, 18);
            Member singleResult2 = query1.getSingleResult();
            System.out.println(singleResult1.getUsername());
            System.out.println(singleResult2.getUsername());


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

