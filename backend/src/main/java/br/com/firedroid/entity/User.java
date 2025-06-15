package br.com.firedroid.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Username is required")
	@NotNull(message = "Username cannot be null")
	private String username;
	
	@NotBlank(message = "Email is required")
	@NotNull(message = "Email cannot be null")
	private String email;
	
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password cannot be null")
	private String password;
	
	private UserRole role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by") 
	private User createdBy;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "updated_by")
//	private User updatedBy;
//
//	@Column(name = "created_at", nullable = false, updatable = false)
//	private LocalDateTime createdAt;
//
//	@Column(name = "updated_at")
//	private LocalDateTime updatedAt;
	
//	@OneToMany(mappedBy = "creator")
//	private List<User> createdUsers;
//	
//	@OneToMany(mappedBy = "createdBy")
//	private List<Game> createdGames;
//	
//	@OneToMany(mappedBy = "createdBy")
//	private List<GameSection> createdSections;
//	
//    @OneToMany(mappedBy = "createdBy")
//    private List<GameSectionParallaxLayer> createdLayers;
    
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
	
	
	
	
	
	
//	@PrePersist
//	protected void onCreate() {
//		createdAt = LocalDateTime.now();
//		updatedAt = LocalDateTime.now();
//	}
//
//	@PreUpdate
//	protected void onUpdate() {
//		updatedAt = LocalDateTime.now();
//	}
	
	
	
	public User() {
		super();
	}

	public User(String username, String password, UserRole role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String username, String email, String password, UserRole role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public User(Long id, String username, String email, String password, UserRole role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	// Note: UserDetails interface requires implementing additional methods
	// (accountNonExpired, accountNonLocked, credentialsNonExpired, isEnabled)
	// which are not shown in the original code
}