package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.dtos.CityPassengerCountDTO;
import com.fastturtle.raahiReserve.enums.BookingStatus;
import com.fastturtle.raahiReserve.enums.TimeSlot;
import com.fastturtle.raahiReserve.models.Booking;
import com.fastturtle.raahiReserve.models.BusRoute;
import com.fastturtle.raahiReserve.models.ChatRequest;
import com.fastturtle.raahiReserve.repositories.BookingRepository;
import com.fastturtle.raahiReserve.repositories.PassengerRepository;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TestChatController
{
    private final AzureOpenAiChatModel azureOpenAiChatModel;

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;

    public TestChatController(AzureOpenAiChatModel azureOpenAiChatModel, BookingRepository bookingRepository, PassengerRepository passengerRepository)
    {
        this.azureOpenAiChatModel = azureOpenAiChatModel;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request)
    {

        // For using gpt-35-turbo, DON'T use the same key as you used for gpt-4o
        // Use the 2nd key present in azure portal
        Prompt prompt = new Prompt(request.getPrompt());
        ChatResponse chatResponse = azureOpenAiChatModel.call(prompt);
        return chatResponse.getResults().get(0).getOutput().getText();
    }

    @GetMapping("/bookingSummary")
    public String bookingSummary()
    {
        long totalBookings = bookingRepository.count();
        long cancelledBookingsCount = bookingRepository.countByBookingStatus(BookingStatus.CANCELLED);
        List<Object[]> topRoutesWithBookingCount = bookingRepository.findTopRoutesWithBookingCount()
                .stream()
                .limit(2)
                .toList();

        Map<BusRoute, Long> topRoutesMap = new LinkedHashMap<>();

        for (Object[] row : topRoutesWithBookingCount) {
            BusRoute route = (BusRoute) row[0];
            Long count = (Long) row[1];
            topRoutesMap.put(route, count);
        }

        StringBuilder top2RouteAggregated = new StringBuilder();

        for (Map.Entry<BusRoute, Long> entry : topRoutesMap.entrySet()) {
            top2RouteAggregated.append(
                    entry.getKey().getRoute().getSource())
                    .append(" to ").append(entry.getKey().getRoute().getDestination())
                    .append(" (").append(entry.getValue()).append(" bookings)");
            top2RouteAggregated.append("\n");
        }

        List<Booking> bookings = bookingRepository.findAll();

        int morningBookings = 0, afternoonBookings = 0, eveningBookings = 0, nightBookings = 0;
        int morningTravels = 0, afternoonTravels = 0, eveningTravels = 0, nightTravels = 0;

        if(!bookings.isEmpty()) {
            for(Booking b : bookings) {
                LocalTime bookingTime = LocalTime.of(b.getBookingDateTime().getHour(), b.getBookingDateTime().getMinute());
                LocalTime travelTime = LocalTime.of(b.getTravelDateTime().getHour(), b.getTravelDateTime().getMinute());
                switch (findSlotOfTime(bookingTime)) {
                    case MORNING -> morningBookings++;
                    case AFTERNOON -> afternoonBookings++;
                    case EVENING -> eveningBookings++;
                    default -> nightBookings++;
                }

                switch (findSlotOfTime(travelTime)) {
                    case MORNING -> morningTravels++;
                    case AFTERNOON -> afternoonTravels++;
                    case EVENING -> eveningTravels++;
                    default -> nightTravels++;
                }

            }
        }

        Map<String, Integer> slotBookings = new HashMap<>();
        slotBookings.put("Morning", morningBookings);
        slotBookings.put("Afternoon", afternoonBookings);
        slotBookings.put("Evening", eveningBookings);
        slotBookings.put("Night", nightBookings);

        Map.Entry<String, Integer> maxEntryBooking = Collections.max(slotBookings.entrySet(), Map.Entry.comparingByValue());

        String maxTimeSlotBooking = maxEntryBooking.getKey();
        int maxSlotValueBooking = maxEntryBooking.getValue();

        Map<String, Integer> slotTravels = new HashMap<>();
        slotTravels.put("Morning", morningTravels);
        slotTravels.put("Afternoon", afternoonTravels);
        slotTravels.put("Evening", eveningTravels);
        slotTravels.put("Night", nightTravels);

        Map.Entry<String, Integer> maxEntryTravel = Collections.max(slotTravels.entrySet(), Map.Entry.comparingByValue());

        String maxTimeSlotTravel = maxEntryTravel.getKey();
        int maxSlotValueTravel = maxEntryTravel.getValue();

        System.out.println("Total bookings: " + totalBookings);
        System.out.println("Cancelled bookings count: " + cancelledBookingsCount);

        System.out.println("Max time slot of travel: " + maxTimeSlotTravel);
        System.out.println("Max time slot of booking: " + maxTimeSlotBooking);

        List<CityPassengerCountDTO> countOfPassengersBelongingToMetroCities = passengerRepository.countPassengersFromMetroCities(
                List.of("Bangalore", "Mumbai", "Delhi"));

        long totalCountOfPassengers = passengerRepository.count();

        StringBuilder metroCityPassengerString = new StringBuilder();

        for(CityPassengerCountDTO countDTO : countOfPassengersBelongingToMetroCities) {
            String cityName = countDTO.getCityName();
            Long passengerCount = countDTO.getPassengerCount();
            double percentage = ((double)passengerCount / totalCountOfPassengers) * 100D;
            percentage = Math.round(percentage * 100.0) / 100.0;
            metroCityPassengerString.append(cityName)
                    .append(" - ")
                    .append(percentage)
                    .append("\n");
        }

        System.out.println("Metro city aggregation: " + metroCityPassengerString);

        String statsTextHardCoded = """
            Total Bookings: 312
            Cancellations: 21
            Top Routes:
            - Delhi to Jaipur (86 bookings)
            - Bangalore to Hyderabad (72 bookings)
            Preferred Times:
            - Travel Time: Morning
            - Booking Time: Evening
            Bus Type Preference:
            - AC Sleeper: 164
            - Non-AC Seater: 92
            Seat Preference:
            - Window: 65%
            - Aisle: 20%
            """;

        String statsText = String.format("""
                Total Bookings: %d
                Cancellations: %d
                Top Routes:
                %s
                Preferred Times:
                - Travel Time: %s
                - Booking Time: %s
                Percentage of travellers belonging to Metro cities:
                - %s
                """,
                totalBookings,
                cancelledBookingsCount,
                top2RouteAggregated,
                maxTimeSlotBooking,
                maxTimeSlotTravel,
                metroCityPassengerString

        );

        String promptString = "Please write a 2-3 line friendly summary of these bus booking stats. Please don't use markdown formatting:\n" + statsText;

        // For using gpt-35-turbo, DON'T use the same key as you used for gpt-4o
        // Use the 2nd key present in azure portal
        Prompt prompt = new Prompt(promptString);
        ChatResponse chatResponse = azureOpenAiChatModel.call(prompt);
        return chatResponse.getResults().get(0).getOutput().getText();
    }

    private TimeSlot findSlotOfTime(LocalTime time) {
        if(!time.isBefore(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
            return TimeSlot.MORNING;
        } else if(!time.isBefore(LocalTime.of(12, 0)) && time.isBefore(LocalTime.of(18, 0))) {
            return TimeSlot.AFTERNOON;
        } else if(!time.isBefore(LocalTime.of(18, 0))) {
            return TimeSlot.EVENING;
        } else
            return TimeSlot.NIGHT;

    }

}
