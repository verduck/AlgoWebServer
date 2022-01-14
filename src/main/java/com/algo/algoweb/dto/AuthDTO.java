package com.algo.algoweb.dto;

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
		private boolean result;
		private String message;
		private String token;

		public Response() {}

		public Response(boolean result, String message, String token) {
			this.result = result;
			this.message = message;
			this.token = token;
		}

		public boolean isResult() {
			return result;
		}

		public void setResult(boolean result) {
			this.result = result;
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
	}
}
