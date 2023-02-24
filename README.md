# Example of sending Web Push notifications from Vaadin Flow

## Requirements
- Java 17+
- Node 18+

## Setting the VAPID keys
Run `npx web-push generate-vapid-keys` to generate VAPID keys. 

Add them to `config/local/application.properties` (create if needed) so they don't end up in Git.
In production, provide them as environment variables. 

## Running the application

Run the application by running `Application.java` or with the included Maven wrapper: 

```
./mvnw
```