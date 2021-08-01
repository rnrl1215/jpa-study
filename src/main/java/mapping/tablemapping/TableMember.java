package mapping.tablemapping;

import javax.persistence.*;


// 꼭넣어야 한다.
// JPA가 로딩 될때 해당 어노테이션을 보고 인식한다.
@Entity

//@Table(name = "USER") // 테이블명 지정
public class TableMember {

    @Id //PK 설정
    private Long id;


    // 컬럼이름을 지정해준다.
    private String name;

    //JPA 는 기본생성자가 있어야 한다.
    public TableMember() {
    }

    public TableMember(Long id, String name) {
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
