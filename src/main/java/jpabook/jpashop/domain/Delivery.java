package jpabook.jpashop.domain;

import javax.persistence.*;
import javax.xml.namespace.QName;

@Entity
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    private String city;
    private String street;
    private String zipCode;

    private DeliveryStatus status;


    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;
}
