package io.github.vialdevelopment.tori.api.event;

/**
 * @author cats
 * @since April 23, 2020
 */
public class CancellableEvent extends StagedEvent {

    /**
     * is the event cancelled?
     */
    protected boolean canceled;

    public CancellableEvent() {
    }

    public CancellableEvent(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}