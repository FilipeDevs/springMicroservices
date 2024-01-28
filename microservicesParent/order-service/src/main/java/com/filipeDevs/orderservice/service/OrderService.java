package com.filipeDevs.orderservice.service;

import com.filipeDevs.orderservice.dto.InventoryResponse;
import com.filipeDevs.orderservice.dto.OrderLineItemsDto;
import com.filipeDevs.orderservice.dto.OrderRequest;
import com.filipeDevs.orderservice.model.Order;
import com.filipeDevs.orderservice.model.OrderLineItems;
import com.filipeDevs.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItemList(orderLineItems);

        // Get all the scanCodes of the products in the order
        List<String> scanCodes = order.getOrderLineItemList()
                .stream()
                .map(OrderLineItems::getScanCode)
                .toList();

        // Call Inventory Service and place order if product is in stock
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("scanCode", scanCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();// sync call

        assert inventoryResponseArray != null;
        // Return true if all the inventories for the products have enough stock
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock !");
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setScanCode(orderLineItemsDto.getScanCode());
        return orderLineItems;
    }


}
