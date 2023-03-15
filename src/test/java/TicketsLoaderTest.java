import org.example.model.ListOfTickets;
import org.example.TicketsLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketsLoaderTest {
    private static Stream<ListOfTickets> createListOfTicketsForTest() {
        return Stream.of(TestUtils.createListOfTicketsForTest());
    }

    @ParameterizedTest
    @MethodSource("createListOfTicketsForTest")
    public void loaderTestOk(ListOfTickets expected) throws IOException {
        ListOfTickets listOfTickets = TicketsLoader.load(new File("src/test/resources/tickets_test1.json"));
        assertEquals(expected.getTickets(), listOfTickets.getTickets());
    }

    @Test
    public void loaderTestEmptyList() throws IOException {
        ListOfTickets listOfTickets = TicketsLoader.load(new File("src/test/resources/ticketsTestEmptyList.json"));
        assertEquals(0, listOfTickets.getTickets().size());
    }

    @Test
    public void loaderTestFileNotFound() {
        assertThrows(IOException.class, () -> TicketsLoader.load(new File("123.json")));
    }
}