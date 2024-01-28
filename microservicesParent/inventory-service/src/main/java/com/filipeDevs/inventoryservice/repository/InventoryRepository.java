package com.filipeDevs.inventoryservice.repository;

import com.filipeDevs.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByScanCode(String scanCode);
}
