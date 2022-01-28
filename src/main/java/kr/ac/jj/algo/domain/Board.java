package kr.ac.jj.algo.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;
    private String icon;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Board() {}

    public Board(Long id, String name, String value, String icon, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.icon = icon;
        this.posts = posts;
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
