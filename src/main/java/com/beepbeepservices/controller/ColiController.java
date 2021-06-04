package com.beepbeepservices.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.beepbeepservices.dao.ChauffeurRepository;
import com.beepbeepservices.dao.ColiRepository;
import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.dao.VilleRepository;
import com.beepbeepservices.entites.Chauffeur;
import com.beepbeepservices.entites.Coli;
import com.beepbeepservices.entites.User;


@Controller
public class ColiController {
	@Autowired
	ColiRepository coliRepository;
	
	@Autowired
	VilleRepository villeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ChauffeurRepository chauffeurRepository;
	
	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
//	System.out.println(formatter.format(date));
	
	@GetMapping("/colis")
	public String getAllColi(Model model,Principal principal,@RequestParam(name = "page",defaultValue = "0") int p,
			@RequestParam(name = "size",defaultValue = "5")int s,
			@RequestParam(name = "reference",defaultValue = "")String mc){ 
		
		//model.addAttribute("colis", coliRepository.findAll());
		String username =principal.getName();
		User user=userRepository.findByUsername(username);
		String role=user.getClass().getSimpleName();
		if(role.equals("Client")) {
			Page<Coli> colis=coliRepository.chercherClient("%"+mc+"%",username,PageRequest.of(p, s));
			//Page<Produit> produits=produitRep.findAll(PageRequest.of(p, s));
			model.addAttribute("colis", colis.getContent()); 
			int[] pages=new int[colis.getTotalPages()]; 
			model.addAttribute("pages", pages);
			model.addAttribute("size", s);
			model.addAttribute("pageCourante", p);  
			model.addAttribute("reference", mc);
		}
		if(role.equals("Admin")) {
			Page<Coli> colis=coliRepository.chercherAdmin("%"+mc+"%",PageRequest.of(p, s));
			model.addAttribute("colis", colis.getContent()); 
			int[] pages=new int[colis.getTotalPages()]; 
			model.addAttribute("pages", pages);
			model.addAttribute("size", s);
			model.addAttribute("pageCourante", p);  
			model.addAttribute("reference", mc);
		}
		if(role.equals("Chauffeur")) {
			User x=userRepository.findById(user.getId()).get();
			model.addAttribute("colis", coliRepository.findByChauffeur(x));
		}
		
		return "coli/colis";
	}
	
	@GetMapping("/colis/add")
	public String formColis(Model model) {
		model.addAttribute("coli", new Coli());
		model.addAttribute("villes", villeRepository.findAll());
		Date d=new Date();
		System.out.println(d);
		return "coli/formColi";
	}
	
	@PostMapping("/colis/save")
	public String addColi(@Valid @ModelAttribute("coli")Coli coli,BindingResult bindingResult,Principal principal) {
		if(bindingResult.hasErrors()) {
			return "coli/formColi";
		}
		String x="CA";
		Random rand = new Random();   
		int rand_int= rand.nextInt(1000); 
		String reference=x+rand_int; 
		coli.setDateCreated(new Date());
		coli.setReference(reference);
		coli.setUser(userRepository.findByUsername(principal.getName()));
		coli.setEtat("En cours");
		coliRepository.save(coli);
		return"redirect:/colis";
	}
	@GetMapping("colis/delete/{id}")
	public String deleteColi(@PathVariable Long id) {
		coliRepository.deleteById(id);
		return "redirect:/colis";
	}
	
	@GetMapping("colis/edit/{id}")
	public String updateColi(@PathVariable Long id,Model model) {
		Coli coli=coliRepository.findById(id).get();
		model.addAttribute("coli", coli);
		model.addAttribute("villes", villeRepository.findAll());
		return "coli/formColi";
	}
	
	@PostMapping("/colis/update")
	public String editColi(@ModelAttribute("coli")Coli coli,Principal principal) {
		
		coli.setDateUpdated(new Date());
		coliRepository.save(coli);
		return"redirect:/colis";
	}
	
	@GetMapping("/colis/addchauffeur")
	public String addChaufeurToColi(Model model) throws ParseException {
		String x=formatter.format(date);
		String sDate1="2021-05-21";  
	    Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(x);
		//model.addAttribute("colis", coliRepository.findByChauffeurIsNullAndDateCreated(date1));
	    model.addAttribute("colis", coliRepository.findByChauffeurIsNull());
		model.addAttribute("chauffeurs", chauffeurRepository.findAll());
		
		return"coli/formChaufToColi";
	}
	
	@PostMapping("/colis/savechauffeur")
	public String saveChauffeur(Coli coli,Long chauffeur,HttpServletRequest request) {
		try {
			if(request.getParameterValues("id")!=null) {
				for (String id:request.getParameterValues("id")) {
					coli=coliRepository.findById(Long.parseLong(id)).get();
					Chauffeur chau=chauffeurRepository.findById(chauffeur).get();
					coli.setChauffeur(chau);
					chau.getColis().add(coli);
					coliRepository.save(coli);
					userRepository.save(chau); 
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*
		 * Long id=coli.getId(); System.out.println("***************"+id);
		 * coli=coliRepository.findById(id).get();
		 * System.out.println("coli"+coli.toString()); System.out.println(chauffeur);
		 * Chauffeur chau=chauffeurRepository.findById(chauffeur).get();
		 * coli.setChauffeur(chau); chau.getColis().add(coli);
		 * 
		 * coliRepository.save(coli); userRepository.save(chau);
		 * System.out.println("44444444444444444444");
		 * chauffeurRepository.findById(4L).get().getColis().forEach(prod->{System.out.
		 * println(prod.getDestinataire());});
		 */
		chauffeurRepository.findById(chauffeur).get().getColis().forEach(prod->{System.out.
			  println(prod.getDestinataire());});
		
		return "redirect:/colis";
	}
	
	
	

}
