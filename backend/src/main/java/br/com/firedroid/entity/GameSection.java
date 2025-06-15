package br.com.firedroid.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_sections")
public class GameSection implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
    
    @OneToMany(mappedBy = "gameSection", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<GameSectionParallaxLayer> parallaxLayers = new ArrayList<>();
    


//	----- Auditoria -----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

 // ----- Uteis -----
    public void addParallaxLayer(GameSectionParallaxLayer layer) {
        parallaxLayers.add(layer);
        layer.setGameSection(this);
    }

    public void removeParallaxLayer(GameSectionParallaxLayer layer) {
        parallaxLayers.remove(layer);
        layer.setGameSection(null);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    
    
    
//	----- getters, setters e construtores -----
    
	public GameSection() {
		super();
	}

	public GameSection(Long id, String title, String text, List<GameSectionParallaxLayer> parallaxLayers) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.parallaxLayers = parallaxLayers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<GameSectionParallaxLayer> getParallaxLayers() {
		return parallaxLayers;
	}

	public void setParallaxLayers(List<GameSectionParallaxLayer> parallaxLayers) {
		this.parallaxLayers = parallaxLayers;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
    
}