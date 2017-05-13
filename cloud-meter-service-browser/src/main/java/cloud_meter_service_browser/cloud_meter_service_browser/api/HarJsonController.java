package cloud_meter_service_browser.cloud_meter_service_browser.api;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
@EnableAutoConfiguration  
@RequestMapping("/harfile") 
public class HarJsonController {  
    
    @RequestMapping("/home")  
    String home() {  
        return "har home!";  
    }  
    @RequestMapping("/get-har-json/{taskId}")  
    String getHarJson(@PathVariable String taskId) {      	
        return "/harfile/" + taskId +".harss";  
    }
       
}