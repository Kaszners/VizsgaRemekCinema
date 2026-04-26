package hu.nyirszikszi.vizsgaremek.cinema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "jwt.secret=QUJDREVGR0hJSktMTU5PUFFSU1RVVldYWVo0NTY3ODkwMTIzNDU2Nzg5MA==",
        "jwt.expiration=3600000",
        "app.default-admin.username=admin",
        "app.default-admin.password=admin123",
        "app.default-admin.email=admin@example.com",
        "spring.mail.username=noreply@example.com"
})
class CinemaApplicationTests {




	@Test
	void contextLoads() {
	}

}
