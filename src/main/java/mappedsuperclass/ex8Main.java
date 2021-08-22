package mappedsuperclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ex8Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {

            Locker8 locker = new Locker8();
            locker.setName("mylocker");
            em.persist(locker);

            Member8 member = new Member8();
            member.setName("test");
            member.setCreateBy("skahn");
            member.setCreateDate(LocalDateTime.now());
            member.setLocker(locker);
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}

