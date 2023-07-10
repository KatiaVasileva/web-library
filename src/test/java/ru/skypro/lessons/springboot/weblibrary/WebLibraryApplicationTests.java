package ru.skypro.lessons.springboot.weblibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
class WebLibraryApplicationTests {

    @Test
    void contextLoads() {
    }
}
