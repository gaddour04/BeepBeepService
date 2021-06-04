package com.beepbeepservices.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.beepbeepservices.dao.ColiRepository;
import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.entites.Coli;
import com.beepbeepservices.entites.User;

@Controller
public class DashboardController {
	@Autowired
	ColiRepository coliRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@GetMapping("/admin/dashboard")
	public String dhashboardAdmin(Model model) {
		
		List<Coli> livre =coliRepository.findByEtat("Livré");
		model.addAttribute("livre",livre.size() );
		List<Coli> encours =coliRepository.findByEtat("En cours");
		model.addAttribute("encours",encours.size() );
		List<Coli> annuler =coliRepository.findByEtat("Annulé");
		model.addAttribute("annuler",annuler.size() );
		model.addAttribute("users", userRepository.findAll());
		return "dashboardadmin";
	}
	
	@GetMapping("/dashboard/{username}")
	public String dhashboardClient(@PathVariable String username, Model model,Principal principal) {
		String username1=principal.getName();
		User client=userRepository.findByUsername(username1);
		List<Coli> livre =coliRepository.findByEtatAndUser("Livré",client);
		model.addAttribute("livre",livre.size() );
		List<Coli> encours =coliRepository.findByEtatAndUser("En cours",client);
		model.addAttribute("encours",encours.size() );
		List<Coli> annuler =coliRepository.findByEtatAndUser("Annulé",client);
		model.addAttribute("annuler",annuler.size() );
		model.addAttribute("user", client);
		return "dashboardclient";
	}
	
	
	
	@GetMapping("/profile/{username}")
	public String profile(Model model,Principal principal) {
		String username1=principal.getName();
		User client=userRepository.findByUsername(username1);
		model.addAttribute("user", client);
		return "profil";
	}
	
	
	@GetMapping("/")
	public String home(Principal principal) {
		String username =principal.getName();
		User user=userRepository.findByUsername(username);
		String role=user.getClass().getSimpleName();
		if(role.equals("Client")) {
			return "redirect:/dashboard/"+username;
		}
		if(role.equals("Admin")) {
			return "redirect:/admin/dashboard";
		}
		return "redirect:/colis";
		
	}
	@RequestMapping(value = "/403")
	public String accessDenied(){
		return "403";
	}

	
	
	
	

}
