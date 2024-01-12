package es.wacoco.SpringCamelProject.Camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import com.jayway.jsonpath.JsonPath;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CamelRoutes extends RouteBuilder {
    @Override
    public void configure() {
        // Police Server
        from("direct:police")
                .to("http://localhost:3000/police")
                .log("Data retrieved from CamelController: ${body}")
                .process(new Processor() {
                    /**
                     * @param exchange
                     * @throws Exception
                     */
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String jsonResponse = exchange.getIn().getBody(String.class);

                        // Extracting desired fields from the Police Server response
                        String firstname = JsonPath.read(jsonResponse, "$.firstname");
                        String lastname = JsonPath.read(jsonResponse, "$.lastname");
                        int age = JsonPath.read(jsonResponse, "$.age");
                        String gender = JsonPath.read(jsonResponse, "$.gender");
                        boolean criminalRecord = JsonPath.read(jsonResponse, "$.criminalRecord");

                        // Formatting the extracted data
                        String formattedData = firstname + " " + lastname + ", Age: " + age + ", Gender: " + gender + ", Criminal Record: " + criminalRecord;

                        // Set the formatted data back to the body
                        exchange.getIn().setBody(formattedData);
                    }
                })
                .to("log:myLogger?level=INFO");

        from("direct:company")
                .to("http://localhost:3000/company")
                .log("Data retrieved from Company Server: ${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String jsonResponse = exchange.getIn().getBody(String.class);

                        // Extracting desired fields from the Company Server response
                        String name = JsonPath.read(jsonResponse, "$.name");

                        // Extracting job history information
                        List<Map<String, String>> jobHistory = JsonPath.read(jsonResponse, "$.jobHistory");

                        // Building a filtered string with extracted data
                        StringBuilder filteredData = new StringBuilder("Name: " + name + "\n");

                        // Adding job history information to the filtered data
                        for (Map<String, String> job : jobHistory) {
                            String companyName = job.get("companyName");
                            String position = job.get("position");
                            String duration = job.get("duration");

                            // Appending each field with a new line
                            filteredData.append("Company Name: ").append(companyName).append("\n")
                                    .append("Position: ").append(position).append("\n")
                                    .append("Duration: ").append(duration).append("\n\n");  // Add two newline characters after each job entry
                        }

                        // Set the filtered data back to the body
                        exchange.getIn().setBody(filteredData.toString());
                    }
                })
                .to("log:myLogger?level=INFO");


        // Contact Server
        from("direct:contact")
                .to("http://localhost:3000/contact")
                .log("Data retrieved from Contact Server: ${body}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String jsonResponse = exchange.getIn().getBody(String.class);

                        // Extracting desired fields from the Contact Server response
                        String username = JsonPath.read(jsonResponse, "$.username");
                        String email = JsonPath.read(jsonResponse, "$.email");

                        // Building a filtered string with extracted data
                        String filteredData = "Username: " + username +
                                ", Email: " + email;

                        // Set the filtered data back to the body
                        exchange.getIn().setBody(filteredData);
                    }
                })
                .to("log:myLogger?level=INFO");

        //Criminal Server
        from("direct:criminal")
                .to("http://localhost:3000/criminal")
                .log("Data retrieved from CamelController: ${body}")
                .to("log:myLogger?level=INFO");
    }


}
