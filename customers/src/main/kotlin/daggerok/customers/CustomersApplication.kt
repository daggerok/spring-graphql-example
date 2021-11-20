package daggerok.customers

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.messaging.rsocket.RSocketRequester
import org.springframework.messaging.rsocket.retrieveFlux
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class CustomersApplication

fun main(args: Array<String>) {
    runApplication<CustomersApplication>(*args)
}

@Table("customers")
data class Customer(@JsonProperty("id") @Id val id: Long? = null, @JsonProperty("name") val name: String = "")

interface Customers : ReactiveCrudRepository<Customer, Long>

@RestController
class CustomersRestApiResource(private val customers: Customers) {

    @GetMapping("/api/v1/customers")
    fun getCustomers() = customers.findAll()
}

@Configuration
class RSocketClientConfig {

    @Bean
    fun rSocket(rrb: RSocketRequester.Builder) =
        rrb.tcp("127.0.0.1", 7071)
}

data class Order(val id: Long, val customerId: Long)

@Component
class OrderClient(private val rSocket: RSocketRequester) {

    fun getCustomerOrders(customerId: Long) =
        rSocket.route("order.{customerId}", customerId)
            .retrieveFlux<Order>()
}

@Controller
class CustomersGraphqlResource(private val customers: Customers, private val orderClient: OrderClient) {

    // @SchemaMapping(typeName = "Query", field = "customers")
    // fun getCustomers() = customers.findAll()
    // same as:

    // @QueryMapping(name = "customers") // typeName = "Query" is used by default
    // fun queryCustomers() = customers.findAll()
    // same as:

    @QueryMapping // fun name should match to name "customers" then field name can be omitted
    fun customers() = customers.findAll()

    @SchemaMapping(typeName = "Customer", field = "orders")
    fun orders(customer: Customer) =
        orderClient.getCustomerOrders(customer.id!!)
}
