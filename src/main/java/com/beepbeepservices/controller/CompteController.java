package com.beepbeepservices.controller;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.beepbeepservices.dao.CompteRepository;
import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.entites.Compte;

@Controller
public class CompteController {
	@Autowired
	CompteRepository compteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/comptes")
	public String getAllCompte(Model model) {
		model.addAttribute("comptes", compteRepository.findAll());
		
		return"compte/comptes";
	}
	
	@GetMapping("/comptes/form")
	public String getFormCompte(Model model) {
		model.addAttribute("compte", new Compte());
		model.addAttribute("users", userRepository.findAll());
		return"compte/formCompte";
	}
	
	@PostMapping("/comptes/save")
	public String saveCompte(@Valid @ModelAttribute("compte") Compte compte ,BindingResult bindingResult) {
		
		String x="RFH";
		Random rand = new Random();   
		int rand_int= rand.nextInt(1000); 
		String codeCompte=x+rand_int; 
		compte.setCodeCompte(codeCompte);
		compteRepository.save(compte);
		return "redirect:/comptes";
	}
	
	@GetMapping("/comptes/delete/{codeCompte}")
	public String deleteCompte(@PathVariable String codeCompte) {
		compteRepository.deleteById(codeCompte);
		
		
		return "redirect:/comptes";
	}
	@GetMapping("/comptes/update/{codeCompte}")
	public String updateCompte(@PathVariable String codeCompte,Model model) {
		Compte compte=compteRepository.findById(codeCompte).get();
		model.addAttribute("compte", compte);
		model.addAttribute("users", userRepository.findAll());
		return"compte/formCompte";
	}

}
