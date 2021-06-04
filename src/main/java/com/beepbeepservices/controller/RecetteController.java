package com.beepbeepservices.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.beepbeepservices.dao.ColiRepository;
import com.beepbeepservices.dao.CompteRepository;
import com.beepbeepservices.dao.RecetteJourRepository;
import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.entites.Coli;
import com.beepbeepservices.entites.Compte;
import com.beepbeepservices.entites.Recettejour;
import com.beepbeepservices.entites.User;

@Controller
public class RecetteController {
	@Autowired
	ColiRepository coliRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RecetteJourRepository recettejourRepository;
	
	@Autowired 
	CompteRepository compteRepository;
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
	@GetMapping("/formrecette/{id}")
	public String dhashboardAdmin(Model model ,@PathVariable Long id) throws Exception {
		String x=formatter.format(date);
	    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(x);
		User u=userRepository.findById(id).get();
		List<Coli> livre =coliRepository.findByEtatAndUserAndDateCreated("Livré",u,date1);
		model.addAttribute("livre",livre.size() );
		List<Coli> annuler =coliRepository.findByEtatAndUserAndDateCreated("Annulé",u,date1);
		model.addAttribute("annuler",annuler.size() );
		double total =coliRepository.montantLivreparJour(u,date1);
		model.addAttribute("total",total );
		model.addAttribute("user", u);
		return "formrecette";
	}
	
	@PostMapping("/recettejour/save")
	public String saveRecette(Recettejour recettejour) throws Exception {
		String x=formatter.format(date);
	    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(x);
	    recettejour.setDate(date1);
		recettejourRepository.save(recettejour);
		User Client=recettejour.getUser();
		
		Compte c=compteRepository.findByUser(Client);
		double montant1=c.getSolde();
		double montantNouveau=montant1+recettejour.getMontant();
		c.setSolde(montantNouveau);
		compteRepository.save(c);
		
		return"redirect:/comptes";
	}

}
