package com.metacoding.order.orders;

import com.metacoding.order.adapter.*;
import com.metacoding.order.adapter.dto.*;
import com.metacoding.order.core.handler.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final DeliveryClient deliveryClient;

    @Transactional
    public OrderResponse createOrder(int userId, int productId, int quantity, Long price, String address) {

        // TODO: 실습 1. 주문 생성 로직 구현
        // 보상트랜잭션을 위한 변수 선언


        try {
            // 1. 주문 생성

            // 최소 주문 금액 검증

            // 2. 상품 재고 차감

            // 3. 배달 생성 

            // 4. 주문 완료

        } catch (Exception e) {
            // 배달 취소
        }
    }

    public OrderResponse findById(int orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));
        return OrderResponse.from(findOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(int orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception404("주문을 찾을 수 없습니다."));
        if (findOrder.getStatus() == OrderStatus.CANCELLED) {
            throw new Exception400("주문이 이미 취소되었습니다.");
        }
        // 상품 재고 복구
        productClient.increaseQuantity(
                new ProductRequest(findOrder.getProductId(), findOrder.getQuantity(), findOrder.getPrice())
        );
        // 배달 취소
        deliveryClient.cancelDelivery(orderId);
        // 주문 취소
        findOrder.cancel();
        return OrderResponse.from(findOrder);
    }
}
