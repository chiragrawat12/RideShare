package com.rideshare.userservice.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationStrategyFactoryTest {

    private RegistrationStrategyFactory factory;

    private PassengerRegistrationStrategy passengerStrategy;
    private DriverRegistrationStrategy driverStrategy;

    @BeforeEach
    void setUp() {
        // MOCK strategies
        passengerStrategy = Mockito.mock(PassengerRegistrationStrategy.class);
        driverStrategy = Mockito.mock(DriverRegistrationStrategy.class);

        // Inject mocks into factory
        factory = new RegistrationStrategyFactory(
                driverStrategy,
                passengerStrategy
        );
    }

    @Test
    void testPassengerStrategySelection() {
        RegistrationStrategy strategy = factory.getStrategy("PASSENGER");
        assertSame(passengerStrategy, strategy);
    }

    @Test
    void testDriverStrategySelection() {
        RegistrationStrategy strategy = factory.getStrategy("DRIVER");
        assertSame(driverStrategy, strategy);
    }

    @Test
    void testInvalidRoleThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                factory.getStrategy("ADMIN"));
    }
}
