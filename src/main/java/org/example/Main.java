package org.example;

import org.example.model.ListOfTickets;
import org.example.model.Ticket;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.OptionalDouble;
import java.util.function.Predicate;

public class Main {

    private static final String NO_TICKETS = "Не найдены билеты";
    private static final String STATISTIC_TEMPLATE = "%s %.1f %s\n";

    public static void main(String[] args) {
        ListOfTickets listOfTickets;

        try {
            listOfTickets = TicketsLoader.load("/tickets.json");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ChronoUnit chronoUnit = ChronoUnit.MINUTES;
        TicketsStatistic statistic = new TicketsStatistic(listOfTickets);
        Predicate<Ticket> predicate = TicketsStatistic.isOriginAndDestination("VVO", "TLV");

        OptionalDouble avgTime = statistic.avgFlightTime(predicate, chronoUnit);
        avgTime.ifPresentOrElse(
                value -> System.out.printf(STATISTIC_TEMPLATE,
                        "Среднее время полета между городами Владивосток и Тель-Авив - ",
                        Math.ceil(value),
                        chronoUnit),
                () -> System.out.println(NO_TICKETS)
        );

        OptionalDouble percentile = statistic.percentileFlightTime(predicate, chronoUnit, 90);
        percentile.ifPresentOrElse(
                value -> System.out.printf(STATISTIC_TEMPLATE,
                        "90-й процентиль времени полета между городами Владивосток и Тель-Авив -",
                        Math.ceil(value),
                        chronoUnit),
                () -> System.out.println(NO_TICKETS)
        );

        percentile = statistic.interpolatedPercentileFlightTime(predicate, chronoUnit, 90);
        percentile.ifPresentOrElse(
                value -> System.out.printf(STATISTIC_TEMPLATE,
                        "90-й процентиль (по методу с интерполяцией) времени полета между городами Владивосток и Тель-Авив -",
                        Math.ceil(value),
                        chronoUnit),
                () -> System.out.println(NO_TICKETS)
        );

    }
}