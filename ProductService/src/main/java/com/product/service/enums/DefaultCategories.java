package com.product.service.enums;

import com.product.service.utils.I18nUtils;

import java.util.UUID;

public enum DefaultCategories {

    ELECTRONICS(UUID.fromString("f05ff978-2746-4b73-8018-098bdf9fcee7"), I18nUtils.getI18nValue("categories.electronics.name")),
    GAMES(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e"), I18nUtils.getI18nValue("categories.games.name")),
    CLOTHES(UUID.fromString("f58b9e43-0320-4bd9-9f9a-a9bdb9a54b19"), I18nUtils.getI18nValue("categories.clothes.name")),
    BOOKS(UUID.fromString("a166d0ac-009e-4dd6-bb1a-d1e173b2c3ba"), I18nUtils.getI18nValue("categories.books.name")),
    FURNITURE(UUID.fromString("124b78da-7526-4e39-94f9-8b4e166a04d1"), I18nUtils.getI18nValue("categories.furniture.name"));

    private final UUID id;
    private final String name;

    DefaultCategories(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public static DefaultCategories getById(UUID id){
        for (DefaultCategories value : DefaultCategories.values()) {
            if (value.getId().equals(id)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Category not found for id: " + id);
    }
}
