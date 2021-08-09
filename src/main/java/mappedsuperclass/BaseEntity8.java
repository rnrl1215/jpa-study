package mappedsuperclass;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 해당 어노테이션을 쓰면
// 상속 받은 모든 클래스에 해당 속성만 들어가게 된다.
// 속성을 공통으로 관리 할때 쓴다.
@MappedSuperclass
public abstract class BaseEntity8 {

    // 모든 테이블에 다 들어가야한다.
    // 속성만 상속 받아서 쓰고 싶다.
    private String createBy;
    private LocalDateTime createDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
