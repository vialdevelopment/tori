package io.github.vialdevelopment.tori.client.events;

import io.github.vialdevelopment.tori.api.event.StagedEvent;

public class GetItemCooldownEvent extends StagedEvent {
    public Float coolDown = null;

    public GetItemCooldownEvent() {
    }
}
