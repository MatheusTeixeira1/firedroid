package br.com.firedroid.service;

import br.com.firedroid.DTOs.page_theme.PageThemeRequest;
import br.com.firedroid.DTOs.page_theme.PageThemeAdminResponse;
import br.com.firedroid.DTOs.page_theme.PageThemePublicResponse;
import br.com.firedroid.entity.Game;
import br.com.firedroid.entity.PageTheme;
import br.com.firedroid.entity.User;
import br.com.firedroid.exception.CustomEntityNotFoundException;
import br.com.firedroid.exception.DuplicateIdentifierException;
import br.com.firedroid.exception.InvalidUsernameException;
import br.com.firedroid.exception.ProtectedResourceException;
import br.com.firedroid.repository.GameRepository;
import br.com.firedroid.repository.PageThemeRepository;
import br.com.firedroid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Service
public class PageThemeService {
	@Autowired
	private GameRepository gameRepository;
	
	
    @Autowired
    private PageThemeRepository pageThemeRepository;

    @Autowired
    private UserRepository userRepository;

    public PageThemeService(PageThemeRepository pageThemeRepository, UserRepository userRepository) {
        this.pageThemeRepository = pageThemeRepository;
        this.userRepository = userRepository;
    }

    // ----- Para usuários públicos -----
    public List<PageThemePublicResponse> getAll() {
        List<PageTheme> themes = pageThemeRepository.findAll();
        return themes.stream().map(PageThemePublicResponse::fromEntity).toList();
    }

    public PageThemePublicResponse getById(Long id) {
    	PageTheme theme = pageThemeRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Tema de id %s não foi encontrado.", id)));
        return PageThemePublicResponse.fromEntity(theme);
    }

    // ----- Para administradores -----
    public List<PageThemeAdminResponse> getAllAdmin() {
        List<PageTheme> themes = pageThemeRepository.findAll();
        return themes.stream().map(PageThemeAdminResponse::fromEntity).toList();
    }

    public PageThemeAdminResponse getByIdAdmin(Long id) {
    	PageTheme theme = pageThemeRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Tema de id %s não foi encontrado.", id)));
        return PageThemeAdminResponse.fromEntity(theme);
    }

    public void create(PageThemeRequest request) {
    	if (pageThemeRepository.existsByName(request.name())) {
            throw new DuplicateIdentifierException("Já existe um tema com este nome");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));

        PageTheme theme = new PageTheme();
        theme.setName(request.name());
        theme.setBackgroundColor(request.backgroundColor());
        theme.setIconColor(request.iconColor());
        theme.setTitleColor(request.titleColor());
        theme.setParagraphColor(request.paragraphColor());
        theme.setTitleWeight(request.titleWeight());
        theme.setParagraphWeight(request.paragraphWeight());
        theme.setTitleFont(request.titleFont());
        theme.setParagraphFont(request.paragraphFont());
        theme.setTitleSize(request.titleSize());
        theme.setParagraphSize(request.paragraphSize());
        theme.setCreatedBy(user);

        pageThemeRepository.save(theme);
    }

    public void update(Long id, PageThemeRequest request) {
        PageTheme theme = pageThemeRepository.findById(id)
				.orElseThrow(() -> new CustomEntityNotFoundException(String.format("Tema de id %s não foi encontrado.", id)));
		
        
        if (!theme.getName().equals(request.name()) && 
            pageThemeRepository.existsByName(request.name())) {
            throw new DuplicateIdentifierException("Já existe um tema com este nome");
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new InvalidUsernameException(String.format("Usuario %s invalido.", username)));

        theme.setName(request.name());
        theme.setBackgroundColor(request.backgroundColor());
        theme.setIconColor(request.iconColor());
        theme.setTitleColor(request.titleColor());
        theme.setParagraphColor(request.paragraphColor());
        theme.setTitleWeight(request.titleWeight());
        theme.setParagraphWeight(request.paragraphWeight());
        theme.setTitleFont(request.titleFont());
        theme.setParagraphFont(request.paragraphFont());
        theme.setTitleSize(request.titleSize());
        theme.setParagraphSize(request.paragraphSize());
        theme.setUpdatedBy(user);

        pageThemeRepository.save(theme);
    }

    public void delete(Long id) {

        PageTheme defaultTheme = pageThemeRepository.findByName("default")
		        .orElseThrow(() -> new CustomEntityNotFoundException("O tema padrão do sistema não foi encontrado, crie um com o nome 'default'"));
        
        if (id.equals(defaultTheme.getId())) {
        	throw new ProtectedResourceException("Esse tema é protegido pelo sistema");
    
        }
        PageTheme themeToDelete = pageThemeRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(String.format("Tema de id %s não encontrado", id)));

        List<Game> gamesWithTheme = gameRepository.findByPageThemeId(id);

        if (!gamesWithTheme.isEmpty()) {
            gamesWithTheme.forEach(game -> game.setPageTheme(defaultTheme));
            gameRepository.saveAll(gamesWithTheme);
        }

        pageThemeRepository.delete(themeToDelete);
    }
}