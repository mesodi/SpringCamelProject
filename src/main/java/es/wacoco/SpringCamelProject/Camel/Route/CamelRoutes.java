package es.wacoco.SpringCamelProject.Camel.Route;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

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
                                 //add more if needed
                                 String filteredData =  JsonPath.read(jsonResponse, "$.username") + ", Age: " + JsonPath.read(jsonResponse, "$.age");

                                 // Set the filtered data back to the body
                                 exchange.getIn().setBody(filteredData);
                             }
                         }
                    )
                .to("log:myLogger?level=INFO");
        // Company Server
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

                            filteredData.append("Company Name: ").append(companyName)
                                    .append(", Position: ").append(position)
                                    .append("\n");
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


}
