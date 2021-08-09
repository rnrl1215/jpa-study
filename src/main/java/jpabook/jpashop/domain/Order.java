package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // 누가 주문했는지 알기 위한 식별자
    // 하지만 객체 지향 설계가 아니다.
    // order에서 직접 멤버를 겟 하는게 객체지향 스럽다.
    //@Column(name = "MEMBER_ID")
    //private Long memberId;

    //객체지향스럽게 멤버를 정의한다
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;


    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    //DB 에 저장되는 컬럼 관례는 다음과 같다.
    // ORDER_DATA 또는 order_date
    // 캐멀을 언더스코어로 변경하는 것이 많다.
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

}
