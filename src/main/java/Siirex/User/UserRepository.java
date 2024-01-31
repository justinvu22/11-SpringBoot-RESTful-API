package JustinVu.User;

import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, ICredentialRepository {
    User findByEmail(String email);

    @Override
    default String getSecretKey(String userName) {
        User user = findByEmail(userName);
        return (user != null) ? user.getSecret() : null;
    }

    @Override
    default void saveUserCredentials(String userName,
                                     String secretKey,
                                     int validationCode,
                                     List<Integer> scratchCodes) {
        User user = findByEmail(userName);
        if (user != null) {
            user.setSecret(secretKey);
            save(user);
        }
    }
}
