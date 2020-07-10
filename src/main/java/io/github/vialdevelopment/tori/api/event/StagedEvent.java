package io.github.vialdevelopment.tori.api.event;

/**
 * @author cats
 * @since April 23, 2020
 */
public class StagedEvent {
    /**
     *The eventstage
     */
    protected EventStage eventStage;

    public StagedEvent() {
    }

    public StagedEvent(EventStage eventStage) {
        this.eventStage = eventStage;
    }

    public EventStage getStage() {
        return this.eventStage;
    }
}
