package dannik.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AlgorithmsApplicationTests {

    @Test
    void contextLoads() {
        assertEquals(Boolean.TRUE, Boolean.valueOf("true"));
    }

}
