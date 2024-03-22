package Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class T1 {

    public static void main(String[] args) {
        try {
            // ChatGPT API endpoint
            String apiUrl = "https://api.openai.com/v1/engines/davinci/completions";

            // Your OpenAI API key
            String apiKey = "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";

            // Request payload
            String requestData = "{\"role\": \"User\",\"prompt\": \"Hello, ChatGPT!\", \"max_tokens\": 50}";
//            String message = "Tell about yourself";
//            String requestData = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            

            // Create URL object
            URL url = new URL(apiUrl);

            // Create connection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Enable output for the request (send data)
            connection.setDoOutput(true);

            // Write request data to output stream
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestData.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Handle response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    System.out.println("Response: " + response.toString());
                }
            } else {
                // Print error message
                System.out.println("Error: " + connection.getResponseMessage());
            }

            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
