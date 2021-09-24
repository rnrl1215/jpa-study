package jpql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class JPQLProduct {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private int stockAmount;
}
