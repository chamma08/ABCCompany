package com.example.inventory.service;

import com.example.inventory.dto.InventoryDTO;
import com.example.inventory.model.Inventory;
import com.example.inventory.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class InventoryService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryRepo inventoryRepo;

    public List<InventoryDTO>getall(){
        List<Inventory>inventoryList = inventoryRepo.findAll();
        return modelMapper.map(inventoryList,new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO createInventory(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public InventoryDTO updateInventory(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public String deleteInventory(Integer id){
        inventoryRepo.deleteById(id);
        return ("Deleted" + id);
    }

    public InventoryDTO getItemById(Integer itemId) {
        Inventory item = inventoryRepo.getItemById(itemId);
        return modelMapper.map(item, InventoryDTO.class);
    }
}
