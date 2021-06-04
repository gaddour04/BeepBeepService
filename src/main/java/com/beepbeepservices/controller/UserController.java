package com.beepbeepservices.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.beepbeepservices.dao.CompteRepository;
import com.beepbeepservices.dao.UserRepository;
import com.beepbeepservices.dao.VilleRepository;
import com.beepbeepservices.entites.Admin;
import com.beepbeepservices.entites.Chauffeur;
import com.beepbeepservices.entites.Client;
import com.beepbeepservices.entites.Compte;
import com.beepbeepservices.entites.User;


@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	VilleRepository villeRepository;
	@Autowired 
	CompteRepository compteRepository;
	@Autowired
	PasswordEncoder passwordencoder;
	@Value("${dir.images}")
	String imageDir;
	
	
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		model.addAttribute("users",userRepository.findAll());
		return "user/users";
	}
	// **************************************** Admin *********************
	@GetMapping("/users/addadmin")
	public String getFormUser(Model model) {
		model.addAttribute("user", new Admin());
		model.addAttribute("villes", villeRepository.findAll());
		return "user/admin/formUsers";
	}
	
	@PostMapping("/users/saveadmin")
	public String saveUser( @Valid @ModelAttribute("user") Admin admin,BindingResult bindingResult,@RequestParam(name = "photo") MultipartFile file) throws Exception, IOException {
		if(bindingResult.hasErrors()) {
			return "user/admin/formUsers";
		}
		String password=passwordencoder.encode(admin.getPassword());
		admin.setPassword(password);
		if(!file.isEmpty()) {
			admin.setImage(file.getOriginalFilename());
			userRepository.save(admin);
			file.transferTo(new File(imageDir+admin.getId()));
		}
		
		return "redirect:/users";
	}
	@GetMapping("/users/deleteadmin/{id}")
	public String deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
		return"redirect:/users";
	}
	@GetMapping("/users/updateadmin/{id}")
	public String updateUser(@PathVariable Long id,Model model) {
		model.addAttribute("villes", villeRepository.findAll());
		User user=userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "user/admin/formUsers";
	}
	
	
	// ******************************************* Client *************
	@GetMapping("/users/addclient")
	public String getFormUserClient(Model model) {
		model.addAttribute("user", new Client());
		model.addAttribute("villes", villeRepository.findAll());
		return "user/client/formUsers";
	}
	
	@PostMapping("/users/saveclient")
	public String saveClient( @Valid @ModelAttribute("user") Client client,BindingResult bindingResult,@RequestParam(name = "photo") MultipartFile file) throws Exception, IOException{
		if(bindingResult.hasErrors()) {
			return "user/client/formUsers";
		}
		String password=passwordencoder.encode(client.getPassword());
		client.setPassword(password);
		if(!file.isEmpty()) {
			client.setImage(file.getOriginalFilename());
			userRepository.save(client);
			file.transferTo(new File(imageDir+client.getId()));
			Compte compte=new Compte() ;
			String x="RFH";

			// Generate random integers in range 0 to 999 
			Random rand = new Random();   

			int rand_int= rand.nextInt(1000); 
			String codeCompte=x+rand_int;
			compte.setCodeCompte(codeCompte);
			compte.setDateCreation(new Date());
			compte.setSolde(0.0);
			compte.setUser(client);
			compteRepository.save(compte);
		}
		
		// save compte automatique 
		
		return "redirect:/users";
	}
	@GetMapping("/users/updateclient/{id}")
	public String updateClient(@PathVariable Long id,Model model) {
		model.addAttribute("villes", villeRepository.findAll());
		User user=userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "user/client/formUsers";
	}
	
	// ******************************************* Chauffeur *************
		@GetMapping("/users/addchauffeur")
		public String getFormUserChauffeur(Model model) {
			model.addAttribute("user", new Chauffeur());
			model.addAttribute("villes", villeRepository.findAll());
			return "user/chauffeur/formUsers";
		}
		
		@PostMapping("/users/savechauffeur")
		public String saveChauffeur( @Valid @ModelAttribute("user") Chauffeur chauffeur,BindingResult bindingResult,@RequestParam(name = "photo") MultipartFile file) throws Exception, IOException{
			if(bindingResult.hasErrors()) {
				return "user/chauffeur/formUsers";
			}
			String password=passwordencoder.encode(chauffeur.getPassword());
			chauffeur.setPassword(password);
			if(!file.isEmpty()) {
				chauffeur.setImage(file.getOriginalFilename());
				userRepository.save(chauffeur);
				file.transferTo(new File(imageDir+chauffeur.getId()));
			}
			return "redirect:/users";
		}
		@GetMapping("/users/updatechauffeur/{id}")
		public String updateChauffeur(@PathVariable Long id,Model model) {
			model.addAttribute("villes", villeRepository.findAll());
			User user=userRepository.findById(id).get();
			model.addAttribute("user", user);
			return "user/chauffeur/formUsers";
		}
		
		@RequestMapping(value = "/username")
	    @ResponseBody
	    public String currentUserName(Principal principal) {
	        return principal.getName();
	    }
		
		 @RequestMapping(value = "/getPhoto",produces = MediaType.IMAGE_JPEG_VALUE)
			//pour envoyer des données dans le corp de la réponse
			  @ResponseBody
			  public byte[] getPhoto(Long id) throws Exception {
				  File f=new File(imageDir+id);
			  return IOUtils.toByteArray(new FileInputStream(f)) ; }
	
}
