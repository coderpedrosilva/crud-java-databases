package jfirebase;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import org.json.JSONObject;

public class Utils {

	static Scanner teclado = new Scanner(System.in);
	static HttpClient conn = conectar();
	static String baseUrl = "https://firebase-default-rtdb.firebaseio.com";

	public static HttpClient conectar() {
		return HttpClient.newBuilder().build();
	}

	public static void desconectar() {
		System.out.println("Desconectando...");
	}

	public static void listar() {
		HttpClient conn = conectar();

		String link = baseUrl + "/produtos.json";

		HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(link)).build();

		try {
			HttpResponse<String> resposta = conn.send(requisicao, BodyHandlers.ofString());

			if (resposta.statusCode() == 200) {
				String corpoResposta = resposta.body();
				if (!corpoResposta.equals("null")) {
					JSONObject obj = new JSONObject(corpoResposta);

					System.out.println("Listando produtos...");
					System.out.println("--------------------");
					for (String chave : obj.keySet()) {
						JSONObject prod = obj.getJSONObject(chave);
						System.out.println("ID: " + chave);
						System.out.println("Produto: " + prod.getString("nome"));
						System.out.println("Preço: " + prod.getDouble("preco"));
						System.out.println("Estoque: " + prod.getInt("estoque"));
						System.out.println("-----------------");
					}
				} else {
					System.out.println("Não existem produtos cadastrados.");
				}
			} else {
				System.out.println("Status: " + resposta.statusCode());
			}

		} catch (IOException e) {
			System.out.println("Houve um erro na conexão.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Houve um erro na conexão.");
			e.printStackTrace();
		}
	}

	public static void inserir() {
		System.out.println("Informe o nome do produto: ");
		String nome = teclado.nextLine();

		System.out.println("Informe o preço: ");
		float preco = Float.parseFloat(teclado.nextLine());

		System.out.println("Informe o estoque: ");
		int estoque = Integer.parseInt(teclado.nextLine());

		JSONObject nproduto = new JSONObject();
		nproduto.put("nome", nome);
		nproduto.put("preco", preco);
		nproduto.put("estoque", estoque);

		String link = baseUrl + "/produtos.json";

		HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(link))
				.POST(HttpRequest.BodyPublishers.ofString(nproduto.toString()))
				.header("Content-Type", "application/json").build();

		try {
			HttpResponse<String> resposta = conn.send(requisicao, BodyHandlers.ofString());

			if (resposta.statusCode() == 200) {
				System.out.println("O produto " + nome + " foi cadastrado com sucesso.");
			} else {
				System.out.println("Status: " + resposta.statusCode());
			}

		} catch (IOException e) {
			System.out.println("Houve erro com a conexão.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Houve erro com a conexão.");
			e.printStackTrace();
		}
	}

	// TODO: [Corrigir a função para não inserir produtos com IDs aleatórios, apenas
	// IDs que existam]
	public static void atualizar() {
		System.out.println("Informe o ID do produto: ");
		String id = teclado.nextLine();

		System.out.println("Informe o novo nome: ");
		String nome = teclado.nextLine();

		System.out.println("Informe o novo preço: ");
		float preco = Float.parseFloat(teclado.nextLine());

		System.out.println("Informe o novo estoque: ");
		int estoque = Integer.parseInt(teclado.nextLine());

		JSONObject nproduto = new JSONObject();
		nproduto.put("nome", nome);
		nproduto.put("preco", preco);
		nproduto.put("estoque", estoque);

		String link = baseUrl + "/produtos/" + id + ".json";

		HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(link))
				.PUT(HttpRequest.BodyPublishers.ofString(nproduto.toString()))
				.header("Content-Type", "application/json").build();

		try {
			HttpResponse<String> resposta = conn.send(requisicao, BodyHandlers.ofString());

			if (resposta.statusCode() == 200) {
				System.out.println("O produto " + nome + " foi atualizado com sucesso.");
			} else {
				System.out.println("Status: " + resposta.statusCode());
			}

		} catch (IOException e) {
			System.out.println("Houve erro com a conexão.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Houve erro com a conexão.");
			e.printStackTrace();
		}
	}

	// TODO: [Corrigir a impressão pós delete, a mensagem de sucesso não está
	// aparecendo]
	public static void deletar() {
		HttpClient conn = conectar();

		System.out.println("Informe o ID do produto: ");
		String id = teclado.nextLine();

		String link = baseUrl + "/produtos/" + id + ".json";

		HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(link)).DELETE()
				.header("Content-Type", "application/json").build();

		try {
			HttpResponse<String> resposta = conn.send(requisicao, BodyHandlers.ofString());

			if (resposta.statusCode() == 200 && !resposta.body().equals("null")) {
				System.out.println("O produto foi deletado com sucesso.");
			} else {
				System.out.println("Não existe produto com o ID " + id);
			}

		} catch (IOException e) {
			System.out.println("Houve erro na conexão.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Houve erro na conexão.");
			e.printStackTrace();
		}
	}

	public static void menu() {
		System.out.println("==================Gerenciamento de Produtos===============");
		System.out.println("Selecione uma opção: ");
		System.out.println("1 - Listar produtos.");
		System.out.println("2 - Inserir produtos.");
		System.out.println("3 - Atualizar produtos.");
		System.out.println("4 - Deletar produtos.");

		int opcao = Integer.parseInt(teclado.nextLine());
		if (opcao == 1) {
			listar();
		} else if (opcao == 2) {
			inserir();
		} else if (opcao == 3) {
			atualizar();
		} else if (opcao == 4) {
			deletar();
		} else {
			System.out.println("Opção inválida.");
		}
	}
}
