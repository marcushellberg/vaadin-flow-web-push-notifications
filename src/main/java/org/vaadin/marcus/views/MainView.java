package org.vaadin.marcus.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
        var messageInput = new TextField("Message:");
        var sendButton = new Button("Notify all users!");

        add(
                new H1("Web Push Notification Demo"),
                toggle,
                new HorizontalLayout(messageInput, sendButton) {{setDefaultVerticalComponentAlignment(Alignment.BASELINE);}}
        );

        toggle.addSubscribeListener(e -> {
            subscribe(e.getSubscription());
        });
        toggle.addUnsubscribeListener(e -> {
            unsubscribe(e.getSubscription());
        });

        sendButton.addClickListener(e -> webPushService.notifyAll("Message from user", messageInput.getValue()));
    }

    public void subscribe(Subscription subscription) {
        webPushService.subscribe(subscription);
    }

    public void unsubscribe(Subscription subscription) {
        webPushService.unsubscribe(subscription);
    }

}
