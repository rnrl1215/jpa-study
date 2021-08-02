package simplexmapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SimplexMappingEx {
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
            SimplexTeam simplexTeam = new SimplexTeam();
            simplexTeam.setName("TeamA");
            em.persist(simplexTeam);


            // 문제1
            // setTeamId 는 객체지향 스럽지가 않다.
            // setTeam으로 객체를 받아야 객체 지향 스럽다.
            //RelationMember member = new RelationMember();
            //member.setUsername("member1");
            ///member.setTeamId(team.getId());
            //em.persist(member);

            // 문제2
            // 조회시 가져오는 과정을 두번 거쳐야 된다.
            // memeber에서 teamId 후 entityManager 에서 Team 을 가져 와야한다.
            //RelationMember findMember = em.find(RelationMember.class, member.getId());
            //Team findTeam = em.find(Team.class, findMember.getTeamId());

            // 수정코드
            SimplexMember member = new SimplexMember();
            member.setUsername("member1");
            member.setTeam(simplexTeam);
            em.persist(member);

            // select 쿼리를 보고 싶을때
            em.flush(); // 영속성에있는 데이터를 DB 에 저장
            em.clear(); // 영속성 컨텍스트 모두 클리어
                        // 그래야 아래 에서 셀렉트 쿼리가 나간다.

            // 조회시 수정코드
            SimplexMember findMember = em.find(SimplexMember.class, member.getId());
            SimplexTeam findSimplexTeam = findMember.getTeam();
            System.out.println("username: "+findMember.getUsername());

            // 수정
            // 변경만 하면 DB 값이 변경된다.
            SimplexTeam newSimplexTeam = em.find(SimplexTeam.class, 100L);
            findMember.setTeam(newSimplexTeam);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
