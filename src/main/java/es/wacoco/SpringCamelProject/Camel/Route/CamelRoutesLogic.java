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

        from("direct:qualifiedApplicant")
                .doTry().setHeader("subject", simple("JavaInUse Invitation111"))
                .setHeader("to", simple("robkaya@icloud.com"))
                .to("smtps://smtp.gmail.com:465?username=berntson.hh@gmail.com&password=rvtygxpmqxzoqlfv");

        from("direct:unQualifiedApplicant")
                .doTry().setHeader("subject", simple("JavaInUse Invitation111"))
                .setHeader("to", simple("robkaya@icloud.com"))
                .to("smtps://smtp.gmail.com:465?username=berntson.hh@gmail.com&password=rvtygxpmqxzoqlfv");
    }
}
