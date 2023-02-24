package org.vaadin.marcus.webpush;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.shared.Registration;
import nl.martijndwars.webpush.Subscription;

@Tag("web-push-toggle")
@JsModule("./web-push-toggle.ts")
public class WebPushToggle extends Component {

    public WebPushToggle(String publicKey) {
        setPublicKey(publicKey);
    }

    public void setPublicKey(String publicKey) {
        getElement().setProperty("publicKey", publicKey);
    }

    public void setCaption(String caption) {
        getElement().setProperty("caption", caption);
    }


    // Events

    public static class WebPushSubscriptionEvent extends ComponentEvent<WebPushToggle> {
        private final Subscription subscription;

        public WebPushSubscriptionEvent(WebPushToggle source,
                                        boolean fromClient,
                                        Subscription subscription) {
            super(source, fromClient);
            this.subscription = subscription;
        }

        public Subscription getSubscription() {
            return subscription;
        }
    }

    @DomEvent("web-push-subscribed")
    public static class SubscribeEvent extends WebPushSubscriptionEvent {
        public SubscribeEvent(WebPushToggle source,
                              boolean fromClient,
                              @EventData("event.detail.endpoint") String endpoint,
                              @EventData("event.detail.keys.auth") String auth,
                              @EventData("event.detail.keys.p256dh") String p256dh) {
            super(source, fromClient, new Subscription(endpoint, new Subscription.Keys(p256dh, auth)));
        }
    }

    @DomEvent("web-push-unsubscribed")
    public static class UnsubscribeEvent extends WebPushSubscriptionEvent {

        public UnsubscribeEvent(WebPushToggle source,
                                boolean fromClient,
                                @EventData("event.detail.endpoint") String endpoint,
                                @EventData("event.detail.keys.auth") String auth,
                                @EventData("event.detail.keys.p256dh") String p256dh) {
            super(source, fromClient, new Subscription(endpoint, new Subscription.Keys(p256dh, auth)));
        }
    }

    public Registration addSubscribeListener(ComponentEventListener<SubscribeEvent> listener) {
        return addListener(SubscribeEvent.class, listener);
    }

    public Registration addUnsubscribeListener(ComponentEventListener<UnsubscribeEvent> listener) {
        return addListener(UnsubscribeEvent.class, listener);
    }
}
