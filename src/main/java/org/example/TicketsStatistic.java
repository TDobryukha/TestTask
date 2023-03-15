package org.example;

import lombok.Getter;
import org.example.model.ListOfTickets;
import org.example.model.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TicketsStatistic {
    @Getter
    private final List<Ticket> tickets;

    public TicketsStatistic(ListOfTickets listOfTickets) {
        this(listOfTickets.getTickets());

    }

    public TicketsStatistic(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public OptionalDouble avgFlightTime(Predicate<Ticket> predicate, ChronoUnit chronoUnit) {
        checkArguments(predicate, chronoUnit);
        return this.tickets.stream()
                .filter(predicate)
                .mapToDouble(t -> flightTime(t, chronoUnit))
                .average();
    }

    public OptionalDouble percentileFlightTime(Predicate<Ticket> predicate, ChronoUnit chronoUnit, int percent) {
        checkArguments(predicate, chronoUnit);
        List<Double> flightTimesList = this.tickets.stream()
                .filter(predicate)
                .map(t -> flightTime(t, chronoUnit))
                .sorted()
                .collect(Collectors.toList());
        if (flightTimesList.size() == 0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(percentile(flightTimesList, percent));
    }

    public OptionalDouble interpolatedPercentileFlightTime(Predicate<Ticket> predicate, ChronoUnit chronoUnit, int percent) {
        checkArguments(predicate, chronoUnit);
        List<Double> flightTimesList = this.tickets.stream()
                .filter(predicate)
                .map(t -> flightTime(t, chronoUnit))
                .sorted()
                .collect(Collectors.toList());
        if (flightTimesList.size() == 0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(interpolatedPercentile(flightTimesList, percent));
    }

    public static Predicate<Ticket> isOriginAndDestination(String origin, String destination) {
        return ticket -> origin != null && destination != null && origin.equals(ticket.getOrigin())
                && destination.equals(ticket.getDestination());
    }

    private static double flightTime(Ticket ticket, ChronoUnit chronoUnit) {
        if (ticket.getDepartureDate() == null || ticket.getDepartureTime() == null
                || ticket.getArrivalDate() == null || ticket.getArrivalTime() == null) {
            return 0;
        }
        long time = chronoUnit.between(LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime()),
                LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime()));
        return time > 0 ? time : 0;
    }

    private static double percentile(List<Double> flightTimesList, int percent) {
        if (flightTimesList.size() == 1) {
            return flightTimesList.get(0);
        }
        if (percent == 0) {
            return flightTimesList.get(0);
        }
        int index = (int) Math.ceil(percent * flightTimesList.size() / 100.0);
        return flightTimesList.get(index - 1);
    }

    private static double interpolatedPercentile(List<Double> flightTimesList, int percent) {
        if (flightTimesList.size() == 1) {
            return flightTimesList.get(0);
        }
        if (percent == 100) {
            return flightTimesList.get(flightTimesList.size() - 1);
        } else {
            double doubleIndex = percent * (flightTimesList.size() - 1) / 100.0;
            int intIndex = (int) Math.floor(doubleIndex);
            return flightTimesList.get(intIndex) + doubleIndex % 1 * (flightTimesList.get(intIndex + 1) - flightTimesList.get(intIndex));
        }
    }

    private static void checkArguments(Predicate<Ticket> predicate, ChronoUnit chronoUnit) {
        if (predicate == null || chronoUnit == null) {
            throw new IllegalArgumentException("All arguments must not be null");
        }
    }

}
