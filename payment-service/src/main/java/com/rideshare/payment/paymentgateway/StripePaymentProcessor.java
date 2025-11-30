package com.rideshare.payment.paymentgateway;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Component;

@Component("stripeProcessor")
public class StripePaymentProcessor implements PaymentProcessor {

//    public String createPaymentIntent(long amountInCents, String currency, String description) throws Exception {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amountInCents)
//                        .setCurrency(currency)
//                        .setDescription(description)
//                        .build();
//
//        PaymentIntent intent = PaymentIntent.create(params);
//        return intent.getClientSecret();
//    }
@Override
public String createPaymentIntent(long amountInCents, String currency, String description) throws Exception {
    PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency(currency)
                    .setDescription(description)
                    .build();

    PaymentIntent paymentIntent = PaymentIntent.create(params);

    return paymentIntent.getId(); // returns string → matches interface
}
}
