package br.com.firedroid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.firedroid.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	UserDetails findByUsername(String username);
    Optional<User> findUserByUsername(String username);

}
