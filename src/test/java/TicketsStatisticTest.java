import org.example.TicketsStatistic;
import org.example.model.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketsStatisticTest {
    public static TicketsStatistic statistic = new TicketsStatistic(TestUtils.createListOfTicketsForTest());

    @ParameterizedTest
    @CsvSource({"VVO, TLV, 3",
            "VVV, TLV, 0",
            "TLV, VVO, 1",
            ",, 0",
            ", TLV, 0",
            "VVO,, 0",
    })
    public void testPredicateIsOriginAndDestination(String origin, String destination, int expected) {
        List<Ticket> list = statistic.getTickets()
                .stream()
                .filter(TicketsStatistic.isOriginAndDestination(origin, destination))
                .collect(Collectors.toList());
        assertEquals(expected, list.size());
    }

    @ParameterizedTest
    @CsvSource({"VVO, TLV, 357", // 3 билета
            "TLV, VVO, 380", //1 билета
            "TLV, VVS, 260", //1 билет
            "AAA, BBB, 130", //2 билета, в одном из них дата вылета больше даты прилета, время полета - отрицательное
            "EEE, FFF, 0", //1 билет, дата вылета больше даты прилета, время полета - отрицательное
            "CCC, DDD, 0"   //1 ,билет дата вылета и прилета null
    })
    public void testAvgFlightTimeOk(String origin, String destination, double expected) {
        double minutes = Math.ceil(statistic.avgFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES).orElse(-1));
        assertEquals(expected, minutes);

    }

    @ParameterizedTest
    @CsvSource({"VVO, AAA",
            "AAA, VVO",
            "VVS, VVR",
            ","
    })
    public void testAvgFlightTimeNotFoundFlight(String origin, String destination) {
        OptionalDouble minutes = statistic.avgFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES);
        assertEquals(OptionalDouble.empty(), minutes);
    }

    @Test
    public void testAvgFlightTimeException() {
        assertThrows(IllegalArgumentException.class, () -> statistic.avgFlightTime(null, ChronoUnit.MINUTES));
        assertThrows(IllegalArgumentException.class, () -> statistic.avgFlightTime(TicketsStatistic.isOriginAndDestination("A", "B"), null));
        assertThrows(IllegalArgumentException.class, () -> statistic.avgFlightTime(null, null));
    }

    @ParameterizedTest
    @CsvSource({"VVO, TLV, 95, 390.0", //VVO, TLV - 3 билета для расчета процентиля
            "VVO, TLV, 40, 350.0",
            "VVO, TLV, 0, 330.0",
            "VVO, TLV, 100, 390.0",
            "TLV, VVS, 100, 260.0", //TLV, VVS - 1 билет для расчета процентиля
            "TLV, VVS, 0, 260.0",
            "TLV, VVS, 40, 260.0",
            "TLV, VVS, 90, 260.0"
    })
    public void testPercentileFlightTime(String origin, String destination, int percent, double expected) {
        OptionalDouble result = statistic.percentileFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES, percent);
        assertEquals(OptionalDouble.of(expected), result);
    }

    @ParameterizedTest
    @CsvSource({"XXX, YYY, 40", // XXX, YYY - нет таких рейсов, пустой список для расчета процентиля
    })
    public void testPercentileFlightTimeEmptyResult(String origin, String destination, int percent) {
        OptionalDouble result = statistic.percentileFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES, percent);
        assertEquals(OptionalDouble.empty(), result);
    }

    @Test
    public void testPercentileFlightTimeException() {
        assertThrows(IllegalArgumentException.class, () -> statistic.percentileFlightTime(null, ChronoUnit.MINUTES, 0));
        assertThrows(IllegalArgumentException.class, () -> statistic.percentileFlightTime(null, null, 0));
        assertThrows(IllegalArgumentException.class, () -> statistic.percentileFlightTime(TicketsStatistic.isOriginAndDestination("A", "B"), null, 0));
    }

    @ParameterizedTest
    @CsvSource({"VVO, TLV, 95, 386.0", //VVO, TLV - 3 билета для расчета процентиля
            "VVO, TLV, 40, 346.0",
            "VVO, TLV, 0, 330.0",
            "VVO, TLV, 100, 390.0",
            "TLV, VVS, 100, 260.0", //TLV, VVS - 1 билет для расчета процентиля
            "TLV, VVS, 0, 260.0",
            "TLV, VVS, 40, 260.0",
            "TLV, VVS, 90, 260.0"
    })
    public void testInterpolatedPercentileFlightTime(String origin, String destination, int percent, double expected) {
        OptionalDouble result = statistic.interpolatedPercentileFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES, percent);
        assertEquals(OptionalDouble.of(expected), result);
    }

    @ParameterizedTest
    @CsvSource({"XXX, YYY, 40", // XXX, YYY - нет таких рейсов, пустой список для расчета процентиля
    })
    public void testInterpolatedPercentileFlightTimeEmptyResult(String origin, String destination, int percent) {
        OptionalDouble result = statistic.interpolatedPercentileFlightTime(TicketsStatistic.isOriginAndDestination(origin, destination), ChronoUnit.MINUTES, percent);
        assertEquals(OptionalDouble.empty(), result);
    }

    @Test
    public void testInterpolatedPercentileFlightTimeException() {
        assertThrows(IllegalArgumentException.class, () -> statistic.interpolatedPercentileFlightTime(null, ChronoUnit.MINUTES, 0));
        assertThrows(IllegalArgumentException.class, () -> statistic.interpolatedPercentileFlightTime(TicketsStatistic.isOriginAndDestination("A", "B"), null, 0));
        assertThrows(IllegalArgumentException.class, () -> statistic.interpolatedPercentileFlightTime(null, null, 0));
    }

}