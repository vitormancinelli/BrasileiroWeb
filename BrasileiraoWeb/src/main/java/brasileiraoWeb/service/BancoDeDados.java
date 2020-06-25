package brasileiraoWeb.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import brasileiraoWeb.domain.Partida;
import brasileiraoWeb.domain.Time;

public class BancoDeDados {

	public Connection getConnection() {
		Connection conn = null;
		String servidor = "jdbc:mysql://localhost:3306/brasileiro";
		String usuario = "root";
		String senha = "Manci_gamer5";
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(servidor, usuario, senha);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
		return conn;
	}

	public void alterarPartida(int partida, int gols_a, int gols_b, int ca_a, int ca_b, int cv_a, int cv_b) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		String erro;
		try {
			if (gols_a >= 0 && gols_b >= 0 && ca_a >= 0 && ca_b >= 0 && cv_a >= 0 && cv_b >= 0) {
				if (gols_a <= 5 && gols_b <= 5 && ca_a <= 3 && ca_b <= 3 && cv_a <= 2 && cv_b <= 2) {
					if (partida < 380 && partida > 0) {
						Connection conn = getConnection();
						String query = "SELECT * FROM partida WHERE numero_partida = " + partida + ";";
						stmt = conn.createStatement();
						rs = stmt.executeQuery(query);
						rs.next();
		
						Time timeA = new Time();
						Time timeB = new Time();
						timeA.setNumero_time(rs.getInt("time_a"));
						timeB.setNumero_time(rs.getInt("time_b"));
		
						int golsA = gols_a - rs.getInt("gols_a");
						int golsB = gols_b - rs.getInt("gols_b");
						int indice = golsA - golsB;
		
						if (indice > 0) {
							timeA.setSaldo_gols(indice);
							timeB.setSaldo_gols(-indice);
						} else if (indice < 0) {
							timeA.setSaldo_gols(-indice);
							timeB.setSaldo_gols(indice);
						} else {
							timeA.setSaldo_gols(0);
							timeB.setSaldo_gols(0);
						}
		
						timeA.setCa(ca_a - rs.getInt("ca_a"));
						timeA.setCv(cv_a - rs.getInt("cv_a"));
						timeB.setCa(ca_b - rs.getInt("ca_b"));
						timeB.setCv(cv_b - rs.getInt("cv_b"));
		
						timeA.setPontos(0);
						timeB.setPontos(0);
						timeA.setEmpates(0);
						timeB.setEmpates(0);
						timeA.setVitorias(0);
						timeB.setVitorias(0);
						timeA.setDerrotas(0);
						timeB.setDerrotas(0);
		
						if (gols_a > gols_b) {
							if (rs.getInt("gols_a") > rs.getInt("gols_b")) { // time A venceu nos dois jogos
							} else if (rs.getInt("gols_a") < rs.getInt("gols_b")) { // time A perdeu 1ª e venceu 2ª
								timeA.setPontos(3);
								timeB.setPontos(-3);
								timeA.setVitorias(1);
								timeB.setVitorias(-1);
								timeA.setDerrotas(-1);
								timeB.setDerrotas(1);
							} else if (rs.getInt("gols_a") == rs.getInt("gols_b")) { // time A empatou 1ª e venceu 2ª
								timeA.setPontos(2);
								timeB.setPontos(-1);
								timeA.setVitorias(1);
								timeB.setDerrotas(1);
								timeA.setEmpates(-1);
								timeB.setEmpates(-1);
							}
						} else if (gols_a < gols_b) {
							if (rs.getInt("gols_a") > rs.getInt("gols_b")) { // time A venceu 1ª e perdeu 2ª
								timeA.setPontos(-3);
								timeB.setPontos(3);
								timeA.setVitorias(-1);
								timeB.setVitorias(1);
								timeA.setDerrotas(1);
								timeB.setDerrotas(-1);
							} else if (rs.getInt("gols_a") < rs.getInt("gols_b")) { // time A perdeu os 2ª
							} else if (rs.getInt("gols_a") == rs.getInt("gols_b")) { // time A empatou 1ª e perdeu 2ª
								timeA.setPontos(-1);
								timeB.setPontos(2);
								timeB.setVitorias(1);
								timeA.setDerrotas(1);
								timeA.setEmpates(-1);
								timeB.setEmpates(-1);
							}
						} else if (gols_a == gols_b) {
							if (rs.getInt("gols_a") > rs.getInt("gols_b")) { // time A venceu 1ª e empatou 2ª
								timeA.setPontos(-2);
								timeB.setPontos(1);
								timeA.setVitorias(-1);
								timeB.setDerrotas(-1);
								timeA.setEmpates(1);
								timeB.setEmpates(1);
							} else if (rs.getInt("gols_a") < rs.getInt("gols_b")) { // time A perdeu 1ª e empatou 2ª
								timeA.setPontos(1);
								timeB.setPontos(-2);
								timeB.setVitorias(-1);
								timeA.setDerrotas(-1);
								timeA.setEmpates(1);
								timeB.setEmpates(1);
							} else if (rs.getInt("gols_a") == rs.getInt("gols_b")) {
							}
						}
		
						query = "UPDATE partida SET gols_a = " + gols_a + ", gols_b = " + gols_b + ", ca_a = " + ca_a
								+ ", ca_b = " + ca_b + ", cv_a = " + cv_a + ", cv_b = " + cv_b + " WHERE numero_partida = "
								+ partida + ";";
						stmt.executeUpdate(query);
		
						query = "UPDATE time SET pontos = pontos + " + timeA.getPontos() + ", vitorias = vitorias + "
								+ timeA.getVitorias() + ", derrotas = derrotas + " + timeA.getDerrotas()
								+ ", empates = empates + " + timeA.getEmpates() + ", gols_pro = gols_pro + " + golsA
								+ ", gols_contra = gols_contra + " + golsB + ", saldo_gols = saldo_gols + "
								+ timeA.getSaldo_gols() + ", ca = ca + " + timeA.getCa() + ", cv = cv + " + timeA.getCv()
								+ " WHERE numero_time = " + timeA.getNumero_time() + ";";
						stmt.executeUpdate(query);
		
						query = "UPDATE time SET pontos = pontos + " + timeB.getPontos() + ", vitorias = vitorias + "
								+ timeB.getVitorias() + ", derrotas = derrotas + " + timeB.getDerrotas()
								+ ", empates = empates + " + timeB.getEmpates() + ", gols_pro = gols_pro + " + golsB
								+ ", gols_contra = gols_contra + " + golsA + ", saldo_gols = saldo_gols + "
								+ timeB.getSaldo_gols() + ", ca = ca + " + timeB.getCa() + ", cv = cv + " + timeB.getCv()
								+ " WHERE numero_time = " + timeB.getNumero_time() + ";";
						stmt.executeUpdate(query);
					} else {
						erro = "A partida a ser alterada não pode ser encontra, por favor insira um valor entre 1 e 380";
						System.out.println(erro);
						throw new Exception(erro);
					} 
				} else {
					erro = "Valor a ser alterado na tabela não pode ser maior do que 5 para gols, nem maior que 3 para cartões amarelos e nem maior que 2 para cartões vermelhos";
					System.out.println(erro);
					throw new Exception(erro);	
				}
			} else {
				erro = "Valor a ser alterado na tabela não pode ser menor que zero";
				System.out.println(erro);
				throw new Exception(erro);
			}
		} catch (Exception e) {
			erro = "Erro: " + e.getMessage();
			System.out.println(erro);
			throw new Exception(erro);
		}
	}

	public void listarCampeonatos() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Connection conn = getConnection();
			if (conn != null) {
				String query = "SELECT * FROM campeonato";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					System.out.println("turnos: " + rs.getInt("qtd_turnos") + "  rodadas: " + rs.getInt("qtd_rodadas")
							+ "  partidas: " + rs.getInt("qtd_partidas") + "  clubes: " + rs.getInt("qtd_clubes"));
				}
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao lista Campeonato.");
		}
	}

	public ArrayList<Time> listarTimes() throws Exception {
		ArrayList<Time> lista = new ArrayList<Time>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM time";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Time t = new Time();
				t.setNome_time(rs.getString("nome_time"));
				t.setNumero_time(rs.getInt("numero_time"));

				lista.add(t);
				System.out.println("time " + rs.getInt("numero_time") + ": " + rs.getString("nome_time"));
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao lista times.");
		}
		return lista;
	}

	public void limparCampeonato() throws Exception {
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			String query;

			query = "delete from brasileiro.partida";
			stmt.executeUpdate(query);
			query = "delete from brasileiro.rodada";
			stmt.executeUpdate(query);
			query = "delete from brasileiro.turno";
			stmt.executeUpdate(query);
			query = "delete from brasileiro.campeonato";
			stmt.executeUpdate(query);
			query = "UPDATE `brasileiro`.`time` SET pontos = 0, vitorias = 0, derrotas = 0, empates = 0, gols_pro = 0, gols_contra = 0, saldo_gols = 0, ca = 0, cv = 0, aproveitamento = 0;";
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao limpar Campeonato.");
		}
	}

	public void cadastrarCampeonato() throws Exception {
		int turno = 2;
		int rodada = 19;
		int partida = 10;
		int clubes = 20;
		Statement stmt = null;
		try {
			limparCampeonato();
			Connection conn = getConnection();
			String query = "INSERT INTO campeonato (qtd_turnos, qtd_rodadas, qtd_partidas, qtd_clubes) VALUES ('"
					+ turno + "', '" + rodada + "', '" + partida + "', '" + clubes + "')";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Cadastrar Campeonato.");
		}
	}

	public int contarTimes() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			Connection conn = getConnection();
			String query = "SELECT count(*) as count FROM brasileiro.time;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			count = rs.getInt("count");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Contar os Times.");
		}
		return count;
	}

	public void cadastrarTime(String nome) throws Exception {
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			String query = "INSERT INTO time (nome_time) VALUES ('" + nome + "')";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Cadastrar Time.");
		}
	}

	public void limparTime(int numero_time) throws Exception {
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			String query = "DELETE FROM `brasileiro`.`time` WHERE (numero_time = '" + numero_time + "')";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Limpar Time.");
		}
	}

	public void sortearTimes() throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		List<String> clubes = new ArrayList<String>();
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM time";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("time " + rs.getInt("numero_time") + ": " + rs.getString("nome_time"));
				clubes.add(rs.getString("numero_time"));
			}

			int partida = 1;
			int turno = 1;
			for (turno = 1; turno <= 2; turno++) {
				String s_truno = "INSERT INTO `brasileiro`.`turno` (`numero_turno`) VALUES ('" + turno + "')";
				stmt.executeUpdate(s_truno);

				int rodada;
				int t = clubes.size();
				int m = clubes.size() / 2;
				for (int i = 0; i < t - 1; i++) {
					rodada = i + 1;
					System.out.print((rodada) + "a rodada: ");
					String s_rodada = "INSERT INTO `brasileiro`.`rodada` (`numero_rodada`, `numero_turno`) VALUES ('"
							+ rodada + "', '" + turno + "');";
					stmt.executeUpdate(s_rodada);

					for (int j = 0; j < m; j++) {
						// Clube está de fora nessa rodada?
						if (clubes.get(j).isEmpty())
							continue;

						// Teste para ajustar o mando de campo
						if (j % 2 == 1 || i % 2 == 1 && j == 0) {
							System.out.println(clubes.get(t - j - 1) + " x " + clubes.get(j) + "   ");
							query = "INSERT INTO `brasileiro`.`partida` (numero_partida , numero_rodada, time_a, time_b) VALUES ("
									+ partida + ", " + rodada + ", " + clubes.get(t - j - 1) + ", " + clubes.get(j)
									+ ");";
							stmt.executeUpdate(query);

						} else {
							System.out.println(clubes.get(j) + " x " + clubes.get(t - j - 1) + "   ");
							query = "INSERT INTO `brasileiro`.`partida` (numero_partida , numero_rodada, time_a, time_b) VALUES ("
									+ partida + ", " + rodada + ", " + clubes.get(j) + ", " + clubes.get(t - j - 1)
									+ ");";
							stmt.executeUpdate(query);
						}
						partida++;
					}
					System.out.println();
					// Gira os clubes no sentido horário, mantendo o primeiro no lugar
					clubes.add(1, clubes.remove(clubes.size() - 1));
				}
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Sortear Times.");
		}
	}

	public void sortearResultados() throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		Statement stmt4 = null;

		try {
			Connection conn = getConnection();
			String query = "SELECT pontos FROM time WHERE numero_time = 1;";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			if(rs.getInt("pontos") == 0) {
				Random r = new Random();
				int golsA;
				int golsB;
				int caA;
				int caB;
				int cvA;
				int cvB;
				int vitoriaA;
				int derrotaA;
				int empate;
				int vitoriaB;
				int derrotaB;
				int pontosA;
				int pontosB;
	
				query = "SELECT * FROM time;";
				rs = stmt.executeQuery(query);
				rs.next();
				if (rs.getInt("pontos") == 0 && rs.getInt("vitorias") == 0) {
					query = "SELECT * FROM partida;";
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						golsA = r.nextInt(5);
						golsB = r.nextInt(5);
						caA = r.nextInt(3);
						caB = r.nextInt(3);
						cvA = r.nextInt(2);
						cvB = r.nextInt(2);
						if (golsA > golsB) {
							vitoriaA = 1;
							derrotaA = 0;
							vitoriaB = 0;
							derrotaB = 1;
							empate = 0;
							pontosA = 3;
							pontosB = 0;
						} else if (golsB > golsA) {
							vitoriaA = 0;
							derrotaA = 1;
							vitoriaB = 1;
							derrotaB = 0;
							empate = 0;
							pontosA = 0;
							pontosB = 3;
						} else {
							vitoriaA = 0;
							derrotaA = 0;
							vitoriaB = 0;
							derrotaB = 0;
							empate = 1;
							pontosA = 1;
							pontosB = 1;
						}
	
						query = "UPDATE `brasileiro`.`partida` SET `gols_a` = '" + golsA + "', `gols_b` = '" + golsB
								+ "', `ca_a` = '" + caA + "', `ca_b` = '" + caB + "', `cv_a` = '" + cvA + "', `cv_b` = '"
								+ cvB + "' WHERE (numero_partida = " + rs.getInt("numero_partida") + ");";
						stmt2 = conn.createStatement();
						stmt2.executeUpdate(query);
	
						query = "UPDATE `brasileiro`.`time` SET `gols_pro` = `gols_pro` + " + golsA
								+ ", `gols_contra` = `gols_contra` + " + golsB
								+ ", `saldo_gols` = `gols_pro` - `gols_contra`, `ca` = `ca` + " + caA + ", `cv` = `cv` + "
								+ cvA + ", `vitorias` = `vitorias` + " + vitoriaA + ", `derrotas` = `derrotas` + "
								+ derrotaA + ", `empates` = `empates` + " + empate + ", `pontos` = `pontos` + " + pontosA
								+ "  WHERE (numero_time = " + rs.getInt("time_a") + ");";
						stmt3 = conn.createStatement();
						stmt3.executeUpdate(query);
	
						query = "UPDATE `brasileiro`.`time` SET `gols_pro` = `gols_pro` + " + golsB
								+ ", `gols_contra` = `gols_contra` + " + golsA
								+ ", `saldo_gols` = `gols_pro` - `gols_contra`, `ca` = `ca` + " + caB + ", `cv` = `cv` + "
								+ cvB + ", `vitorias` = `vitorias` + " + vitoriaB + ", `derrotas` = `derrotas` + "
								+ derrotaB + ", `empates` = `empates` + " + empate + ", `pontos` = `pontos` + " + pontosB
								+ "  WHERE (numero_time = " + rs.getInt("time_b") + ");";
						stmt4 = conn.createStatement();
						stmt4.executeUpdate(query);
					}
				}
			} else {
				String erro = "O sorteio já foi realizado, caso queria realizar outro sorteio, é necessário limpar o campeonato, e fazer novamento o processo de sorteio dos times";
				System.out.println(erro);
				throw new Exception(erro);
			}
		} catch (Exception e) {
			String erro = "Erro: " + e.getMessage();
			System.out.println(erro);
			throw new Exception(erro);
		}
	}

	public void calculoAproveitamento() throws Exception {
		ResultSet rs = null;
		Statement stmt = null;
		Statement stmt2 = null;
		double aproveitamento;

		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM `brasileiro`.`time`";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				aproveitamento = (rs.getInt("pontos") * 100 / 114);

				query = "UPDATE `brasileiro`.`time` SET `aproveitamento` = " + aproveitamento
						+ " WHERE (`numero_time` = " + rs.getInt("numero_time") + ");";
				stmt2 = conn.createStatement();
				stmt2.executeUpdate(query);
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Calcular o Aproveitamento.");
		}
	}

	public ArrayList<Time> mostrarClassificaçao() throws Exception {
		ArrayList<Time> lista = new ArrayList<Time>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			String query = "SELECT * FROM time ORDER BY pontos desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Time t = new Time();
				t.setNome_time(rs.getString("nome_time"));
				t.setPontos(rs.getInt("pontos"));
				t.setVitorias(rs.getInt("vitorias"));
				t.setDerrotas(rs.getInt("derrotas"));
				t.setEmpates(rs.getInt("empates"));
				t.setGols_pro(rs.getInt("gols_pro"));
				t.setGols_contra(rs.getInt("gols_contra"));
				t.setSaldo_gols(rs.getInt("saldo_gols"));
				t.setAproveitamento(rs.getInt("aproveitamento"));

				lista.add(t);
				System.out.println("time: " + rs.getString("nome_time") + "  pontos: " + rs.getInt("pontos")
						+ "  vitorias: " + rs.getInt("vitorias") + "  derrotas: " + rs.getInt("derrotas")
						+ "  empates: " + rs.getInt("empates") + "  gols pro: " + rs.getInt("gols_pro")
						+ "  gols contra: " + rs.getInt("gols_contra") + "  saldo de gols: " + rs.getInt("saldo_gols"));
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Ver Resultados .");
		}
		return lista;
	}

	public ArrayList<Partida> listarPartidas() throws Exception {
		ArrayList<Partida> lista = new ArrayList<Partida>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			Connection conn = getConnection();
			String query = "select numero_rodada, numero_partida, t1.nome_time as time1, t2.nome_time as time2 \r\n"
					+ "from  partida p \r\n" + "join `brasileiro`.`time` t1 on (p.time_a = t1.numero_time)\r\n"
					+ "join `brasileiro`.`time` t2 on (p.time_b = t2.numero_time)\r\n" + "order by numero_partida";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Partida p = new Partida();
				Time t1 = new Time();
				Time t2 = new Time();
				t1.setNome_time(rs.getString("time1"));
				t2.setNome_time(rs.getString("time2"));
				p.setNumero_rodada(rs.getInt("numero_rodada"));
				p.setNumero_partida(rs.getInt("numero_partida"));
				p.setTime_a(t1);
				p.setTime_b(t2);

				lista.add(p);
				System.out.println("numero rodada: " + rs.getInt("numero_rodada") + "  numero partida: "
						+ rs.getInt("numero_partida") + "  " + rs.getString("time1") + "  x  " + rs.getString("time2"));
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Listar Partidas");
		}
		return lista;
	}

	public String mostrarVencedores() throws Exception {
		String lista = "";
		Time t = new Time();
		String query;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Time timeAnterior = new Time();

		try {
			Connection conn = getConnection();
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();

			query = "select * from time order by pontos desc, vitorias desc, saldo_gols desc ,gols_pro desc;";
			rs = stmt.executeQuery(query);
			rs.next();
			timeAnterior.setPontos(rs.getInt("pontos"));
			timeAnterior.setVitorias(rs.getInt("vitorias"));
			timeAnterior.setSaldo_gols(rs.getInt("saldo_gols"));
			timeAnterior.setGols_pro(rs.getInt("gols_pro"));

			query = "select count(*) as count from time where (pontos = " + timeAnterior.getPontos()
					+ " AND vitorias = " + timeAnterior.getVitorias() + " AND saldo_gols = "
					+ timeAnterior.getSaldo_gols() + " AND gols_pro = " + timeAnterior.getGols_pro() + ");";
			rs = stmt.executeQuery(query);
			rs.next();
			if (rs.getInt("count") == 1) {
				query = "select nome_time from time where (pontos = " + timeAnterior.getPontos() + " AND vitorias = "
						+ timeAnterior.getVitorias() + " AND saldo_gols = " + timeAnterior.getSaldo_gols()
						+ " AND gols_pro = " + timeAnterior.getGols_pro() + ");";
				rs2 = stmt2.executeQuery(query);
				if (rs2.next()) {
					t.setNome_time(rs2.getString("nome_time"));

					lista = t.getNome_time();
					System.out.println("time campeão: " + rs2.getString("nome_time"));
				} else {
					System.out.println("Erro no sistema");
					System.exit(0);
				}
			} else if (rs.getInt("count") == 2) {
				query = "select * from time where (pontos = " + timeAnterior.getPontos() + " AND vitorias = "
						+ timeAnterior.getVitorias() + " AND saldo_gols = " + timeAnterior.getSaldo_gols()
						+ " AND gols_pro = " + timeAnterior.getGols_pro() + ");";
				rs2 = stmt2.executeQuery(query);

				String s;
				rs2.next();
				s = "" + rs2.getInt("numero_time");
				rs2.next();
				s = s + "," + rs2.getInt("numero_time");

				query = "select * from partida where ( time_a in ( " + s + " ) and time_b in  ( " + s + " ));";
				rs2 = stmt2.executeQuery(query);
				int vitoriaA = 0;
				int empate = 0;
				int partida = 0;
				while (rs2.next()) {
					if (rs2.getInt("gols_a") > rs2.getInt("gols_b")) {
						vitoriaA = vitoriaA + 1;
						empate = empate + 0;
						partida = partida + 1;
					} else if (rs2.getInt("gols_a") < rs2.getInt("gols_b")) {
						vitoriaA = vitoriaA + 0;
						empate = empate + 0;
						partida = partida + 1;
					} else {
						vitoriaA = vitoriaA + 0;
						empate = empate + 1;
						partida = partida + 1;
					}
					query = "select * from time where (pontos = " + timeAnterior.getPontos() + " AND vitorias = "
							+ timeAnterior.getVitorias() + " AND saldo_gols = " + timeAnterior.getSaldo_gols()
							+ " AND gols_pro = " + timeAnterior.getGols_pro() + ");";
					rs = stmt.executeQuery(query);
					rs.next();
					String timeA = rs.getString("nome_time");
					rs.next();
					String timeB = rs.getString("nome_time");
					if (vitoriaA == 0 && empate == 0 && partida == 2) { // 2 vitorias B
						t.setNome_time(timeB);

						lista = t.getNome_time();
						System.out.println("time campeão: " + timeB);
					} else if (vitoriaA == 2 && empate == 0 && partida == 2) { // 2 vitorias A
						t.setNome_time(timeA);

						lista = t.getNome_time();
						System.out.println("time campeão: " + timeA);
					} else if (vitoriaA == 1 && empate == 1 && partida == 2) { // 1 vitoria A e 1 empate
						t.setNome_time(timeA);

						lista = t.getNome_time();
						System.out.println("time campeão: " + timeA);
					} else if (vitoriaA == 0 && empate == 1 && partida == 2) { // 1 vitoria B e 1 empate
						t.setNome_time(timeB);

						lista = t.getNome_time();
						System.out.println("time campeão: " + timeB);
					} else if (vitoriaA == 1 && empate == 0 && partida == 2) { // 1 vitoria A e 1 vitoria B
						query = "select * from time order by pontos desc, vitorias desc, saldo_gols desc, gols_pro desc, cv asc, ca asc;";
						rs2 = stmt2.executeQuery(query);
						rs2.next();
						t.setNome_time(rs2.getString("nome_time"));

						lista = t.getNome_time();
						System.out.println("time campeão: " + rs2.getString("nome_time"));
					} else if (vitoriaA == 0 && empate == 2 && partida == 2) { // 2 empates
						query = "select * from time order by pontos desc, vitorias desc, saldo_gols desc, gols_pro desc, cv asc, ca asc;";
						rs2 = stmt2.executeQuery(query);
						rs2.next();
						t.setNome_time(rs2.getString("nome_time"));

						lista = t.getNome_time();
						System.out.println("time campeão: " + rs2.getString("nome_time"));
					}
				}
			} else {
				query = "select * from time order by pontos desc, vitorias desc, saldo_gols desc, gols_pro desc, cv asc, ca asc;";
				rs2 = stmt2.executeQuery(query);
				rs2.next();
				t.setNome_time(rs2.getString("nome_time"));

				lista = t.getNome_time();
				System.out.println("time campeão: " + rs2.getString("nome_time"));
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			throw new Exception("Erro ao Ver Vencedor");
		}
		return lista;
	}

	public void desconectar() {
		Connection conn;
		try {
			conn = getConnection();
			conn.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
