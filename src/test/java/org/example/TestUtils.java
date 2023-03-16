package org.example;

import org.example.model.ListOfTickets;
import org.example.model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TestUtils {
    public static ListOfTickets createListOfTicketsForTest() {
        List<Ticket> list = List.of(new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                        LocalDate.of(2018, 5, 12), LocalTime.of(16, 20),
                        LocalDate.of(2018, 5, 12), LocalTime.of(22, 10),
                        "TK", 3, 12400),
                new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                        LocalDate.of(2018, 5, 12), LocalTime.of(17, 20),
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        "S7", 1, 13100),
                new Ticket("TLV", "Тель-Авив", "VVO", "Владивосток",
                        LocalDate.of(2018, 5, 12), LocalTime.of(17, 30),
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        "TK", 1, 20000),
                new Ticket("TLV", "Тель-Авив", "VVS", "Владивосток",
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        LocalDate.of(2018, 5, 13), LocalTime.of(4, 10),
                        "TK", 1, 20000),
                new Ticket("AAA", null, "BBB", null,
                        LocalDate.of(2018, 5, 13), LocalTime.of(4, 10),
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        "TK", 1, 20000),
                new Ticket("AAA", null, "BBB", null,
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        LocalDate.of(2018, 5, 13), LocalTime.of(4, 10),
                        "TK", 1, 20000),
                new Ticket("CCC", null, "DDD", null,
                        null, null,
                        null, null,
                        "TK", 1, 20000),
                new Ticket("EEE", null, "FFF", null,
                        LocalDate.of(2018, 5, 13), LocalTime.of(23, 50),
                        LocalDate.of(2018, 5, 12), LocalTime.of(4, 10),
                        "TK", 1, 20000),
                new Ticket(null, null, null, null,
                        null, LocalTime.of(4, 10),
                        LocalDate.of(2018, 5, 12), LocalTime.of(23, 50),
                        "TK", 1, 20000),
                new Ticket("VVO", "Владивосток", "TLV", "Тель-Авив",
                        LocalDate.of(2018, 5, 12), LocalTime.of(17, 20),
                        LocalDate.of(2018, 5, 12), LocalTime.of(22, 50),
                        "S7", 1, 13100)

        );
        return new ListOfTickets(list);
    }
}
