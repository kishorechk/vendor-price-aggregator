package com.integwise.aggregator.domain;

import java.io.Serializable;

public interface Entity extends Serializable {
    boolean equals(Entity entity);
    int hashCode();
}