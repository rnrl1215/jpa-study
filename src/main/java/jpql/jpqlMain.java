package jpql;



import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class jpqlMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // JPQL 예제
/*
            String qlString = "select m from Member m where m.username like '%kim%'";
            List<Member> result = em.createQuery(qlString,
                            Member.class).getResultList();

            for (Member member : result) {
                System.out.println("member =" + member);
            }
*/

            // Criteria 예제
            // 단순한 예제는 쉽지만 복잡한 쿼리에서는 힘들다.
            // 장점 동적쿼리 및 문법오류를 줄일수 있다.
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class); // 멤버에 대한쿼리를 한다.
            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq =  query.select(m).where(cb.equal(m.get("username"),"kim")); // equal 로 비교
            List<Member> resultList = em.createQuery(cq).getResultList();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
