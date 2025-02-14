package org.hidevelop.coffeecats.model.dto.google_map;

public record Place(
        String name,
        String formattedAddress,
        DisplayName displayName,
        Location location

){

    public record Location (
            double latitude,
            double longitude
    ){}

    public record DisplayName(
            String text
    ){}
}