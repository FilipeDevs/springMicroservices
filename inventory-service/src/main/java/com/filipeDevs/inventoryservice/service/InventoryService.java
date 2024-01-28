package com.filipeDevs.inventoryservice.service;

import com.filipeDevs.inventoryservice.model.Inventory;
import com.filipeDevs.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String scanCode) {
        return inventoryRepository.findByScanCode(scanCode).isPresent();
    }
}
