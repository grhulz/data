package space.janiekitty.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import space.janiekitty.data.model.Data;
import space.janiekitty.data.service.DataService;

@Controller
public class DataController {
    @GetMapping("/data")
    public String dataForm(Model model) {
        model.addAttribute("data", new Data());
        return "data";
    }

    @PostMapping("/data")
    public String dataSubmit(@ModelAttribute Data data) {
        DataService dataService = new DataService(data);
        data = dataService.execute();
        return "results";
    }
}
