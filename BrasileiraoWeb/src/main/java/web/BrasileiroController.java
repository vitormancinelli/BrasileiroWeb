package web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import servico.BancoDeDados;

@Controller
public class BrasileiroController {
	
	@GetMapping("/CadastrarCampeonato")
	public String CadastrarCampeonato(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			bd.cadastrarCampeonato();
			return "CadastrarCampeonato";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
	
	@GetMapping("/LimparCampeonato")
	public String LimparCampeonato(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			bd.limparCampeonato();
			return "LimparCampeonato";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}	
	}
	
	@GetMapping("/ListarCampeonato")
	public String ListarCampeonato() {
		return "ListarCampeonato";
	}
	
	 @GetMapping("/CadastrarTime")
	 public String greetingForm(Model model) {
		 try {
			 model.addAttribute("CadastrarTime", new NomeTime());
			 return "CadastrarTime";
		 } catch (Exception e) {
				model.addAttribute("erro", e.getMessage());
				return "Erro";
			} 
	 }

	 @PostMapping("/CadastrarTime")
	 public String greetingSubmit(@ModelAttribute NomeTime CadastrarTime) {
		  return "TimeCadastrado";
	 }
	
	@GetMapping("/ListarTime")
	public String ListarTime(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			model.addAttribute("lista", bd.listarTimes());
			return "ListarTime";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
	
	@GetMapping("/LimparTime")  
	public String LimparTime(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
//			model.addAttribute("lista", bd.limparTime(2));
			return "LimparTime";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}	
	}
	
	@GetMapping("/SortearTimes")
	public String SortearTimes(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			bd.sortearTimes();
			return "SortearTimes";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
	
	@GetMapping("/SortearResultados")
	public String SortearResultados(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			bd.sortearResultados();
			return "SortearResultados";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
	
	@GetMapping("/ListarPartidas")
	public String ListarPartidas(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			model.addAttribute("lista", bd.listarPartidas());
			return "ListarPartidas";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}	
	
	@GetMapping("/VerResultados")
	public String VerResultados(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			model.addAttribute("lista", bd.mostrarClassificaçao());
			return "VerResultados";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
		
	@GetMapping("/VerVencedor")
	public String VerVencedor(Model model) {
		try {
			BancoDeDados bd = new BancoDeDados();
			model.addAttribute("lista", bd.mostrarVencedores());
			return "VerVencedor";
		} catch (Exception e) {
			model.addAttribute("erro", e.getMessage());
			return "Erro";
		}
	}
}