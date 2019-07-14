package org.sergei.reservation.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergei Visotsky
 */
@Profile({"dev"})
@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(/*HttpServletRequest req*/) {
//        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return "error-404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
