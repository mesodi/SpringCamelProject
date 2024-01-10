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
                .to("log:myLogger?level=INFO");

        //Company Server
        from("direct:company")
                .to("http://localhost:3000/company")
                .log("Data retrieved from CamelController: ${body}")
                .to("log:myLogger?level=INFO");

        //Contact Server
        from("direct:contact")
                .to("http://localhost:3000/contact")
                .log("Data retrieved from CamelController: ${body}")
                .to("log:myLogger?level=INFO");

        //Criminal Server
        from("direct:criminal")
                .to("http://localhost:3000/criminal")
                .log("Data retrieved from CamelController: ${body}")
                .to("log:myLogger?level=INFO");
    }


}
