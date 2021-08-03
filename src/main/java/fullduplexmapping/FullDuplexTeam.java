package fullduplexmapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class FullDuplexTeam {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    public List<FullDuplexMember> getFullDuplexMembers() {
        return fullDuplexMembers;
    }

    public void setFullDuplexMembers(List<FullDuplexMember> fullDuplexMembers) {
        this.fullDuplexMembers = fullDuplexMembers;
    }

    // 양방향 관계를 위한 List 생성
    // Team 입장에서 봤을때는 Team 1 Memeber N 1:N 관계이다
    // mappedBy 는 내가 무엇이랑 연결 되어 있는지 판별
    // member class의 team 변수에 맵핑이 되어있다.
    // team class 는 fullDuplexTeam 변수에 맵핑 되어있다는 뜻
    @OneToMany( mappedBy = "fullDuplexTeam")
    private List<FullDuplexMember> fullDuplexMembers= new ArrayList<>();

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
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
