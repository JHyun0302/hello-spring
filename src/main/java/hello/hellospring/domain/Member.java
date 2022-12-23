package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //JPA가 관리하는 객체
public class Member {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db에 값 넣으면 자동으로 id 생성되는 것 = identity
    private Long id; // 시스템이 저장하는 id
    //@Column(name = "username") //Column의 내용 = username
    private String name;

    public Long getId() { // Getter&Setter: Alt + Insert
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
