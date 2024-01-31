package JustinVu.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import JustinVu.User.User;
import JustinVu.User.UserRepository;
import lombok.SneakyThrows;



@RestController
@RequestMapping("/api/users")
public class RESTapiController {

    @Autowired
    private UserRepository userRepository;
    private final GoogleAuthenticator gAuth;

    @Autowired
    public RESTapiController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.gAuth = new GoogleAuthenticator();
        this.gAuth.setCredentialRepository(userRepository);
    }
    

    // Lấy danh sách thông tin tất cả user trong hệ thống
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    
    // Tạo user mới trong hệ thống
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error creating : " + e.getMessage());
        }
    }


    //Bật 2FA Google Authenticator cho user = email
    @SneakyThrows
    @GetMapping("/generate/{username}")
    public void generate(@PathVariable String username, HttpServletResponse response) {
        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        User user = userRepository.findByEmail(username);
        if (user != null) {
            user.setMfa_enabled(true);
            userRepository.save(user);
        }

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();
    }


    // Tắt 2FA Google Authenticator cho user = email
    @PutMapping("/disable2FA/{username}")
    public ResponseEntity<String> disable2FA(@PathVariable String username) {
        try {
            User user = userRepository.findByEmail(username);

            if (user != null) {
                user.setMfa_enabled(false);
                user.setSecret(null);

                userRepository.save(user);

                return ResponseEntity.ok("2FA disabled successfully for user: " + username);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Error disabling 2FA: User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error disabling 2FA : " + e.getMessage());
        }
    }


    // Điền mã xác thực 2FA Google Authenticator
    @PostMapping("/validate2FA")
    public ResponseEntity<String> validate2FA(@RequestBody ValidateCodeDto body) {
        try {
            boolean isCodeValid = gAuth.authorizeUser(body.getEmail(), body.getCode());

            if (isCodeValid) {
                return ResponseEntity.ok("2FA code validated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body("Error validating 2FA code: Invalid code");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error validating 2FA code: " + e.getMessage());
        }
    }


    // chỉnh sửa thông tin user
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
    try {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setFirstname(userDetails.getFirstname());
            user.setLastname(userDetails.getLastname());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setMfa_enabled(userDetails.isMfa_enabled());
            user.setSecret(userDetails.getSecret());

            userRepository.save(user);

            return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Error updating: User not found");
                }
            } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error updating : " + e.getMessage());
        }
    }


    // Xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);

            if (user != null) {
                userRepository.delete(user);
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("Error deleting: User not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error deleting : " + e.getMessage());
        }
    }


    @PostConstruct
    public void init() {

    }
}

