package jpql;

import javax.persistence.*;
import java.time.temporal.TemporalAmount;
import java.util.List;


public class jpqlMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            JPQLTeam team = new JPQLTeam();
            team.setName("teamA");
            em.persist(team);

            JPQLMember member = new JPQLMember();
            member.setUsername("teamA");
            member.setAge(10);
            member.setTeam(team);
            member.setType(MemberType.ADMIN);

            em.persist(member);
            em.flush();

            // 쿼리로 불러오면 영속성 컨텍스트에 들어 갈까?
            //  엔티티 프로젝션
            List <JPQLMember> entityResult1 = em.createNativeQuery("select MEMBER_ID, USER_NAME from JPQLMember", JPQLMember.class)
                    .getResultList();

            // 관리가 됨.
            JPQLMember findMember = entityResult1.get(0);
            findMember.setUsername("member2");

            // 엔티티 프로젝션
            // 팀 불러오기
            // 알아보기 쉽게 join을 직접 써주자. 예측을 할 수 있다.
            List<JPQLTeam> entityResult2 = em.createQuery("select t from JPQLMember m join m.team t", JPQLTeam.class)
                    .getResultList();


            //임베디드 프로젝션
            List<JPQLAddress> resultList = em.createQuery("select m.address from JPQLOrder m", JPQLAddress.class)
                    .getResultList();

            // 스칼라 프로젝션
            // 스칼라 타입 조회

            // 쿼리
           List<Object[]> scalarResult = em.createQuery("select m.username, m.age from JPQLMember m")
                    .getResultList();

            // object 배열로 조회
            Object[] objArray = scalarResult.get(0);
            System.out.println("resultList1 = " + objArray[0]);
            System.out.println("resultList1 = " + objArray[1]);


            // new 를 이용하여 조회
            // 쿼리문에 new 를 써줘야한다. 패키지명이 다들어가야 한다.
            List<MemberDTO> dtoResult = em.createQuery("select new jpql.MemberDTO(m.username, m.age)from JPQLMember m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = dtoResult.get(0);

            // paging 예제 0 번째 부터 최대 n 개까지 출력
            List<JPQLMember> pagingResult = em.createQuery("select m from JPQLMember m order by m.age desc", JPQLMember.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();


            for (JPQLMember member1 : pagingResult) {
                System.out.println("member1 = "+member1);
            }

            System.out.println("===================inner join===================");
            String innerJoinQuery = "select m from JPQLMember m join m.team t";
            List<JPQLMember> innerJoinResult = em.createQuery(innerJoinQuery, JPQLMember.class)
                    .getResultList();


            System.out.println("===================left join===================");
            String leftJoinQuery = "select m from JPQLMember m left join m.team t";
            List<JPQLMember> lefterJoinResult = em.createQuery(leftJoinQuery, JPQLMember.class)
                    .getResultList();

            System.out.println("===================theta join===================");
            String thetaQuery = "select m from JPQLMember m, JPQLTeam t where m.username = t.name";
            List<JPQLMember> thetaJoinResult = em.createQuery(thetaQuery, JPQLMember.class)
                    .getResultList();


            System.out.println("===================on join1===================");
            String onJoinQuery1 = "select m from JPQLMember m left join m.team t on t.name = 'teamA'";
            List<JPQLMember> onJoinResult1 = em.createQuery(onJoinQuery1, JPQLMember.class)
                    .getResultList();


            System.out.println("===================on join2===================");
            String onJoinQuery2 = "select m from JPQLMember m left join JPQLTeam t on m.username = t.name";
            List<JPQLMember> onJoinResult2 = em.createQuery(onJoinQuery2, JPQLMember.class)
                    .getResultList();

            System.out.println("===================SUB1===================");
            String subQuery1 = "select m from JPQLMember m where m.age > (select avg(m2.age) from JPQLMember m2)";
            List<JPQLMember> subQueryResult1 = em.createQuery(subQuery1, JPQLMember.class)
                    .getResultList();

            System.out.println("===================TYPE===================");
            String query2 = "select m.username, 'HELLO', true from JPQLMember m where m.type = :userType";
            List<Object[]> scalarResult2 = em.createQuery(query2)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] obj : scalarResult2) {
                System.out.println("member1 = "+obj[0]);
                System.out.println("member1 = "+obj[1]);
                System.out.println("member1 = "+obj[2]);
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
