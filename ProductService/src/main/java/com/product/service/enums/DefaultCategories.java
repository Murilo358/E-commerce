package com.product.service.enums;

import java.util.UUID;

public enum DefaultCategories {

    ELECTRONICS(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e")),
    GAMES(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e")),
    CLOTHES(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e")),
    BOOKS(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e")),
    FURNITURE(UUID.fromString("e1799214-84a8-4467-ae1e-eef2b81a211e"));

    private final UUID id;

    DefaultCategories(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
