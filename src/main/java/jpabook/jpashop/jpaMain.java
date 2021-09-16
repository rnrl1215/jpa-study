package jpabook.jpashop;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class jpaMain {
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

            /*
            Address address = new Address("city","street","10000");

            Member member1 = new Member();
            member1.setUserName("member1");
            member1.setHomeAddress(address);
            em.persist(member1);

            // 변경을 할때는 완전히 다 바꿔야한다.
            Address modifiedAddress = new Address(address.getCity(), address.getStreet(), address.getZipCode());
            member1.setHomeAddress(modifiedAddress);
             */

            // embedded collection ex
            Member member = new Member();
            member.setUserName("1");
            member.setHomeAddress(new Address("homeCity","street", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressesHistory().add(new Address("old1","street","zipCode"));
            member.getAddressesHistory().add(new Address("old2","street","zipCode"));

            em.persist(member);

            //DB 저장후 영속성 클리어
            em.flush();
            em.clear();


            // 조회 했을 경우 멤버만 조회 한다.
            // 컬렉션 값 타입을 조회하지 않는다.
            // 이유는? 컬렉션 값타입은 지연로딩이다.

            System.out.println("==================== Start ====================");
            Member findMember = em.find(Member.class, member.getId());

            /* 값 타입 조회
            // 가져올때 쿼리가 수행된다.
            List<Address> addressesHistory = findMember.getAddressesHistory();
            for (Address address : addressesHistory) {
                System.out.println("address: " + address.getCity());
            }

            // 가져올때 쿼리가 수행된다.
            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for(String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood: " + favoriteFood);
            }
            */

            // 값 타입 수정
            // homeCity -> newCity
            // 아래 처럼 수정하면 안된다. 사이드 이펙트 발생
            // findMember.getHomeAddress().setCity("newCity");

            // 통으로 변경해야 된다.
            Address old = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", old.getStreet(), old.getZipCode()));

            // 값타입 컬렉션 수정
            // 치킨 -> 한식으로 변경
            // 스트링이기 때문에 삭제후 변경해야된다.
            //findMember.getFavoriteFoods().remove("치킨");
            //findMember.getFavoriteFoods().add("한식");

            // 주소를 변경한다.
            // old1 -> new 1 으로 변경
            // 찾을때 equals 를 사용한다. equals 와 hascode 를 잘 짜줘야한다.
            findMember.getAddressesHistory().remove(new Address("old1","street","zipCode"));
            findMember.getAddressesHistory().add(new Address("newCity","street","zipCode"));



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
