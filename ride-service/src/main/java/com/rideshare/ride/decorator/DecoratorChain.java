package com.rideshare.ride.decorator;

/**
 * Interface for decorator chain pattern
 * Allows composing multiple decorators
 */
public interface DecoratorChain {

    RideDecorator chain(RideDecorator decorator);
}