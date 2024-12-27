package com.thymeleaf.CURD.example.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thymeleaf.CURD.example.Entity.Tutorial;
import com.thymeleaf.CURD.example.Repository.TutorialRepository;

@Controller
//@CrossOrigin("*")
public class TutorialController {

	@Autowired
	private TutorialRepository tutorialRepository;
	

	@GetMapping("/tutorials/new")
	public String addTutorial(Model model) {
		
		Tutorial tutorial = new Tutorial();
		tutorial.setPublished(true);
		
		System.out.print("End point executed ");
		model.addAttribute("tutorial", tutorial);
		model.addAttribute("pageTitle", "Create new Tutorial");
		
		
		return "tutorial_form";
	}
	
	@PostMapping("/tutorials/save")
	public String saveTutorial(@ModelAttribute Tutorial tutorial, RedirectAttributes redirectAttributes) {
		System.out.println("save tutorial  started");
		try {
			System.out.println("object id "+tutorial.getId());
			tutorialRepository.save(tutorial);
			System.out.println("saved tutorial");
			System.out.println("object id after save "+tutorial);
			redirectAttributes.addFlashAttribute("message", "The Tutorial has been saved successfully!");
		
		} catch (Exception e) {
			
			redirectAttributes.addAttribute("message", e.getMessage());
		}
		
		return "redirect:/tutorials";
	}
	
	
	@GetMapping("/tutorials")
	public String getAll(Model model, @Param("keyword") String keyword) {
		try {
			
				List<Tutorial> tutorials = new ArrayList<Tutorial>();
			
				if (keyword == null) {
					//tutorialRepository.findAll().forEach(tutorials::add);
					
					tutorials = tutorialRepository.findAll().stream()
									.sorted(Comparator.comparing(Tutorial::getId))
									.collect(Collectors.toList());
					
				}else {
					//tutorialRepository.findByTitleContainingIgnoreCase(keyword).forEach(tutorials::add);
					
					tutorials = tutorialRepository.findByTitleContainingIgnoreCase(keyword).stream()
																.sorted(Comparator.comparing(Tutorial::getId))
																.collect(Collectors.toList());
					model.addAttribute("keyword", keyword);
				}
				
				model.addAttribute("tutorials", tutorials);
			
		} catch (Exception e) {
				model.addAttribute("message", e.getMessage());
		}
		
		return "tutorials";
	}
	
	@GetMapping("/tutorials/{id}")
	public String editTutorial(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("edit tutorial end started");
		try {
				Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutorial not found"));
			
				model.addAttribute("tutorial", tutorial);
				model.addAttribute("pageTitle", "Edit Tutorial (ID: " + id + ")");
				System.out.println("edit tutorial found");
				System.out.println(tutorial);
				return "tutorial_form";
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
				return "redirect:/tutorials";
			}
	
	}
	
	@GetMapping("/tutorials/delete/{id}")
	public String deleteTutorial(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
	
		try {
			tutorialRepository.deleteById(id);
			
			redirectAttributes.addFlashAttribute("message", "The Tutorial with id=" + id + " has been deleted successfully!");
		} catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/tutorials";
	}
	
	@Transactional
	@GetMapping("/tutorials/{id}/published/{status}")
	public String updatePublishedStatus(@PathVariable("id") Long id,
										@PathVariable("status") String status, 
										Model model, 
										RedirectAttributes redirectAttributes) {
		System.out.println ("update status endpoint called");
		
		boolean published = Boolean.parseBoolean(status);
		
		try {
			tutorialRepository.updatePublishedStatus(id, published);
			
			String status_msg = published ? "published" : "disabled";
			String message = "The Tutorial id=" + id + "has been "+ status_msg;
			
			redirectAttributes.addFlashAttribute("message", message);
			System.out.println ("update status endpoint executed");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			System.out.println ("update status endpoint error block executed");
			System.out.println (e.getMessage());
		}
		System.out.println ("Page redirected to tutorials page ");
		return "redirect:/tutorials";
	}
	
	
	
	
	
	
	
}
