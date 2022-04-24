package kr.ac.jj.algo.dto;

import kr.ac.jj.algo.domain.Authority;

import java.time.LocalDate;

public class UserDTO {
    private Integer id;
    private String username;
    private String name;

    public UserDTO() {}

    public UserDTO(Integer id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Detail {
        private Integer id;
        private String username;
        private String name;
        private LocalDate birth;
        private char gender;
        private byte grade;
        private String status;
        private Authority authority;

        public Detail() {}

        public Detail(Integer id, String username, String name, LocalDate birth, char gender, byte grade, String status, Authority authority) {
            this.id = id;
            this.username = username;
            this.name = name;
            this.birth = birth;
            this.gender = gender;
            this.grade = grade;
            this.status = status;
            this.authority = authority;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirth() {
            return birth;
        }

        public void setBirth(LocalDate birth) {
            this.birth = birth;
        }

        public char getGender() {
            return gender;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        public byte getGrade() {
            return grade;
        }

        public void setGrade(byte grade) {
            this.grade = grade;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Authority getAuthority() {
            return authority;
        }

        public void setAuthority(Authority authority) {
            this.authority = authority;
        }
    }

}
