package kr.ac.jj.algo.dto;

public class ApplicationDTO {
    private int id;
    private UserDTO.Detail user;
    private String introduction;

    public ApplicationDTO() {}

    public ApplicationDTO(int id, UserDTO.Detail user, String introduction) {
        this.id = id;
        this.user = user;
        this.introduction = introduction;
    }

    public UserDTO.Detail getUser() {
        return user;
    }

    public void setUser(UserDTO.Detail user) {
        this.user = user;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class Request {
        private String introduction;

        public Request() {}

        public Request(String introduction) {
            this.introduction = introduction;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
    }
}
