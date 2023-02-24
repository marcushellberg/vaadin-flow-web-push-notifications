package org.vaadin.marcus.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import nl.martijndwars.webpush.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.vaadin.marcus.webpush.WebPushService;
import org.vaadin.marcus.webpush.WebPushToggle;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Web Push")
@Route(value = "")
public class MainView extends VerticalLayout {

    private final WebPushService webPushService;
    private final List<Subscription> subscriptions = new ArrayList<>();

    public MainView(WebPushService webPushService) {
        this.webPushService = webPushService;

        var toggle = new WebPushToggle(webPushService.getPublicKey());
        toggle.addSubscribeListener(e -> {
            subscribe(e.getSubscription());
        });
        toggle.addUnsubscribeListener(e -> {
            unsubscribe(e.getSubscription());
        });

        add(
                new H1("Web Push Notification Demo"),
                toggle
        );
    }

    public void subscribe(Subscription subscription) {
        System.out.println("Subscribed to " + subscription.endpoint);
        this.subscriptions.add(subscription);
    }

    public void unsubscribe(Subscription subscription) {
        System.out.println("Unsubscribed from " + subscription.endpoint);
        subscriptions.remove(subscription);
    }

    @Scheduled(fixedRate = 15000)
    private void sendNotifications() {
        System.out.println("Sending notifications to all subscribers");

        var json = """
        {
          "title": "Server says hello!",
          "body": "It is now: %s"
        }
        """;

        subscriptions.forEach(subscription -> {
            webPushService.sendNotification(subscription, String.format(json, LocalTime.now()));
        });
    }

}
