package com.rpsa.rpsa.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Map<String, Object> errorAttributes = getErrorAttributes(request);
        model.addAttribute("timestamp", errorAttributes.get("timestamp"));
        model.addAttribute("status", errorAttributes.get("status"));
        model.addAttribute("error", errorAttributes.get("error"));
        model.addAttribute("message", errorAttributes.get("message"));
        model.addAttribute("path", errorAttributes.get("path"));
        return "/error/error";
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        return (Map<String, Object>) request.getAttribute("javax.servlet.error.error_attributes");
    }


    public String getErrorPath() {
        return "/error/error";
    }
}
