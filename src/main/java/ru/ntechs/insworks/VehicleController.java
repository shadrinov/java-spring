package ru.ntechs.insworks;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleController {

    @ModelAttribute
    public void setVaryResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://polisonline.zhavoronkov.ntechs");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    }
}
