package lazy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class lazyEx {
    public static void main(String[] args) {

        // persistence.xml 의 persistence-unit name="hello" 를 넘긴다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 매니저를 꺼내온다.
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 얻어오고 실행한다.
        // JPA 는 트랜잭션 안에서 수행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // exception 처리를 위해 try catch 문을 반드시 사용해야된다.
        try {
            System.out.println("====================================");
            Member9 member = new Member9("Member");
            em.persist(member);

            Team9 team9 = new Team9();
            team9.setName("Team");
            em.persist(team9);

            member.setTeam(team9);

            em.flush();
            em.clear();

            // 프록시로 가져온다.
            Member9 member9 = em.find(Member9.class,member.getId());

            // 팀에 대한 쿼리는 나가지 않는다 지연 로딩 때문에
            System.out.println("member9"+member9.getTeam().getClass());

            // 여기서 팀에대한 쿼리가 나간다.
            //System.out.println("member9"+member9.getTeam().getName());

            System.out.println("====================================");

            // 커밋시 insert sql을 보낸다.
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
