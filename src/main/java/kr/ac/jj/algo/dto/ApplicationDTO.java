package kr.ac.jj.algo.dto;

public class ApplicationDTO {
    private UserDTO user;
    private String introduction;

    public ApplicationDTO() {}

    public ApplicationDTO(UserDTO user, String introduction) {
        this.user = user;
        this.introduction = introduction;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
