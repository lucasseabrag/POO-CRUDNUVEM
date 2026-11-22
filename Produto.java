package uniceplac_Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Produto {

    public static void main(String[] args)
    {

        // conexão no Railway (dados do MySQL: Connect > Public Network)

        String host = "yamabiko.proxy.rlwy.net";
        String porta = "32097";
        String banco = "railway";

        String url = "jdbc:mysql://" + host + ":" + porta + "/" + banco
                + "?useSSL=false&allowPublicKeyRetrieval=true";
        String usuariobanco = "root";
        String bancosenha = "rIUdNreKvKpSqcSILIZLRXhJbniptgNP";


        Scanner sc = new Scanner(System.in);
        try
        {

            Connection conexao = DriverManager.getConnection(url, usuariobanco, bancosenha);

            int opcao = -1;
            while(opcao != 0) {
                System.out.println("1 - Inserir | 2 - Listar | 3 - Atualizar | 4 - Deletar | 0 - Sair ");

                opcao = sc.nextInt();
                sc.nextLine();

                switch(opcao) {

                case 1:
                    System.out.print("Código do produto: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Nome do produto: ");
                    String nome = sc.nextLine();

                    System.out.print("Preço do produto: ");
                    float preco = sc.nextFloat();
                    sc.nextLine();

                    String sql = "INSERT INTO PRODUTO (CODIGO, NOME, PRECO) VALUES (?, ?, ? )";

                    PreparedStatement stmt = conexao.prepareStatement(sql);
                    stmt.setInt(1, codigo);
                    stmt.setString(2, nome);
                    stmt.setFloat(3, preco);
                    stmt.executeUpdate();
                    stmt.close();

                    System.out.println("Produto inserido!");
                    break;

                case 2:
                    System.out.print("Digite o código do produto: ");
                    int codigo2 = sc.nextInt();
                    sc.nextLine();

                    String sql2 = "SELECT * FROM PRODUTO WHERE CODIGO = ? ";
                    PreparedStatement stmt2 = conexao.prepareStatement(sql2);
                    stmt2.setInt(1 , codigo2);
                    ResultSet rs = stmt2.executeQuery();

                    boolean achou = false;
                    while(rs.next()) {
                        achou = true;
                        System.out.println("Nome produto: " + rs.getString("nome") + " | Preço: " + rs.getFloat("preco"));
                    }
                    if(!achou) {
                        System.out.println("Produto não encontrado!");
                    }
                    rs.close();
                    stmt2.close();
                    break;

                case 3:
                    System.out.print("Código do produto a atualizar: ");
                    int codigo3 = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Novo nome do produto: ");
                    String nome3 = sc.nextLine();

                    System.out.print("Novo preço do produto: ");
                    float preco3 = sc.nextFloat();
                    sc.nextLine();

                    String sql3 = "UPDATE PRODUTO SET NOME = ?, PRECO = ? WHERE CODIGO = ?";
                    PreparedStatement stmt3 = conexao.prepareStatement(sql3);
                    stmt3.setString(1, nome3);
                    stmt3.setFloat(2, preco3);
                    stmt3.setInt(3, codigo3);
                    int atualizados = stmt3.executeUpdate();
                    stmt3.close();

                    if(atualizados > 0) {
                        System.out.println("Produto atualizado!");
                    } else {
                        System.out.println("Produto não encontrado!");
                    }
                    break;

                case 4:
                    System.out.print("Código do produto a deletar: ");
                    int codigo4 = sc.nextInt();
                    sc.nextLine();

                    String sql4 = "DELETE FROM PRODUTO WHERE CODIGO = ?";
                    PreparedStatement stmt4 = conexao.prepareStatement(sql4);
                    stmt4.setInt(1, codigo4);
                    int deletados = stmt4.executeUpdate();
                    stmt4.close();

                    if(deletados > 0) {
                        System.out.println("Produto deletado!");
                    } else {
                        System.out.println("Produto não encontrado!");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!!!");
                    break;

                }

            }

            conexao.close();

        } catch (Exception E) {

            System.out.println("Erro de cadastro" + E.getMessage());

        }

        sc.close();

    }

}
