package es.wacoco.SpringCamelProject.Camel.Route;

import org.apache.camel.builder.RouteBuilder;

public class CamelRoutesLogic extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:hiringProcess")
                .log(" Received job applications : $ {body}")
                .choice()
                .when(simple("${body.age} > 25 && !${body.hasPoliceCase} && ${body.previousCompanies.size} >= 2"))
                .to("direct: qualifiedApplicant")
                .otherwise()
                .to("direct: unQualifiedApplicant");

        from("direct: qualifiedApplicant")
                .log("Qualified applicant: ${body}")
                .to("mock:qualifiedApplicants");

        from("direct:unQualifiedApplicant")
                .log("UnQualifiedApplicant: ${body}")
                .to("mock:unQualifiedApplicants");
    }
}
