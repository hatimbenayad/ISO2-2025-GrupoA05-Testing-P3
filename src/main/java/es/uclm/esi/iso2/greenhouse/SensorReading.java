package es.uclm.esi.iso2.greenhouse;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Immutable set of values read from the greenhouse sensors at a given time of day.
 */
public final class SensorReading {
    /** Lowest soil moisture the sensor can report, in percent. */
    public static final double MIN_SOIL_MOISTURE = 0.0;
    /** Highest soil moisture the sensor can report, in percent. */
    public static final double MAX_SOIL_MOISTURE = 100.0;
    /** Lowest temperature the sensor can physically measure, in degrees Celsius. */
    public static final double MIN_TEMPERATURE = -40.0;
    /** Highest temperature the sensor can physically measure, in degrees Celsius. */
    public static final double MAX_TEMPERATURE = 60.0;

    private final double soilMoisturePercentage;
    private final double ambientTemperatureCelsius;
    private final boolean rainForecastedWithin24Hours;
    private final boolean imminentRainForecasted;
    private final LocalTime timeOfDay;

    /**
     * Creates a validated sensor reading.
     *
     * @param soilMoisturePercentage soil moisture, in [0.0, 100.0] percent
     * @param ambientTemperatureCelsius ambient temperature, in [-40.0, 60.0] degrees Celsius
     * @param rainForecastedWithin24Hours whether rain is forecast within the next 24 hours
     * @param imminentRainForecasted whether rain is imminent
     * @param timeOfDay time at which the reading was taken
     * @throws InvalidSensorReadingException if a value is null, not a number or outside the sensor limits
     */
    public SensorReading(double soilMoisturePercentage, double ambientTemperatureCelsius,
                         boolean rainForecastedWithin24Hours, boolean imminentRainForecasted,
                         LocalTime timeOfDay) {
        if (!Double.isFinite(soilMoisturePercentage)
                || soilMoisturePercentage < MIN_SOIL_MOISTURE
                || soilMoisturePercentage > MAX_SOIL_MOISTURE) {
            throw new InvalidSensorReadingException(
                    "soilMoisturePercentage must be finite and in [0.0, 100.0]: " + soilMoisturePercentage);
        }
        if (!Double.isFinite(ambientTemperatureCelsius)
                || ambientTemperatureCelsius < MIN_TEMPERATURE
                || ambientTemperatureCelsius > MAX_TEMPERATURE) {
            throw new InvalidSensorReadingException(
                    "ambientTemperatureCelsius must be finite and in [-40.0, 60.0]: " + ambientTemperatureCelsius);
        }
        if (timeOfDay == null) {
            throw new InvalidSensorReadingException("timeOfDay must not be null");
        }

        this.soilMoisturePercentage = soilMoisturePercentage;
        this.ambientTemperatureCelsius = ambientTemperatureCelsius;
        this.rainForecastedWithin24Hours = rainForecastedWithin24Hours;
        this.imminentRainForecasted = imminentRainForecasted;
        this.timeOfDay = timeOfDay;
    }

    /**
     * Returns the soil moisture.
     *
     * @return the soil moisture, in percent
     */
    public double getSoilMoisturePercentage() {
        return soilMoisturePercentage;
    }

    /**
     * Returns the ambient temperature.
     *
     * @return the temperature, in degrees Celsius
     */
    public double getAmbientTemperatureCelsius() {
        return ambientTemperatureCelsius;
    }

    /**
     * Returns whether rain is forecast within the next 24 hours.
     *
     * @return {@code true} if rain is forecast within 24 hours
     */
    public boolean isRainForecastedWithin24Hours() {
        return rainForecastedWithin24Hours;
    }

    /**
     * Returns whether rain is imminent.
     *
     * @return {@code true} if rain is imminent
     */
    public boolean isImminentRainForecasted() {
        return imminentRainForecasted;
    }

    /**
     * Returns the time at which the reading was taken.
     *
     * @return the time of day
     */
    public LocalTime getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public String toString() {
        return "SensorReading{soilMoisturePercentage=" + soilMoisturePercentage
                + ", ambientTemperatureCelsius=" + ambientTemperatureCelsius
                + ", rainForecastedWithin24Hours=" + rainForecastedWithin24Hours
                + ", imminentRainForecasted=" + imminentRainForecasted
                + ", timeOfDay=" + timeOfDay + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SensorReading that)) {
            return false;
        }
        return Double.compare(soilMoisturePercentage, that.soilMoisturePercentage) == 0
                && Double.compare(ambientTemperatureCelsius, that.ambientTemperatureCelsius) == 0
                && rainForecastedWithin24Hours == that.rainForecastedWithin24Hours
                && imminentRainForecasted == that.imminentRainForecasted
                && timeOfDay.equals(that.timeOfDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(soilMoisturePercentage, ambientTemperatureCelsius, rainForecastedWithin24Hours,
                imminentRainForecasted, timeOfDay);
    }
}
