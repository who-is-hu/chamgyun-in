package com.jh.chamgyunin.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PointDto {
    private Integer point;

    private PointDto(Integer point) {
        this.point = point;
    }

    public static PointDto of(Integer point){
        return new PointDto(point);
    }
}
