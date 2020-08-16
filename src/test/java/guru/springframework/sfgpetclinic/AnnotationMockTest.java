package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AnnotationMockTest {

    @Mock
    Map<String, Object> mapMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMock() {
        mapMock.put("foo", "bar");
    }

    @Test
    void testInlineMocks() {
        Map map = mock(Map.class);  // another way to create Mock object
        assertEquals(0, map.size());
    }
}
