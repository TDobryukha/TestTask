package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ListOfTickets;

import java.io.File;
import java.io.IOException;

public class TicketsLoader {
    public static ListOfTickets load(File file) throws IOException {
        return new ObjectMapper().findAndRegisterModules().readValue(file, ListOfTickets.class);
    }
}
