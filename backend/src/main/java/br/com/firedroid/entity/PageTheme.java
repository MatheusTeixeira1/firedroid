package br.com.firedroid.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "page_themes")
public class PageTheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "background_color")
	private String backgroundColor;

	@Column(name = "icon_color")
	private String iconColor;

	@Column(name = "title_color")
	private String titleColor;

	@Column(name = "paragraph_color")
	private String paragraphColor;

	@Column(name = "title_weight")
	private String titleWeight;

	@Column(name = "paragraph_weight")
	private String paragraphWeight;

	@Column(name = "title_font")
	private String titleFont;

	@Column(name = "paragraph_font")
	private String paragraphFont;

	@Column(name = "title_size")
	private Integer titleSize;

	@Column(name = "paragraph_size")
	private Integer paragraphSize;

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

//----- Uteis -----

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
// ----- Construrores -----

	public PageTheme() {
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

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getIconColor() {
		return iconColor;
	}

	public void setIconColor(String iconColor) {
		this.iconColor = iconColor;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getParagraphColor() {
		return paragraphColor;
	}

	public void setParagraphColor(String paragraphColor) {
		this.paragraphColor = paragraphColor;
	}

	public String getTitleWeight() {
		return titleWeight;
	}

	public void setTitleWeight(String titleWeight) {
		this.titleWeight = titleWeight;
	}

	public String getParagraphWeight() {
		return paragraphWeight;
	}

	public void setParagraphWeight(String paragraphWeight) {
		this.paragraphWeight = paragraphWeight;
	}

	public String getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(String titleFont) {
		this.titleFont = titleFont;
	}

	public String getParagraphFont() {
		return paragraphFont;
	}

	public void setParagraphFont(String paragraphFont) {
		this.paragraphFont = paragraphFont;
	}

	public Integer getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(Integer titleSize) {
		this.titleSize = titleSize;
	}

	public Integer getParagraphSize() {
		return paragraphSize;
	}

	public void setParagraphSize(Integer paragraphSize) {
		this.paragraphSize = paragraphSize;
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

// ----- Getters e Setters -----
	
	
}