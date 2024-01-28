package com.filipeDevs.inventoryservice.controller;

import com.filipeDevs.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{scanCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String scanCode) {
        return inventoryService.isInStock(scanCode);
    }
}
