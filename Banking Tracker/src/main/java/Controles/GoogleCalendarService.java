package Controles;



import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Todo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/client.json";

    public static void addEvent(String summary, String description, DateTime startDate, DateTime endDate)
            throws IOException, GeneralSecurityException {
        Calendar service = initializeCalendarService();

        Event event = new Event()
                .setSummary(summary)
                .setDescription(description);

        EventDateTime startDateTime = new EventDateTime()
                .setDateTime(startDate)
                .setTimeZone("Your Time Zone");
        event.setStart(startDateTime);

        EventDateTime endDateTime = new EventDateTime()
                .setDateTime(endDate)
                .setTimeZone("Your Time Zone");
        event.setEnd(endDateTime);

        String calendarId = "primary";
        service.events().insert(calendarId, event).execute();
    }

    private static Calendar initializeCalendarService() throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credentials = getCredentials().createScoped(Collections.singleton(CalendarScopes.CALENDAR));

        return new Calendar.Builder(httpTransport, JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static GoogleCredentials getCredentials() throws IOException {
        try (InputStream credentialsStream = GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (credentialsStream == null) {
                throw new FileNotFoundException("Credentials file not found: " + CREDENTIALS_FILE_PATH);
            }
            return GoogleCredentials.fromStream(credentialsStream);
        } catch (IOException e) {
            // Handle IOException
            throw new IOException("Error reading credentials file: " + e.getMessage(), e);
        }
    }

}
