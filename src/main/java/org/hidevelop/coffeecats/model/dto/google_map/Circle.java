package org.hidevelop.coffeecats.model.dto.google_map;

import lombok.Getter;

@Getter
public class Circle {
    private final Center center;
    private final double radius;

    public Circle(Center center) {
        this.center = center;
        this.radius = 500.0;
    }
}