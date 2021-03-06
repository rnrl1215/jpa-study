package mapping.fullduplexmapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class FullDuplexMappingEx {
    public static void main(String[] args) {
        /*
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
            FullDuplexTeam fullDuplexTeam = new FullDuplexTeam();
            fullDuplexTeam.setName("TeamA");
            em.persist(fullDuplexTeam);

            // 수정코드
            FullDuplexMember member = new FullDuplexMember();
            member.setUsername("member1");
            member.changeTeam(fullDuplexTeam);
            em.persist(member);

            // 이걸 안해줘도
            // 아래 for 문에서 값이 출력이 된다.
            // 쿼리가 하번 더 돈다
            // LAZY 효과
            // 하지만 안넣어 주면 문제가 발생한다.
            // flus, clear 를 해주면 문제는 없다.
            fullDuplexTeam.getFullDuplexMembers().add(member);

            // select 쿼리를 보고 싶을때
            em.flush(); // 영속성에있는 데이터를 DB 에 저장
            em.clear(); // 영속성 컨텍스트 모두 클리어

            // 조회시 수정코드
            // 쿼리문 한번 발생
            FullDuplexMember findMember = em.find(FullDuplexMember.class, member.getId());
            List<FullDuplexMember> fullDuplexMembers = findMember.getTeam().getFullDuplexMembers();

            for(FullDuplexMember m : fullDuplexMembers)
            {
                // 조회시 쿼리문 발생한다.
                System.out.println("m: " + m.getUsername());
            }

            //tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

         */
    }
}
