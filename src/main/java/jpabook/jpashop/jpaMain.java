package jpabook.jpashop;


import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("김영한");

            em.persist(book);
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
