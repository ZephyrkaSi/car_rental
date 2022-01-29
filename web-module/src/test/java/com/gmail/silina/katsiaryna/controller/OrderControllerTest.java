package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.App;
import com.gmail.silina.katsiaryna.repository.OrderRepository;
import com.gmail.silina.katsiaryna.service.ConvertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.MOCK,
        classes = App.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = {IntegrationConfig.class})
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ConvertService convertService;

    @Test
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/orders").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();
    }

/*

        mockMvc.perform(get("orders")
                        .contentType(MediaType.te))
            .andExpect(status().isOk())
            .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("bob")));*/


    @Test
    void addOrderForm() throws Exception {
        mockMvc.perform(get("/orders/addOrderForm").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();
    }

    @Test
    void saveOrder() throws Exception {
       /* Order order = orderRepository.findById(1L).get();
        OrderStatus initialOrderStatus = order.getOrderStatus();
        String initialDeclineReason = order.getDeclineReason();

        OrderDTO orderDTO = convertService.getDTOFromObject(order, OrderDTO.class);
        orderDTO.setOrderStatus(OrderStatusDTO.builder()
                .id(3L)
                .orderStatus("Status")
                .description("status")
                .build());
        orderDTO.setDeclineReason("Some reason");

        mockMvc.perform(post("/orders/saveOrder")
                        .content(new ObjectMapper().writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

        OrderStatus changedOrderStatus = order.getOrderStatus();
        String changedDeclineReason = order.getDeclineReason();
        Assertions.assertNotEquals(initialOrderStatus, changedOrderStatus);
        Assertions.assertNotEquals(initialDeclineReason, changedDeclineReason);*/
    }

    @Test
    void showUpdateForm() {
    }

    @Test
    void redirectToOrderForm() {
    }

    @Test
    void addOrder() {
      /*  Optional<Order> initOptionalOrder = orderRepository.findById(2L);
        Assertions.assertFalse(initOptionalOrder.isPresent());

        Order order = orderRepository.findById(1L).get();
        OrderDTO orderDTO = convertService.getDTOFromObject(order, OrderDTO.class);
        orderDTO.setId(null);
        orderDTO.setC


        mockMvc.perform(post("/orders/saveOrder")
                        .content(new ObjectMapper().writeValueAsString(OrderDTO.builder()
                                        .
                                .build()))
                .contentType(MediaType.TEXT_HTML).)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

        Optional<Order> resultOptionalOrder = orderRepository.findById(2L);
        Assertions.assertTrue(resultOptionalOrder.isPresent());*/
    }
}