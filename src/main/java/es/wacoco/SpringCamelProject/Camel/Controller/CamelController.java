package es.wacoco.SpringCamelProject.Camel.Controller;



import es.wacoco.SpringCamelProject.Camel.Service.CamelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamelController {

    private final CamelService camelService;
    @Autowired
    public CamelController(CamelService camelService) {
        this.camelService = camelService;
    }
    @GetMapping("/police")
    public String police( ) {
        return camelService.policeRoute();
    }
    @GetMapping("/company")
    public String company( ) {
        return camelService.companyRoute();
    }
    @GetMapping("/contact")
    public String contact( ) {
        return camelService.contactRoute();
    }

}
