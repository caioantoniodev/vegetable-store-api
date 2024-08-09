package tech.dev.vegetablestoreapi.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.dev.vegetablestoreapi.rest.dto.VegetablesDto;
import tech.dev.vegetablestoreapi.service.VegetableStoreService;

@RestController
public class VegetableStoreController {

    private final VegetableStoreService vegetableStoreService;

    public VegetableStoreController(VegetableStoreService vegetableStoreService) {
        this.vegetableStoreService = vegetableStoreService;
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody VegetablesDto vegetablesDto) {
        vegetableStoreService.create(vegetablesDto);
        return ResponseEntity.noContent().build();
    }
}
