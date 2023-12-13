package rafa.gomez.videogametrivia.shared.event.bus

import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.SimpleEventBus
import org.axonframework.eventhandling.gateway.DefaultEventGateway
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

interface DomainEventPublisher {
    fun publish(events: List<DomainEvent>)
}

@Component
class AxonDomainEventPublisher(private val eventGateway: EventGateway) : DomainEventPublisher {

    override fun publish(events: List<DomainEvent>) {
        eventGateway.publish(events)
    }
}

@Configuration
class AxonConfiguration {

    @Bean
    fun eventBus(): EventBus = SimpleEventBus.builder().build()

    @Bean
    fun eventGateway(): EventGateway = DefaultEventGateway.builder().eventBus(eventBus()).build()
}
