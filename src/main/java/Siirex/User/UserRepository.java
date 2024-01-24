package Siirex.User;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    void save(org.apache.catalina.@Valid User user);
}
