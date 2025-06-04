package br.com.firedroid.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "image_url")
	private String image;

	@Column(name = "steam_link")
	private String steamLink;

	@Column(name = "epic_link")
	private String epicLink;

	@Column(name = "itchio_link")
	private String itchioLink;

	@Column(name = "site_link")
	private String siteLink;

	@OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("displayOrder ASC")
	private List<GameSection> sections;

//	----- Auditoria -----
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private User updatedBy;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

//	----- Uteis	-----
	public void addSection(GameSection section) {
		sections.add(section);
		section.setGame(this);
	}

	public void removeSection(GameSection section) {
		sections.remove(section);
		section.setGame(null);
	}

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

//	----- getters, setters e construtores -----

//	public Game(Long id, String name, String description, String image, String steamLink, String epicLink,
//			String itchioLink, String siteLink, List<GameSection> sections) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.image = image;
//		this.steamLink = steamLink;
//		this.epicLink = epicLink;
//		this.itchioLink = itchioLink;
//		this.siteLink = siteLink;
//		this.sections = sections;
//	}

	public Game() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSteamLink() {
		return steamLink;
	}

	public void setSteamLink(String steamLink) {
		this.steamLink = steamLink;
	}

	public String getEpicLink() {
		return epicLink;
	}

	public void setEpicLink(String epicLink) {
		this.epicLink = epicLink;
	}

	public String getItchioLink() {
		return itchioLink;
	}

	public void setItchioLink(String itchioLink) {
		this.itchioLink = itchioLink;
	}

	public String getSiteLink() {
		return siteLink;
	}

	public void setSiteLink(String siteLink) {
		this.siteLink = siteLink;
	}

	public List<GameSection> getSections() {
		return sections;
	}

	public void setSections(List<GameSection> sections) {
		this.sections = sections;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}