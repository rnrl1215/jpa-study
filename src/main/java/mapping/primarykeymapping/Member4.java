package mapping.primarykeymapping;

import javax.persistence.*;

// 꼭넣어야 한다.
// JPA가 로딩 될때 해당 어노테이션을 보고 인식한다.
@Entity
//@SequenceGenerator(name = "member_seq_generator" ,sequenceName = "member_seq")
public class Member4 {

    @Id //PK 설정
    // 자동으로 키값을 생성한다.
    // IDENTITY: 기본키 생성을 DB에 위임한다.
    // SEQUENCE: 순서대로 생성한다.
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "member_seq_generator")
    private Long id;


    // 컬럼이름을 지정해준다.
    // insertable: 컬럼을 수정시 DB 인서트 할것인지
    // updateable: 수정시 업데이트 할 것인지 안할 것인지
    // 둘다 false 로 하면 DB에 변경 내용이 들어 가지 않는다.
    // nullable: false일 경우 not null 제약 조건이 적용된다.
    // unique: 제약 조건을 만들어 준다. 하지만 여기서걸면 이름이 맘대로 생성
    // 그래서 @Table 에서 걸어준다.
    @Column(name = "name", nullable = false)
    private String username;

    //JPA 는 기본생성자가 있어야 한다.
    public Member4() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
