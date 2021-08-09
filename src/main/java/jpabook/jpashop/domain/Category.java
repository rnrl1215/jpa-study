package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;

    
    // 부모는 하나이고
    // 여러개의 자식을 가질수 있다.
    // 셀프 맵핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;


    // 셀프 맵핑이다.
    @OneToMany(mappedBy = "parent")
    private List<Category>  childs= new ArrayList<>();


    // 중간 테이블을 만들어 준다.
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();

}
