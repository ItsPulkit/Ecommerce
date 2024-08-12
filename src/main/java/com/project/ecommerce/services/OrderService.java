package com.project.ecommerce.services;

import java.util.List;

import com.project.ecommerce.dtos.CreateOrderRequest;
import com.project.ecommerce.dtos.OrderDto;
import com.project.ecommerce.dtos.OrderUpdateRequest;
import com.project.ecommerce.dtos.PageableResponse;

public interface OrderService {

    // create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    // remove order
    void removeOrder(String orderId);

    // get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    // get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    OrderDto updateOrder(String orderId, OrderUpdateRequest request);

    // order methods(logic) related to order

}
