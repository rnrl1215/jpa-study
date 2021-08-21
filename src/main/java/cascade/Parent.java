package cascade;

import javax.persistence.*;
import java.util.*;

@Entity
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    // cascade 를 해주면 child는 알아서 저장된다.
    // collection안에 있는 것들에도 persis를 날려 줄거야
    // cascade: 이것이 연쇄이다.
    // orphanRemoval: 컬렉션의 아이템 삭제시 delete 쿼리 발생
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
