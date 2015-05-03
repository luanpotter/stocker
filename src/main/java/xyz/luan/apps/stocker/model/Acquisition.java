package xyz.luan.apps.stocker.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Acquisition implements Serializable {

    private static final long serialVersionUID = -7941248992828222758L;

    @Getter
    private int amount;

    @Getter
    private double value;
}
