package mappingstrategy.joinstrategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class joinstartegyEx {
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
            Movie7 movie = new Movie7();
            movie.setName("셜록");
            movie.setDirector("누구지?");
            movie.setActor("배네딕트컴버배치");
            movie.setPrice(10000);

            em.persist(movie);
            em.flush();
            em.clear();

            Movie7 findMovie = em.find(Movie7.class, movie.getId());
            System.out.println(findMovie.getActor());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
