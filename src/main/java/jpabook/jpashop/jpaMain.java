package jpabook.jpashop;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            Member member1 = new Member();
            member1.setName("Hi");
            //member1.setAddress(new Address("city","street","zipcode"));
            member1.setWorkPeriod(new Period());
            em.persist(member1);


            Member member2 = new Member();
            member2.setName("Hi2");
            em.persist(member2);
            em.flush();
            em.clear();

            Member proxyMember = em.getReference(Member.class, member1.getId());
            Member entityMember = em.find(Member.class, member2.getId());
            System.out.println("entityMember: "+ entityMember.getClass());
            System.out.println("proxyMember: "+ proxyMember.getClass());
            System.out.println("entityMember == proxyMember: "+ (entityMember.getClass() == proxyMember.getClass()));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
