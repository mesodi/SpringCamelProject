package es.wacoco.SpringCamelProject.Camel.Service;

import es.wacoco.SpringCamelProject.Camel.Controller.CamelController;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CamelService {
    private static final Logger logger = LoggerFactory.getLogger(CamelController.class);
    private final CamelContext camelContext;
    @Autowired
    public CamelService(CamelContext camelContext) {
        this.camelContext = camelContext;
    }
    public String policeRoute() {
        try {

            // Trigger the Camel route using ProducerTemplate
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            // Sending a message to direct:police to initiate the route
            String response = producerTemplate.requestBody("direct:police", null, String.class);

            return "Camel route processing initiated! Response from police server: \n" + response + "\n Date: " + getFormattedTimestamp();
        } catch (Exception e) {
            // Log the exception or handle it in a way that provides more details
            e.printStackTrace(); // For logging to console

            return "Error occurred during info from police: " + e.getMessage();
        }
    }
    public String companyRoute() {
        try {

            // Trigger the Camel route using ProducerTemplate
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            // Sending a message to direct:company to initiate the route
            String response = producerTemplate.requestBody("direct:company", null, String.class);

            return "Camel route processing initiated! Response from company server: \n" + response + "\n Date: " + getFormattedTimestamp();
        } catch (Exception e) {
            // Log the exception or handle it in a way that provides more details
            e.printStackTrace(); // For logging to console

            return "Error occurred during collect info from company: " + e.getMessage();
        }
    }
    public String contactRoute() {
        try {
            // Trigger the Camel route using ProducerTemplate
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
            // Sending a message to direct:contact to initiate the route
            String response = producerTemplate.requestBody("direct:contact", null, String.class);
            return "Camel route processing initiated! Response from contact server: \n" + response + "\n Date: " + getFormattedTimestamp();
        } catch (Exception e) {
            // Log the exception or handle it in a way that provides more details
            e.printStackTrace(); // For logging to console

            return "Error occurred during getting info from user contact: " + e.getMessage();
        }
    }
    public String qualifiedApplicantsRoute() {
        try{
            ProducerTemplate producerTemplate=camelContext.createProducerTemplate();
            String response=producerTemplate.requestBody("direct:qualifiedApplicants",null, String.class);
            return "Camel route processing initiated! Response from qualifiedApplicants server:\n" + response + "\n Date:" +getFormattedTimestamp();
        }catch (Exception e){
            e.printStackTrace();
            return "Error occured during selecting the applicants:" +e.getMessage();
        }
    }

    private String getFormattedTimestamp() {
        // Get the current timestamp using a custom formatter without milliseconds
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(customFormatter);
    }


}
