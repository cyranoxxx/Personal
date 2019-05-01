package com.golhan.person.controller;

import com.golhan.person.entity.Person;
import com.golhan.person.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/")
   public String showIndex(Model model){
        model.addAttribute("persons", personService.findAllPerson());
        return "index";
   }

    @RequestMapping(value="/new",method= RequestMethod.GET)
    public String createPerson() {
        return "createPerson";
    }

    @ModelAttribute
    public Person initModel() {
        return new Person();
    }

    @RequestMapping(value="/new",method= RequestMethod.POST)
    public String handleFormCreate(@ModelAttribute @Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            return "createPerson";
        }
        personService.savePerson(person);
        redirectAttributes.addFlashAttribute("message", "Person created with id :" + person.getId());
        return "redirect:/";
   }

    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String loadPerson(@PathVariable Long id, ModelMap modelMap) {
        Person person = personService.findPersonid(id).get();
        modelMap.put("person", person);
        return "updatePerson";
    }

    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String handleFormSubmit(@PathVariable Long id, @ModelAttribute @Valid Person person, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "updatePerson";
        }
        Person dbInstance = personService.findPersonid(id).get();
        dbInstance.setName(person.getName());
        personService.updatePerson(dbInstance);
        redirectAttributes.addFlashAttribute("message","Person updated with id :" +  id);
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
    public String loadEmployee(@PathVariable Long id, ModelMap modelMap) {
        Person person = personService.findPersonid(id).get();
        modelMap.put("person", person);
        return "deletePerson";
    }

    @RequestMapping(value="/delete/{id}",method=RequestMethod.POST)
    public String handleFormSubmit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        personService.deletePerson(id);
        redirectAttributes.addFlashAttribute("message", "Person deleted with id:" + id);
        return "redirect:/";
    }
}
