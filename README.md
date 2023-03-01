# Example of sending Web Push notifications from Vaadin Flow

## Requirements
- Java 17+
- Node 18+

## Setting the VAPID keys
Run `npx web-push generate-vapid-keys` to generate VAPID keys. 

Then define the following environment variables:
```
vapid.public.key=[public key]
vapid.private.key=[private key]
vapid.subject=[mailto:your@email.com OR https://your-website.com]
```

## Running the application

Run the application by running `Application.java` or with the included Maven wrapper: 

```
./mvnw
```