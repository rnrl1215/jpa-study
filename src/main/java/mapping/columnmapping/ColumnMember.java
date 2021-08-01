package mapping.columnmapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// 꼭넣어야 한다.
// JPA가 로딩 될때 해당 어노테이션을 보고 인식한다.
@Entity
//@Table(name = "USER") // 테이블명 지정
public class ColumnMember {

    @Id //PK 설정
    private Long id;


    // 컬럼이름을 지정해준다.
    // insertable: 컬럼을 수정시 DB 인서트 할것인지
    // updateable: 수정시 업데이트 할 것인지 안할 것인지
    // 둘다 false 로 하면 DB에 변경 내용이 들어 가지 않는다.
    // nullable: false일 경우 not null 제약 조건이 적용된다.
    // unique: 제약 조건을 만들어 준다. 하지만 여기서걸면 이름이 맘대로 생성
    // 그래서 @Table 에서 걸어준다.
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    private Integer age;

    //DB 에는 enum 타입이 없기 때문에  Enumerated로 맵핑해준다.
    // EnumType은 무조건 String으로 한다.
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // DB 에는 Date, Time, Data+time 이 있기 때문에 맵핑으로 지정해준다.
    // 날짜타입에 쓰는건데 지금은 필요없다.
    // 그냥 LocalDate 를 쓰면된다.
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate testLocalDate;
    private LocalDateTime testLocalDataTime;

    // VACHAR 보다 큰 경우 사용한다.
    @Lob
    private String description;

    //JPA 는 기본생성자가 있어야 한다.
    public ColumnMember() {
    }

    public ColumnMember(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
