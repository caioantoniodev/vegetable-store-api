package tech.dev.vegetablestoreapi.domain;

public enum VegetableCategory {
    FRUITS,
    NUTS;

    public static VegetableCategory fromString(String category) {
        try {
            return VegetableCategory.valueOf(category);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}