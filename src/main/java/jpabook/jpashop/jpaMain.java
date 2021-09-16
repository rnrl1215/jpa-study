package jpabook.jpashop;


import jpabook.jpashop.domain.Address;

import jpabook.jpashop.domain.AddressEntity;
import jpabook.jpashop.domain.Member;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.awt.*;
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

            Member member = new Member();
            member.setUserName("1");
            member.setHomeAddress(new Address("homeCity","street", "zipcode"));


            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressesHistory().add(new AddressEntity("old1","street","zipCode"));
            member.getAddressesHistory().add(new AddressEntity("old2","street","zipCode"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=========== Start ===========");

            Member findMember = em.find(Member.class, member.getId());

            /*
            // 조회
            // 값타입은 기본적으로 lazy 다 그렇기 때문에
            // 직접 조회할 경우에만 쿼리가 발생한다.
            List<Address> addressHistory = findMember.getAddressesHistory();
            for (Address address : addressHistory) {
                System.out.println("address = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood: favoriteFoods) {
                System.out.println("favoriteFood = "+favoriteFood);
            }
           */

            // 수정
            // 값타입은 통째로 바꿔줘야한다.
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipCode()));


            // 컬렉션 값타입 수정
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 비교할 경우 equal 로 비교한다.
            // 삭제후 통으로 변경한다.
            findMember.getAddressesHistory().remove(new AddressEntity("old1","street","zipCode"));
            findMember.getAddressesHistory().add(new AddressEntity("newCity","street","zipCode"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
