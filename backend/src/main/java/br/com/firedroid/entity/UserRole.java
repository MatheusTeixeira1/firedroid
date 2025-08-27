package br.com.firedroid.entity;

public enum UserRole {
	ADMIN("admin"),
	USER("user"),//remova posteriormente
	CONTENT_EDITOR("content_editor"),
	GAME_EDITOR("game_editor"),
	SUPPORT("support");
	
	private String role;
	
	UserRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
}
