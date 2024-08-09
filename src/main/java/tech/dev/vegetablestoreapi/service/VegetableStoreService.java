package tech.dev.vegetablestoreapi.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.dev.vegetablestoreapi.domain.Fruits;
import tech.dev.vegetablestoreapi.domain.Nuts;
import tech.dev.vegetablestoreapi.domain.VegetableCategory;
import tech.dev.vegetablestoreapi.repository.FruitsRepository;
import tech.dev.vegetablestoreapi.repository.NutsRepository;
import tech.dev.vegetablestoreapi.rest.dto.VegetableInfoDto;
import tech.dev.vegetablestoreapi.rest.dto.VegetablesDto;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class VegetableStoreService {

    private static final Logger log = LoggerFactory.getLogger(VegetableStoreService.class);

    private final FruitsRepository fruitsRepository;
    private final NutsRepository nutsRepository;
    private final Map<VegetableCategory, Consumer<List<VegetableInfoDto>>> vegetableCategoryHandlers;

    public VegetableStoreService(FruitsRepository fruitsRepository, NutsRepository nutsRepository) {
        this.fruitsRepository = fruitsRepository;
        this.nutsRepository = nutsRepository;

        this.vegetableCategoryHandlers = Map.of(
                VegetableCategory.FRUITS, this::saveFruits,
                VegetableCategory.NUTS, this::saveNuts
        );
    }

    @Transactional
    public void create(VegetablesDto vegetablesDto) {
        Map<String, List<VegetableInfoDto>> vegetablesByCategory = vegetablesDto.vegetables()
                .stream()
                .collect(Collectors.groupingBy(VegetableInfoDto::category));

        vegetablesByCategory
            .forEach((key, value) -> {
                VegetableCategory vegetableCategory = VegetableCategory.fromString(key);

                Consumer<List<VegetableInfoDto>> vegetableCategoryHandler = vegetableCategoryHandlers.get(vegetableCategory);

                if (vegetableCategoryHandler != null) {
                    vegetableCategoryHandler.accept(value);
                    log.info("Successfully created {}: {}", vegetableCategory, value);
                } else {
                    log.warn("Unknown category: {}", key);
                }
            });
    }

    public void saveFruits(List<VegetableInfoDto> vegetablesInfoDto) {
        List<Fruits> fruits = vegetablesInfoDto.stream()
                .map(vegetableInfoDto -> new Fruits(vegetableInfoDto.name()))
                .toList();

        fruitsRepository.saveAll(fruits);
    }

    public void saveNuts(List<VegetableInfoDto> vegetablesInfoDto) {
        List<Nuts> nuts = vegetablesInfoDto.stream()
                .map(vegetableInfoDto -> new Nuts(vegetableInfoDto.name()))
                .toList();

        nutsRepository.saveAll(nuts);
    }
}
