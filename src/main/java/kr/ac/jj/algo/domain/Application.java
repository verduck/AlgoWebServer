package kr.ac.jj.algo.domain;

import javax.persistence.*;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String introduction;

    public Application() {}
    public Application(User user, String introduction) {
        this(null, user, introduction);
    }

    public Application(Integer id, User user, String introduction) {
        this.id = id;
        this.user = user;
        this.introduction = introduction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
