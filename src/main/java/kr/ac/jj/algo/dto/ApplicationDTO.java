package kr.ac.jj.algo.dto;

public class ApplicationDTO {
    private UserDTO user;
    private String introduction;

    public ApplicationDTO() {}

    public ApplicationDTO(UserDTO user, String introduction) {
        this.user = user;
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

    public static class Response {
        private boolean success;
        private String message;
        private ApplicationDTO application;

        public Response() {}

        public Response(boolean success, String message, ApplicationDTO application) {
            this.success = success;
            this.message = message;
            this.application = application;
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

        public ApplicationDTO getApplication() {
            return application;
        }

        public void setApplication(ApplicationDTO application) {
            this.application = application;
        }
    }
}
