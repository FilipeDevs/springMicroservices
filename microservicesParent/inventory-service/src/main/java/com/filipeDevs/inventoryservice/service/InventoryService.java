package com.filipeDevs.inventoryservice.service;

import com.filipeDevs.inventoryservice.dto.InventoryResponse;
import com.filipeDevs.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> scanCode) {
        // Some simulation for testing time limiter and retry of the circuit breaker
        //        log.info("Wait started");
        //        Thread.sleep(10000);
        //        log.info("Wait ended");


        // Find all inventories from the list of the product's scanCode
        // and check if the inventory of the product still has stock
        return inventoryRepository.findByScanCodeIn(scanCode)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .scanCode(inventory.getScanCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
