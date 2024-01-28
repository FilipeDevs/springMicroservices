package com.filipeDevs.inventoryservice.controller;

import com.filipeDevs.inventoryservice.dto.InventoryResponse;
import com.filipeDevs.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> scanCode) {
        return inventoryService.isInStock(scanCode);
    }
}
