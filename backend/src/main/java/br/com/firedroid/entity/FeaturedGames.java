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
import jakarta.persistence.Table;

@Entity
@Table(name = "featuredGames")
public class FeaturedGames implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game1_id", nullable = true)
    private Game game1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game2_id", nullable = true)
    private Game game2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game3_id", nullable = true) 
    private Game game3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game4_id", nullable = true) 
    private Game game4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game5_id", nullable = true) 
    private Game game5;

    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private User createdBy;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	
	public FeaturedGames() {
		super();
	}

	

	public FeaturedGames(Game game1, Game game2, Game game3, Game game4, Game game5, User createdBy,
			LocalDateTime createdAt) {
		super();
		this.game1 = game1;
		this.game2 = game2;
		this.game3 = game3;
		this.game4 = game4;
		this.game5 = game5;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}



	public Long getId() {
		return id;
	}

	public User getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame1() {
		return game1;
	}

	public void setGame1(Game game1) {
		this.game1 = game1;
	}

	public Game getGame2() {
		return game2;
	}

	public void setGame2(Game game2) {
		this.game2 = game2;
	}

	public Game getGame3() {
		return game3;
	}

	public void setGame3(Game game3) {
		this.game3 = game3;
	}

	public Game getGame4() {
		return game4;
	}

	public void setGame4(Game game4) {
		this.game4 = game4;
	}

	public Game getGame5() {
		return game5;
	}

	public void setGame5(Game game5) {
		this.game5 = game5;
	}

	
	
	
//	@OneToMany(mappedBy = "featured", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("displayOrder ASC")
//    private List<Game> games = new ArrayList<>();
	
	
}
