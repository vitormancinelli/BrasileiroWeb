package brasileiraoWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import brasileiraoWeb.domain.Time;
import brasileiraoWeb.service.BancoDeDados;

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
	
	@GetMapping("/CadastrarTime")
	public String CadastrarTime(Model model) {
		model.addAttribute("time", new Time());
		return "CadastrarTime";
	}
	
	@PostMapping("/CadastrarTime")
	public String TimeCadastrado(Model model, Time time) {
		BancoDeDados bd = new BancoDeDados();
			try {
				if(bd.contarTimes() <= 19) {
					bd.cadastrarTime(time.getNome_time());
					return "TimeCadastrado";
				} else {
					return "ErroContarTime";
				}
			} catch (Exception e) {
				model.addAttribute("erro", e.getMessage());
				return "Erro";
			}
	}
	
	@GetMapping("/LimparTime")
	public String LimparTime(Model model) {
		model.addAttribute("time", new Time());
		return "LimparTime";
	}
	
	@PostMapping("/LimparTime")
	public String TimeLimpo(Model model, Time time) {
		BancoDeDados bd = new BancoDeDados();
			try {
				bd.limparTime(time.getNumero_time());
				return "TimeExcluido";
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
			bd.calculoAproveitamento();
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