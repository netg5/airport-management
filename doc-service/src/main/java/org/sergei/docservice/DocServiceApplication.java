package org.sergei.docservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class DocServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocServiceApplication.class, args);
    }

    @Controller
    class DocsController {
        @GetMapping("/")
        public String docsRedirect() {
            return "redirect:swagger-ui.html";
        }
    }
}
