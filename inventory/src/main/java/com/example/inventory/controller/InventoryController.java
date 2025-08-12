package com.example.inventory.controller;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "api/v1/")
@CrossOrigin
@RestController
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getinventories")
    public List<InventoryDTO> getinventories(){
        return inventoryService.getall();
    }

    @GetMapping("/item/{itemId}")
    public InventoryDTO getItemById(@PathVariable Integer itemId) {
        return inventoryService.getItemById(itemId);
    }

    @PostMapping("/createinventory")
    public InventoryDTO create(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.createInventory(inventoryDTO);
    }

    @PutMapping("/updateinventory")
    public InventoryDTO update(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.updateInventory(inventoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        return inventoryService.deleteInventory(id);
    }

}
