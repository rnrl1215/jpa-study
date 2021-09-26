package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


public class jpqlMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            JPQLTeam teamA = new JPQLTeam();
            teamA.setName("teamA");
            em.persist(teamA);

            JPQLTeam teamB = new JPQLTeam();
            teamB.setName("teamB");
            em.persist(teamB);

            JPQLTeam teamC = new JPQLTeam();
            teamC.setName("teamC");
            em.persist(teamC);

            JPQLMember member1 = new JPQLMember();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.setTeam(teamA);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);


            JPQLMember member2 = new JPQLMember();
            member2.setUsername("회원2");
            member2.setAge(10);
            member2.setTeam(teamA);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            JPQLMember member3 = new JPQLMember();
            member3.setUsername("회원3");
            member3.setAge(13);
            member3.setTeam(teamB);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);

            em.flush();
            em.clear();

            // 쿼리로 불러오면 영속성 컨텍스트에 들어 갈까?
            //  엔티티 프로젝션
            List <JPQLMember> entityResult1 = em.createNativeQuery("select MEMBER_ID, USER_NAME, age, TEAM_ID, type from JPQLMember", JPQLMember.class)
                    .getResultList();

            // 관리가 됨.
            //JPQLMember findMember = entityResult1.get(0);
            //findMember.setUsername("member2");

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
            System.out.println("===================paging===================");
            List<JPQLMember> pagingResult = em.createQuery("select m from JPQLMember m order by m.age desc", JPQLMember.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();


            for (JPQLMember m : pagingResult) {
                System.out.println("m = "+m);
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

            System.out.println("===================함수예제===================");
            // case when 예제
            String select = "select case when m.age <= 10 then '학생요금' when m.age >= 60 then '경로요금' else '일반요금' end from JPQLMember m";
            List<String> resultList1 = em.createQuery(select, String.class).getResultList();

            for(String s : resultList1) {
                System.out.println("s = "+s);
            }

            // coalesce 예제
            String selectCoalesce = "select coalesce(m.username,'이름없는회원') as username from JPQLMember m";
            List<String> resultList2 = em.createQuery(selectCoalesce, String.class).getResultList();

            for(String s : resultList2) {
                System.out.println("s = "+s);
            }

            // nullif 예제
            String query = "select nullif(m.username,'teamA') as username from JPQLMember m";
            List<String> resultList3 = em.createQuery(query, String.class).getResultList();

            for(String s : resultList3) {
                System.out.println("s = "+s);
            }

            // CONCAT 예제
            String concatQuery = "select concat('a', 'b') as username from JPQLMember m";
            List<String> concatResult = em.createQuery(concatQuery, String.class).getResultList();

            for(String s : concatResult) {
                System.out.println("s = "+s);
            }

            // SUBSTRING 예제
            String subStringQuery = "select substring(m.username, 2,3) as username from JPQLMember m";
            List<String> subStringResult = em.createQuery(subStringQuery, String.class).getResultList();

            for(String s : subStringResult) {
                System.out.println("s = "+s);
            }

            // locate 예제
            String locateQuery = "select locate('de','abcde') as username from JPQLMember m";
            List<Integer> locateResult = em.createQuery(locateQuery, Integer.class).getResultList();

            for(Integer s : locateResult) {
                System.out.println("s = "+s);
            }


            // SIZE
            String sizeQuery = "select size(m.members) from JPQLTeam m";
            List<Integer> sizeResult = em.createQuery(sizeQuery, Integer.class).getResultList();

            for(Integer s : sizeResult) {
                System.out.println("s = "+s);
            }


            // 사용자 정의함수 예제
            String customFunctionQuery = "select function('group_concat', m.username)  from JPQLMember m";
            List<String> customFunctionResult = em.createQuery(customFunctionQuery, String.class).getResultList();

            for(String s : customFunctionResult) {
                System.out.println("s = "+s);
            }


            // collection 예제
            String collectionQuery = "select t.members from JPQLTeam t";
            List<Collection> result = em.createQuery(collectionQuery, Collection.class)
                    .getResultList();

            System.out.println("getCollection = " + result);


            // members 에서 username을 가져오고 싶은경우
            // 직접 join 문을 써서 별칭으로 가져온후 접근한다.
            String collectionQuery2 = "select m.username from JPQLTeam t join t.members m";
            List<String> result2 = em.createQuery(collectionQuery2, String.class)
                    .getResultList();

            for(String name : result2) {
                System.out.println("getCollection = " + name);
            }


            System.out.println("===================fetch join===================");

            // 영속성 컨텍스트에서 제거.
            em.clear();

            // members 에서 username을 가져오고 싶은경우
            // 직접 join 문을 써서 별칭으로 가져온후 접근한다.

            // fetch 를 써서 한번에 다 가져온다. 컬렉션에 담기는 데이터는 프로시가 아닌 진짜 데이터가 담긴다.
            //String fetchJoinQuery = "select m from JPQLMember m";
            String fetchJoinQuery = "select m from JPQLMember m join fetch m.team";
            List<JPQLMember> fetchJoinResult = em.createQuery(fetchJoinQuery, JPQLMember.class)
                    .getResultList();

            for (JPQLMember member : fetchJoinResult) {
                System.out.println("fetchJoinResult = "+ member.getUsername()+","+member.getTeam().getName());
                //회원1, 팀A(SQL)
                //회원2, 팀A(1차캐시)
                //회원3, 팀B(SQL)
            }

            //String fetchJoinQuery2 = "select t from JPQLTeam t join fetch t.members";
            String fetchJoinQuery2 = "select DISTINCT t from JPQLTeam t join fetch t.members where t.name = 'teamA'"; //중복제거
            List<JPQLTeam> fetchJoinResult2 = em.createQuery(fetchJoinQuery2, JPQLTeam.class)
                    .getResultList();

            for (JPQLTeam team : fetchJoinResult2) {
                System.out.println("fetchJoinResult2= " + team.getName() + "," + team.getMembers().size());
                //회원1, 팀A(SQL)
                //회원2, 팀A(1차캐시)
                //회원3, 팀B(SQL)
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
