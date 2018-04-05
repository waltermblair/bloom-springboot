import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import java.io.IOException;

@Controller
@EnableAutoConfiguration
public class AppController {
    Filter f = new Filter();

    // https://springframework.guru/spring-requestmapping-annotation/
    // https://spring.io/guides/gs/actuator-service/
    @PutMapping(value = "/bloom")
    @ResponseBody
    public JSONObject storeURL(@RequestParam(value = "url") String url) throws IOException {
        System.out.println("URL is "+url);
        return f.storeURL(url);
    }

    @RequestMapping(value = "/bloom", method=RequestMethod.GET)
    @ResponseBody
    public JSONObject getParsed(@RequestParam(value = "url") String url,
                                @RequestParam(value = "contains") String contains) throws IOException {
        System.out.println("Full URL is "+url+" and Query is "+contains);
        return f.queryFilter(url, contains);
    }

}
