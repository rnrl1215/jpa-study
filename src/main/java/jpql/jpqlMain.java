package jpql;

import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            // 실무에서는 사용하지 않는다.
            // 단순한 예제는 쉽지만 복잡한 쿼리에서는 힘들다.
            // 장점 동적쿼리 및 문법오류를 줄일수 있다.

            /*
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class); // 멤버에 대한쿼리를 한다.
            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq =  query.select(m);

            // 아래 처럼 동적 쿼리를 짤수 있다.
            // 사용자 이름 여부에대해서 동적쿼리를 다음과같이 작성함.
            String username = "kim";
            if (username != null) {
                cq.where().where(cb.equal(m.get("username"),"kim")); // equal 로 비교
            }

            List<Member> resultList = em.createQuery(cq).getResultList();
            */

            System.out.println("TEST");

            // Native SQL
            Member member = new Member();
            member.setUserName("member1");
            em.persist(member);
            em.flush();
            // flush 는 다음 과정에서 발생한다.
            // commit 할 경우와 query가 날라가는 경우이다.


            // Member 로 할 경우 모든 필드가 다 들어가야한다.
            List <Member> resultList = em.createNativeQuery("select MEMBER_ID, USER_NAME from Member",Member.class)
                    .getResultList();

            for(Member JPQLMember1 : resultList) {
                System.out.println(JPQLMember1.getUserName());
            }

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
