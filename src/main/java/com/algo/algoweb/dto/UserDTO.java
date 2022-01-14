package com.algo.algoweb.dto;

import com.algo.algoweb.domain.Authority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class UserDTO {
    private String username;
    private String name;
    private LocalDate birth;
    private Authority authority;

    public UserDTO() {}

    public UserDTO(String username, String name, LocalDate birth, Authority authority) {
        this.username = username;
        this.name = name;
        this.birth = birth;
        this.authority = authority;
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

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public static class Request {

    }

    public static class Response {
        private boolean success;
        private String message;
        private UserDTO user;

        public Response() {}

        public Response(boolean success, String message, UserDTO user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }
    }
}
