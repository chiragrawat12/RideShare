package com.rideshare.payment.paymentgateway;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

@Component("stripeProcessor")
public class StripePaymentProcessor implements PaymentProcessor {

    @Override
    public String createPaymentIntent(long amountInCents, String currency, String description) throws Exception {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amountInCents)
                        .setCurrency(currency)
                        .setDescription(description)
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();  // send this to client to finish card auth
    }
}
