package cascade;

import lazy.Member9;
import lazy.Team9;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CascadeEx {
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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            // 저장시 persist 를 3번 호출해야 저장이된다.
            //parent 를 persist를 할 경우
            // 같이 저장 했으면 좋겠다
            em.persist(parent);
            //em.persist(child1);
            //em.persist(child2);

            em.flush();
            em.clear();

            // 고아객체 테스트
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

            // parent 삭제시 같이 지워진다.
            // parent 가 지워지기 때문에
            // 컬렉션도 같이 날아간다.
            em.remove(findParent);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
