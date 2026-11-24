package es.uclm.esi.iso2.greenhouse.cli;

import es.uclm.esi.iso2.greenhouse.IrrigationController;
import es.uclm.esi.iso2.greenhouse.IrrigationMode;
import es.uclm.esi.iso2.greenhouse.SensorReading;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Console interface for collecting a sensor reading and displaying the controller recommendation.
 */
public final class GreenhouseCLI {
    private GreenhouseCLI() {
    }

    /**
     * Runs the interactive greenhouse irrigation console.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            double soilMoisture = readDouble(input, "Soil moisture percentage (0-100): ");
            double temperature = readDouble(input, "Ambient temperature in Celsius (-40 to 60): ");
            boolean rainWithin24Hours = readBoolean(input, "Rain forecast within 24 hours (true/false): ");
            boolean imminentRain = readBoolean(input, "Imminent rain forecast (true/false): ");
            LocalTime timeOfDay = readTime(input, "Time of reading (HH:mm): ");

            SensorReading reading = new SensorReading(
                    soilMoisture, temperature, rainWithin24Hours, imminentRain, timeOfDay);
            IrrigationMode recommendation = new IrrigationController().evaluate(reading);

            System.out.println("Recommendation: " + recommendation.getActionCode());
            System.out.println(recommendation.getDescription());
        } catch (IllegalArgumentException exception) {
            System.err.println("Invalid sensor input: " + exception.getMessage());
        }
    }

    private static double readDouble(Scanner input, String prompt) {
        System.out.print(prompt);
        String value = input.nextLine();
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Enter a numeric value.", exception);
        }
    }

    private static boolean readBoolean(Scanner input, String prompt) {
        System.out.print(prompt);
        String value = input.nextLine();
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw new IllegalArgumentException("Enter true or false.");
    }

    private static LocalTime readTime(Scanner input, String prompt) {
        System.out.print(prompt);
        try {
            return LocalTime.parse(input.nextLine());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Enter time in HH:mm format.", exception);
        }
    }
}
