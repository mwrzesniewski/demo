package pl.wrzesniewski.demo.user;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(Long email);

    void deleteUserById(Long id);

    Page<User> findByActive(boolean active, Pageable pageable);
}
