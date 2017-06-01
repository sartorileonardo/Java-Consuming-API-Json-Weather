package com.sc.leo.br.main;

import javax.swing.JOptionPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class TestJsonApi {

	public static void main(String[] args) {

		String URL_API = "http://api.openweathermap.org/data/2.5/weather/";
		String API_KEY = "eb8b1a9405e659b2ffc78f0a520b1a46";

		Client client = ClientBuilder.newClient();

		String country = JOptionPane
				.showInputDialog("Digite a sigla do país: ");
		String city = JOptionPane.showInputDialog("Digite a cidade: ");

		try {
			// Pesquisa somente por cidade caso país esteja NULL ou VAZIO
			if (country.equals(null) || country.isEmpty()) {
				String name = client.target(URL_API).queryParam("q", city)
						.queryParam("appid", API_KEY)
						.request(MediaType.APPLICATION_JSON).get(String.class);
			}

			// Valida se cidade foi preenchida
			if (city.equals(null) || city.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"Nome da cidade é obrigatório!");
				return;
			}

			String name = client.target(URL_API)
					.queryParam("q", city.concat(country))
					.queryParam("appid", API_KEY)
					.request(MediaType.APPLICATION_JSON).get(String.class);

			System.out.println(name);

		} catch (javax.ws.rs.NotFoundException n) {
			JOptionPane
					.showMessageDialog(
							null,
							"Os valores inseridos para País ou Cidade não foram encontrados, favor inserir outra país/cidade válidos! \n Por exemplo: País: BR, Cidade: Florianópolis",
							"Erro de validação", JOptionPane.WARNING_MESSAGE);
			n.getMessage();
			n.printStackTrace();
		} catch (javax.ws.rs.BadRequestException b) {
			JOptionPane
					.showMessageDialog(
							null,
							"Os valores inseridos para País ou Cidade são inválidos ou em branco, favor inserir outra país/cidade válidos! \n Por exemplo: País: BR, Cidade: Florianópolis",
							"Erro de validação", JOptionPane.WARNING_MESSAGE);
			b.getMessage();
			b.printStackTrace();
		} finally {
			client.close();
		}

	}

}
