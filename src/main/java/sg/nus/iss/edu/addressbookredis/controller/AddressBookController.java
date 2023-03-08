package sg.nus.iss.edu.addressbookredis.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import sg.nus.iss.edu.addressbookredis.model.Contact;
import sg.nus.iss.edu.addressbookredis.service.AddressbookService;


@Controller
@RequestMapping(path="/form")
public class AddressBookController {


    // @Autowired
    // private ApplicationArguments appArgs;

    // @Value("${addressbookrefactorworkshop13.default.data.dir}")
    // private String defaultDataDir;

    @Autowired
    private AddressbookService addbookService;
    
    @GetMapping
    public String getForm(Model m){
        m.addAttribute("contact", new Contact());
        return "addressbook";
    }


    @PostMapping
    public String savePerson(@Valid Contact contact, BindingResult binding, Model m ){
        if (binding.hasErrors()){
            return "addressbook";
        }

        addbookService.save(contact);
        return "displaycontact";
    }

    @GetMapping(path="{contactId}")
    public String getContactId(Model model, @PathVariable String contactId){
        
        Contact ctc = addbookService.findById(contactId);
        model.addAttribute("contact", ctc);
        return "displaycontact";
    }

    @GetMapping(path="/list")
    public String getAll(Model model, @RequestParam(defaultValue = "0") Integer startIndex){
        List<Contact> ctcs = addbookService.findAll(startIndex);
        model.addAttribute("contacts", ctcs);
        return "allcontacts";
    }

    
}
