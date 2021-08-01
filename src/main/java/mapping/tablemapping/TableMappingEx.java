package mapping.tablemapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TableMappingEx {
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
            // code 작성
            // 저장로직
            /*
            Member member = new Member(1L," HELLO");
             */

            // 찾기로직
            /*
            Member findMember = em.find(Member.class, 1L);
            System.out.println("memberId: " + findMember.getId());
            System.out.println("memberName: "+findMember.getName());
            */

            // 삭제로직
            /*
            Member findMember = em.find(Member.class, 1L);
            em.remove(findMember);
            */

            // 수정로직
            // 수정 로직은 영속성 객체를 가져왔기 때문에
            // 따로 em을 이용하지 않고 변경 가능하다.
            // commit 직전에 update 쿼리를 생성후
            // commit 한다.
            /*
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("Hi");
            */

            // JPQL 사용
            // 복잡한 쿼리 작성시.
            // 대상은 객체가 된다.
            /*
            List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                    .getResultList();
            for(Member  member : resultList) {
                System.out.println("memberId: " + member.getId());
                System.out.println("memberName: " + member.getName());
            }
            */


            // 비영속 객체 JPA가 관리 하지 않음
            TableMember tableMember = new TableMember(150L,"A");


            // 영속 컨텍스트에 넣음.
            // 객체는 이제부터 영속 객체가 됨.
            // 여기서는 DB에 쿼리가 날라가지 않는다.
            em.persist(tableMember);
            TableMember findTableMember1 = em.find(TableMember.class, 101L);
            System.out.println("findMember.id =" + findTableMember1.getId());
            System.out.println("findMember.name =" + findTableMember1.getName());


            // 1차 캐시에서 조회 했기 때문에 주소가 같다
            // 동일성 비교를 가능하게 해준다.
            TableMember findTableMember2 = em.find(TableMember.class, 101L);
            TableMember findTableMember3 = em.find(TableMember.class, 101L);

            if (findTableMember2 == findTableMember3) {
                System.out.println("findMember2 == findMember3");
            }


            // 영속성 컨테스트에서 삭제
            // 준영속성이 됨.
            //em.detach(member);

            // 객체를 삭제한 상태
            //em.remove(member);

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
