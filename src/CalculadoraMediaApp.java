import javax.swing.*; // Importa a biblioteca Swing para criar a interface gráfica
import java.awt.*; // Importa a biblioteca AWT para layouts e eventos
import java.awt.event.ActionEvent; // Importa a classe para eventos de ação (cliques de botão)
import java.awt.event.ActionListener; // Importa a interface para "ouvir" os eventos de ação

/**
 * Esta classe cria uma janela (JFrame) para calcular a média de um aluno.
 * Ela implementa ActionListener para poder reagir aos cliques dos botões.
 */
public class CalculadoraMediaApp extends JFrame implements ActionListener {

    // --- Componentes da Interface Gráfica ---
    // Campos de texto para entrada de dados
    private JTextField txtNome;
    private JTextField txtNota1, txtNota2, txtNota3, txtNota4;

    // Botões de ação
    private JButton btnCalcular, btnLimpar, btnSair;

    // Rótulos (labels) para exibir os resultados
    private JLabel lblResultadoNome;
    private JLabel lblResultadoMedia;
    private JLabel lblResultadoSituacao;

    /**
     * Construtor da classe.
     * Configura a janela principal e inicializa todos os componentes.
     */
    public CalculadoraMediaApp() {
        // --- 1. Configuração da Janela Principal ---
        setTitle("Calculadora de Média Simples");
        setSize(350, 450); // Define o tamanho da janela (largura, altura)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Faz o programa fechar ao clicar no "X"
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new GridLayout(10, 2, 10, 10)); // Define o layout (linhas, colunas, espaçamento h, espaçamento v)

        // --- 2. Inicialização e Adição dos Componentes ---

        // Campo Nome
        add(new JLabel("Nome do Aluno:"));
        txtNome = new JTextField();
        add(txtNome);

        // Campo Nota 1
        add(new JLabel("Nota 1:"));
        txtNota1 = new JTextField();
        add(txtNota1);

        // Campo Nota 2
        add(new JLabel("Nota 2:"));
        txtNota2 = new JTextField();
        add(txtNota2);

        // Campo Nota 3
        add(new JLabel("Nota 3:"));
        txtNota3 = new JTextField();
        add(txtNota3);

        // Campo Nota 4
        add(new JLabel("Nota 4:"));
        txtNota4 = new JTextField();
        add(txtNota4);

        // Botão Calcular
        btnCalcular = new JButton("Calcular Média");
        btnCalcular.addActionListener(this); // Registra o "ouvinte" de clique
        add(btnCalcular);

        // Botão Limpar
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(this); // Registra o "ouvinte" de clique
        add(btnLimpar);

        // Botão Sair
        btnSair = new JButton("Sair");
        btnSair.addActionListener(this); // Registra o "ouvinte" de clique
        add(btnSair);

        // Espaço vazio (apenas para layout)
        add(new JLabel());

        // --- 3. Área de Resultados ---
        add(new JLabel("--- Resultados ---"));
        add(new JLabel()); // Espaço

        lblResultadoNome = new JLabel("Aluno: ");
        add(lblResultadoNome);
        add(new JLabel()); // Espaço

        lblResultadoMedia = new JLabel("Média Final: ");
        add(lblResultadoMedia);
        add(new JLabel()); // Espaço

        lblResultadoSituacao = new JLabel("Situação: ");
        add(lblResultadoSituacao);
        add(new JLabel()); // Espaço
    }

    /**
     * Este método é chamado automaticamente quando um botão é clicado.
     * (Porque implementamos ActionListener e registramos com addActionListener).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Verifica qual botão foi clicado
        Object source = e.getSource();

        if (source == btnSair) {
            // Se for o botão Sair, fecha a aplicação
            System.exit(0);

        } else if (source == btnLimpar) {
            // Se for o botão Limpar, apaga o texto de todos os campos
            txtNome.setText("");
            txtNota1.setText("");
            txtNota2.setText("");
            txtNota3.setText("");
            txtNota4.setText("");
            lblResultadoNome.setText("Aluno: ");
            lblResultadoMedia.setText("Média Final: ");
            lblResultadoSituacao.setText("Situação: ");

        } else if (source == btnCalcular) {
            // Se for o botão Calcular, executa a lógica da média
            try {
                // 1. Obter os dados dos campos de texto
                String nome = txtNome.getText();
                double n1 = Double.parseDouble(txtNota1.getText());
                double n2 = Double.parseDouble(txtNota2.getText());
                double n3 = Double.parseDouble(txtNota3.getText());
                double n4 = Double.parseDouble(txtNota4.getText());

                // 2. Calcular a média
                double media = (n1 + n2 + n3 + n4) / 4.0;

                // 3. Determinar a situação
                String situacao;
                if (media < 6.0) {
                    situacao = "Ruim";
                } else if (media >= 6.0 && media <= 7.0) {
                    situacao = "Mais ou menos";
                } else { // media > 7.0
                    situacao = "Muito bom";
                }

                // 4. Exibir os resultados nos rótulos (labels)
                lblResultadoNome.setText("Aluno: " + nome);
                // String.format("%.2f", media) formata a média para ter 2 casas decimais
                lblResultadoMedia.setText("Média Final: " + String.format("%.2f", media));
                lblResultadoSituacao.setText("Situação: " + situacao);

            } catch (NumberFormatException ex) {
                // Se o usuário digitar algo que não é um número (ex: "abc")
                JOptionPane.showMessageDialog(this,
                        "Erro: Por favor, insira apenas números válidos nas notas.",
                        "Erro de Entrada",
                        JOptionPane.ERROR_MESSAGE);
                lblResultadoSituacao.setText("Situação: Erro!");
            } catch (Exception ex) {
                // Captura outros erros inesperados
                JOptionPane.showMessageDialog(this,
                        "Ocorreu um erro inesperado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método principal (main) que inicia a aplicação.
     */
    public static void main(String[] args) {
        // Cria e exibe a janela da aplicação
        // SwingUtilities.invokeLater garante que a interface gráfica
        // seja criada na thread correta (Event Dispatch Thread).
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraMediaApp().setVisible(true);
            }
        });
    }
}
