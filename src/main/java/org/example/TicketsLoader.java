package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ListOfTickets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class TicketsLoader {
    public static ListOfTickets load(String fileName) throws IOException {
        try (InputStream inputStream = Main.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            return new ObjectMapper().findAndRegisterModules().readValue(inputStream, ListOfTickets.class);
        }
    }
}
