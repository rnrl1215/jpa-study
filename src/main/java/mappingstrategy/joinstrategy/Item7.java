package mappingstrategy.joinstrategy;

import javax.persistence.*;

@Entity
// JOIN 전략으로 간다.
@Inheritance(strategy = InheritanceType.JOINED)

// 단일테이블 전략
// DiscriminatorColumn 가 없어도 자동으로 생긴다.
// 무슨타입인지 파악하기 위해서 이다.
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)


// 테이블을 다 만드는 전략
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

//DTYPE 을 생성해 준다.
// 클래스 명이 들어간다.
// 예를 들어 Movie7의 엔티티면 Movie7이 타입으로 들어간다.
// name을 써서 컬럼명을 변경해줄수 있다.
@DiscriminatorColumn
public abstract class Item7 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
