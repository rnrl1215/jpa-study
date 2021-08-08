package directionmapping.onetoone;

import javax.persistence.*;


// 꼭넣어야 한다.
// JPA가 로딩 될때 해당 어노테이션을 보고 인식한다.
@Entity
//@Table(name = "USER") // 테이블명 지정
public class OneToOenMember {
    @Id //PK 설정
    @GeneratedValue
    @Column(name = "OTO_MEMBER_ID")
    private Long id;

    // 컬럼이름을 지정해준다.
    private String name;

    @OneToOne
    @JoinColumn(name = "OTO_LOCKER_ID")
    private Locker locker;

    //JPA 는 기본생성자가 있어야 한다.
    public OneToOenMember() {
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

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}
