package com.integwise.aggregator.domain;

import java.io.Serializable;

/**
* Entity is base interface for all other domain classes.
* 
* This has two methods equals(), hashCode() to ensure all the sub classes will implement these two methods
* 
* @author Kishor Chukka
* 
*/
public interface Entity extends Serializable {
    boolean equals(Entity entity);
    int hashCode();
}