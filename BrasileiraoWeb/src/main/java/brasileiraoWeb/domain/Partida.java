package brasileiraoWeb.domain;

public class Partida {
	private int numero_rodada;
	private int numero_partida;
	private Time time_a;
	private Time time_b;
	private int gols_a;
	private int gols_b;
	private int ca_a;
	private int ca_b;
	private int cv_a;
	private int cv_b;

	public int getNumero_rodada() {
		return numero_rodada;
	}

	public void setNumero_rodada(int numero_rodada) {
		this.numero_rodada = numero_rodada;
	}

	public int getNumero_partida() {
		return numero_partida;
	}

	public void setNumero_partida(int numero_partida) {
		this.numero_partida = numero_partida;
	}

	public int getGols_a() {
		return gols_a;
	}

	public void setGols_a(int gols_a) {
		this.gols_a = gols_a;
	}

	public int getGols_b() {
		return gols_b;
	}

	public void setGols_b(int gols_b) {
		this.gols_b = gols_b;
	}

	public int getCa_a() {
		return ca_a;
	}

	public void setCa_a(int ca_a) {
		this.ca_a = ca_a;
	}

	public int getCa_b() {
		return ca_b;
	}

	public void setCa_b(int ca_b) {
		this.ca_b = ca_b;
	}

	public int getCv_a() {
		return cv_a;
	}

	public void setCv_a(int cv_a) {
		this.cv_a = cv_a;
	}

	public int getCv_b() {
		return cv_b;
	}

	public void setCv_b(int cv_b) {
		this.cv_b = cv_b;
	}
	
	public Time getTime_a() {
		return time_a;
	}

	public void setTime_a(Time time_a) {
		this.time_a = time_a;
	}

	public Time getTime_b() {
		return time_b;
	}

	public void setTime_b(Time time_b) {
		this.time_b = time_b;
	}

}