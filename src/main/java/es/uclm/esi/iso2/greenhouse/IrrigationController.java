package es.uclm.esi.iso2.greenhouse;

import java.time.LocalTime;

/**
 * Selects an irrigation action from a sensor reading.
 */
public final class IrrigationController {
    private static final double CRITICAL_MOISTURE_THRESHOLD = 10.0;
    private static final double HEAVY_WATERING_THRESHOLD = 30.0;
    private static final double LIGHT_WATERING_THRESHOLD = 60.0;
    private static final double FREEZE_TEMPERATURE_THRESHOLD = 5.0;
    private static final LocalTime PEAK_SOLAR_START = LocalTime.NOON;
    private static final LocalTime PEAK_SOLAR_END = LocalTime.of(16, 0);

    /**
     * Evaluates a sensor reading and returns the highest-priority applicable irrigation mode.
     *
     * @param reading the greenhouse sensor reading
     * @return the recommended irrigation mode
     * @throws InvalidSensorReadingException if {@code reading} is null
     */
    public IrrigationMode evaluate(SensorReading reading) {
        if (reading == null) {
            throw new InvalidSensorReadingException("reading must not be null");
        }

        if (reading.getAmbientTemperatureCelsius() < FREEZE_TEMPERATURE_THRESHOLD) {
            return IrrigationMode.EMERGENCY_DRAINAGE;
        }

        double moisture = reading.getSoilMoisturePercentage();
        if (isPeakSolarTime(reading.getTimeOfDay())) {
            return moisture < CRITICAL_MOISTURE_THRESHOLD
                    ? IrrigationMode.CRITICAL_WATERING
                    : IrrigationMode.POSTPONED_SOLAR_RADIATION;
        }

        if (reading.isImminentRainForecasted()) {
            return IrrigationMode.DO_NOT_WATER;
        }

        if (moisture < HEAVY_WATERING_THRESHOLD) {
            return IrrigationMode.HEAVY_WATERING;
        }
        if (moisture < LIGHT_WATERING_THRESHOLD) {
            return IrrigationMode.LIGHT_WATERING;
        }
        return IrrigationMode.STANDBY;
    }

    private boolean isPeakSolarTime(LocalTime time) {
        return !time.isBefore(PEAK_SOLAR_START) && !time.isAfter(PEAK_SOLAR_END);
    }
}
