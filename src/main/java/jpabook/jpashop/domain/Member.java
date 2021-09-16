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


    @Column(length = 10)
    private String userName;

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


    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFood) {
        this.favoriteFoods = favoriteFood;
    }

    /*
    public List<Address> getAddressesHistory() {
        return addressHistory;
    }

    public void setAddressesHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }
    */

    // 값타입 컬렉션 맵핑
    @ElementCollection
    // 테이블 이름을 준다 FK 를 지정해준다.
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
    @JoinColumn(name = "MEMBER_ID"))
    // 값이 하나기 때문에 예외적으로 컬럼이름을 줄수 있다.
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    
    // 하지만 실무에서는 엔티티로 만들어서 사용하는 대안도 있다.
    /*
    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
    @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();
    */
    
    // 값타입 컬렉션을 Entity로 만들어서 풀기
    // 이렇게 하면 활용할수 있는게 더 많아진다.
    // 1:N cascade
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    public List<AddressEntity> getAddressesHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
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
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
