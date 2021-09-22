package jpabook.jpashop.domain;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;


    @Column(name = "USER_NAME")
    private String username;

    // period
    /*
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    */

    @Embedded
    private Period workPeriod;


    /*
    private String city;
    private String street;
    private String zipCode;
    */
    @Embedded
    private Address homeAddress;

    // 값 컬렉션 타입
    // 값 타입 맵핑
    @ElementCollection
    // 테이블 명과 조안 FK 지정
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
    )
    @Column(name = "FOOD_NAME") // 예외적으로 된다. 1나이기 때문에 컬럼명 지정가능
    private Set<String> favoriteFoods = new HashSet<>();

    // 값 타입 맵핑
    @ElementCollection
    // 테이블 명과 조인 FK 지정
    @CollectionTable(name = "ADDRESS", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
    )
    private List<Address> addressesHistory = new ArrayList<>();


    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressesHistory() {
        return addressesHistory;
    }

    public void setAddressesHistory(List<Address> addressesHistory) {
        this.addressesHistory = addressesHistory;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Period getWorkPeriod() {
         return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
}
