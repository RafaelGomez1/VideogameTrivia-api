package rafa.gomez.videogametrivia.shared.event.aggregate

import rafa.gomez.videogametrivia.shared.event.bus.DomainEvent

abstract class Aggregate(
    private var domainEvents: MutableList<DomainEvent> = ArrayList()
) {
    fun pullEvents(): List<DomainEvent> {
        val events: List<DomainEvent> = domainEvents
        domainEvents = ArrayList()
        return events
    }

    fun push(event: DomainEvent) {
        domainEvents.add(event)
    }
}
