package es.uclm.esi.iso2.greenhouse;

/**
 * Irrigation decisions that the greenhouse controller can take, each with an action code, an operational
 * description and an urgency level (0 = no action needed, 5 = immediate action needed).
 */
public enum IrrigationMode {
    /** Very low temperature: the pipes are drained to protect them from freezing (below 5 °C). */
    EMERGENCY_DRAINAGE(
            "DRAIN",
            "Emergency drainage: temperature below 5 °C, the irrigation pipes are drained to avoid freezing.",
            5),

    /** The soil is almost dry: water is applied even during the solar radiation hours. */
    CRITICAL_WATERING(
            "CRIT",
            "Critical watering: soil moisture below 10 %, water is applied immediately even at peak sun.",
            4),

    /** Dry soil: a long irrigation cycle is applied. */
    HEAVY_WATERING(
            "HEAVY",
            "Heavy watering: dry soil, a long irrigation cycle is applied.",
            3),

    /** Slightly dry soil: a short irrigation cycle is applied. */
    LIGHT_WATERING(
            "LIGHT",
            "Light watering: slightly dry soil, a short irrigation cycle is applied.",
            2),

    /** Watering is postponed because of the solar radiation hours (12:00 to 16:00). */
    POSTPONED_SOLAR_RADIATION(
            "POSTPONE",
            "Postponed: watering between 12:00 and 16:00 is delayed to avoid evaporation and leaf burn.",
            1),

    /** No watering because rain is expected soon. */
    DO_NOT_WATER(
            "SKIP",
            "Do not water: rain is forecast, so natural rainfall will cover the irrigation needs.",
            0),

    /** The soil has enough moisture: the system waits for the next reading. */
    STANDBY(
            "IDLE",
            "Standby: soil moisture is sufficient, no action is needed until the next reading.",
            0);

    private final String actionCode;
    private final String description;
    private final int urgencyLevel;

    /**
     * Creates an irrigation mode with its metadata.
     *
     * @param actionCode short code sent to the irrigation actuators
     * @param description operational description of the mode
     * @param urgencyLevel urgency from 0 (no action) to 5 (immediate action)
     */
    IrrigationMode(String actionCode, String description, int urgencyLevel) {
        this.actionCode = actionCode;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * Returns the short code sent to the irrigation actuators.
     *
     * @return the action code
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * Returns the operational description of the mode.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the urgency level, from 0 (no action needed) to 5 (immediate action needed).
     *
     * @return the urgency level
     */
    public int getUrgencyLevel() {
        return urgencyLevel;
    }
}
