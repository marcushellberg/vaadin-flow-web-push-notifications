package org.vaadin.marcus.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import nl.martijndwars.webpush.Subscription;
import org.vaadin.marcus.webpush.WebPushService;
import org.vaadin.marcus.webpush.WebPushToggle;

@PageTitle("Web Push")
@Route(value = "")
public class MainView extends VerticalLayout {

    private final WebPushService webPushService;

    public MainView(WebPushService webPushService) {
        this.webPushService = webPushService;

        var toggle = new WebPushToggle(webPushService.getPublicKey());
        toggle.addSubscribeListener(e -> {
            subscribe(e.getSubscription());
        });
        toggle.addUnsubscribeListener(e -> {
            unsubscribe(e.getSubscription());
        });

        TextField msg = new TextField("Message:");
        Button btn = new Button("Notify all users!",
                e -> webPushService.notifyAll("Message from user", msg.getValue()));

        add(
                new H1("Web Push Notification Demo"),
                toggle,
                msg,
                btn
        );
    }

    public void subscribe(Subscription subscription) {
        webPushService.subscribe(subscription);
    }

    public void unsubscribe(Subscription subscription) {
        webPushService.unsubscribe(subscription);
    }

}
