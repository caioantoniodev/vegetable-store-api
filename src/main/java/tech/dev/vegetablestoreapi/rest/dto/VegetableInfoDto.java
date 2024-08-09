package tech.dev.vegetablestoreapi.rest.dto;

public record VegetableInfoDto(String name, String category) {

    @Override
    public String toString() {
        return "VegetableInfoDto{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
