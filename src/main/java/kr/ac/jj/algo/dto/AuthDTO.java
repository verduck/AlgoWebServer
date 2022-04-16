package kr.ac.jj.algo.dto;

public class AuthDTO {
	public static class Request {
		private String username;
		private String password;

		public Request() {}

		public Request(String username, String password) {
			this.username = username;
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class Response {
		private String message;
		private UserDTO user;
		private String token;

		public Response() {}

		public Response(String message, UserDTO user, String token) {
			this.message = message;
			this.user = user;
			this.token = token;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public UserDTO getUser() {
			return user;
		}

		public void setUser(UserDTO user) {
			this.user = user;
		}
	}
}
