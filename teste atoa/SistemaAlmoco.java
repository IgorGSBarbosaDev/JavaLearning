package teste_atoa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Sistema de Gerenciamento de Almoço
 * Aplicação para gerenciar esfomeados e suas preferências de almoço
 */
public class SistemaAlmoco {
    
    private static List<String> esfomeados = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        inicializarSistema();
        exibirMenu();
    }
    
    /**
     * Inicializa o sistema com dados básicos
     */
    private static void inicializarSistema() {
        System.out.println("=== SISTEMA DE GERENCIAMENTO DE ALMOÇO ===");
        System.out.println("Pelo amor de Deus, chega logo almoço! 🍽️");
        System.out.println();
        
        // Inicializa lista de esfomeados
        esfomeados.add("Igor");
        esfomeados.add("Matheus");
        esfomeados.add("Pedro");
        esfomeados.add("Garrafinha perdida");
        
        System.out.println("Pessoas registradas como esfomeadas:");
        for (int i = 0; i < esfomeados.size(); i++) {
            System.out.println((i + 1) + ". " + esfomeados.get(i));
        }
        System.out.println();
    }
    
    /**
     * Exibe o menu principal e processa as opções
     */
    private static void exibirMenu() {
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1. Definir objetivo do almoço");
            System.out.println("2. Adicionar nova pessoa esfomeada");
            System.out.println("3. Remover pessoa da lista");
            System.out.println("4. Listar todas as pessoas esfomeadas");
            System.out.println("5. Verificar status da fome");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção (1-6): ");
            
            int opcao = lerInteiro();
            
            switch (opcao) {
                case 1:
                    definirObjetivoAlmoco();
                    break;
                case 2:
                    adicionarPessoa();
                    break;
                case 3:
                    removerPessoa();
                    break;
                case 4:
                    listarEsfomeados();
                    break;
                case 5:
                    verificarStatusFome();
                    break;
                case 6:
                    System.out.println("Saindo do sistema... Bom almoço! 🍽️");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            if (continuar) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
                limparTela();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Define o objetivo do almoço
     */
    private static void definirObjetivoAlmoco() {
        System.out.println("\n=== DEFINIR OBJETIVO DO ALMOÇO ===");
        System.out.println("Qual seu objetivo hoje?");
        System.out.println("1. Só provar (diet mode)");
        System.out.println("2. Hoje vou só sentir o cheiro de perto (extreme diet)");
        System.out.println("3. Hoje vou só comprimentar a cozinheira (social mode)");
        System.out.println("4. Vou devorar tudo que ver pela frente (beast mode)");
        System.out.print("Escolha sua opção (1-4): ");
        
        int objetivo = lerInteiro();
        
        switch (objetivo) {
            case 1:
                System.out.println("✅ Hoje o objetivo é só provar - Mode Diet ativado!");
                System.out.println("Dica: Mastigue bem e saboreie cada garfada.");
                break;
            case 2:
                System.out.println("✅ Hoje vou só sentir o cheiro - Mode Extreme Diet ativado!");
                System.out.println("Dica: O cheiro já alimenta a alma... quase.");
                break;
            case 3:
                System.out.println("✅ Hoje vou só comprimentar a cozinheira - Mode Social ativado!");
                System.out.println("Dica: Um sorriso e um elogio fazem milagres!");
                break;
            case 4:
                System.out.println("✅ Vou devorar tudo - Mode Beast ativado!");
                System.out.println("Dica: Prepare o estômago para a batalha épica!");
                break;
            default:
                System.out.println("❌ Opção inválida! Parece que a fome está afetando sua capacidade de escolha.");
        }
    }
    
    /**
     * Adiciona uma nova pessoa à lista de esfomeados
     */
    private static void adicionarPessoa() {
        System.out.println("\n=== ADICIONAR NOVA PESSOA ===");
        System.out.print("Digite o nome da pessoa esfomeada: ");
        scanner.nextLine(); // Limpa o buffer
        String nome = scanner.nextLine();
        
        if (!nome.trim().isEmpty()) {
            if (!esfomeados.contains(nome)) {
                esfomeados.add(nome);
                System.out.println("✅ " + nome + " foi adicionado(a) à lista de esfomeados!");
            } else {
                System.out.println("⚠️ " + nome + " já está na lista!");
            }
        } else {
            System.out.println("❌ Nome inválido!");
        }
    }
    
    /**
     * Remove uma pessoa da lista de esfomeados
     */
    private static void removerPessoa() {
        if (esfomeados.isEmpty()) {
            System.out.println("❌ Não há pessoas na lista para remover!");
            return;
        }
        
        System.out.println("\n=== REMOVER PESSOA ===");
        System.out.println("Lista atual:");
        for (int i = 0; i < esfomeados.size(); i++) {
            System.out.println((i + 1) + ". " + esfomeados.get(i));
        }
        
        System.out.print("Digite o número da pessoa para remover (1-" + esfomeados.size() + "): ");
        int indice = lerInteiro() - 1;
        
        if (indice >= 0 && indice < esfomeados.size()) {
            String removido = esfomeados.remove(indice);
            System.out.println("✅ " + removido + " foi removido(a) da lista!");
            System.out.println("Esperamos que tenha encontrado comida! 🍽️");
        } else {
            System.out.println("❌ Número inválido!");
        }
    }
    
    /**
     * Lista todas as pessoas esfomeadas
     */
    private static void listarEsfomeados() {
        System.out.println("\n=== LISTA DE ESFOMEADOS ===");
        if (esfomeados.isEmpty()) {
            System.out.println("🎉 Ninguém está com fome! Todos já almoçaram!");
        } else {
            System.out.println("Total de esfomeados: " + esfomeados.size());
            for (int i = 0; i < esfomeados.size(); i++) {
                System.out.println((i + 1) + ". " + esfomeados.get(i) + " 🍽️");
            }
        }
    }
    
    /**
     * Verifica o status geral da fome
     */
    private static void verificarStatusFome() {
        System.out.println("\n=== STATUS DA FOME ===");
        
        if (esfomeados.isEmpty()) {
            System.out.println("🎉 Status: TODOS ALIMENTADOS!");
            System.out.println("Parabéns! Ninguém está com fome no momento.");
        } else {
            System.out.println("⚠️ Status: EMERGÊNCIA ALIMENTAR!");
            System.out.println("Número de esfomeados: " + esfomeados.size());
            System.out.println("Nível de urgência: " + calcularNivelUrgencia());
            System.out.println("Recomendação: " + gerarRecomendacao());
        }
    }
    
    /**
     * Calcula o nível de urgência baseado no número de esfomeados
     */
    private static String calcularNivelUrgencia() {
        int total = esfomeados.size();
        if (total <= 1) return "BAIXO 🟢";
        else if (total <= 3) return "MÉDIO 🟡";
        else if (total <= 5) return "ALTO 🟠";
        else return "CRÍTICO 🔴";
    }
    
    /**
     * Gera recomendação baseada no número de esfomeados
     */
    private static String gerarRecomendacao() {
        int total = esfomeados.size();
        if (total <= 1) return "Relaxa, é só uma pessoa. Dá pra aguentar mais um pouco.";
        else if (total <= 3) return "Hora de pensar seriamente no almoço.";
        else if (total <= 5) return "ALERTA! Providenciem comida urgentemente!";
        else return "EVACUEM O PRÉDIO! A fome está fora de controle!";
    }
    
    /**
     * Lê um número inteiro do usuário com tratamento de erro
     */
    private static int lerInteiro() {
        while (true) {
            try {
                int numero = Integer.parseInt(scanner.nextLine());
                return numero;
            } catch (NumberFormatException e) {
                System.out.print("❌ Por favor, digite um número válido: ");
            }
        }
    }
    
    /**
     * Simula a limpeza da tela
     */
    private static void limparTela() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
    }
}