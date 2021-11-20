package daggerok.orders

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux

@SpringBootApplication
class OrdersApplication

fun main(args: Array<String>) {
    runApplication<OrdersApplication>(*args)
}

@Table("orders")
data class Order(@JsonProperty("id") val id: Long? = null, @JsonProperty("customerId") val customerId: Long = -1)

interface Orders : ReactiveCrudRepository<Order, Long> {
    fun findAllByCustomerId(@Param("customerId") customerId: Long): Flux<Order>
}

@Controller
class OrdersRsocketResource(private val orders: Orders) {

    @MessageMapping("order.{customerId}")
    fun customerOrder(@DestinationVariable customerId: Long) =
        orders.findAllByCustomerId(customerId)
}
