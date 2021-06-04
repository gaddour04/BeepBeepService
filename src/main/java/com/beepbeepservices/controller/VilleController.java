package com.beepbeepservices.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.dao.VilleRepository;
import com.beepbeepservices.entites.User;
import com.beepbeepservices.entites.Ville;



@Controller
public class VilleController {
	@Autowired
	private VilleRepository villeRepository;
	
	
	
	@GetMapping("/villes")
	public String getAllVille(Model model) {
		model.addAttribute("villes", villeRepository.findAll());
		return "ville/villes";
	}
	@GetMapping(value = "/villes/add")
	public String formAddVille(Model model) {
		model.addAttribute("ville", new Ville());
		return "ville/formVille";
	}
	@PostMapping(value = "/villes/save")
	public String addVille(Model model,@Valid Ville ville,BindingResult bindingResult) {
		if(bindingResult.hasErrors()){ return "formVille"; }
		villeRepository.save(ville);
		return "redirect:/villes";
	}
	@GetMapping(value = "/villes/delete/{id}")
	public String deleteVille(@PathVariable int id) {
		villeRepository.deleteById(id);
		return "redirect:/villes";
	}
	@GetMapping("/villes/update/{id}")
	public String updateVille(@PathVariable int id ,Model model) {
		Optional<Ville> ville=villeRepository.findById(id);
		if(ville.isPresent()) {
			model.addAttribute("ville", ville.get());
		}
		return "ville/formVille";
	}
	
	
}
