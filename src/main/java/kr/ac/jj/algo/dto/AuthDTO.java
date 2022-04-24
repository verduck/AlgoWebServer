package kr.ac.jj.algo.dto;

public class AuthDTO {
	private UserDTO.Detail user;
	private String token;

	public AuthDTO() {}
	public AuthDTO(UserDTO.Detail user, String token) {
		this.user = user;
		this.token = token;
	}

	public UserDTO.Detail getUser() {
		return user;
	}

	public void setUser(UserDTO.Detail user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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
}
