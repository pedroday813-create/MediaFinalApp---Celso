import javax.swing.*;
import javax.swing.border.EmptyBorder; // Para adicionar espaçamento (padding)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta classe cria uma janela (JFrame) para calcular a média de um aluno.
 * A interface foi reorganizada usando BorderLayout e JPanels para melhor estrutura.
 * O cálculo da média agora utiliza um vetor.
 */
public class CalculadoraMediaApp extends JFrame implements ActionListener {

    // --- Componentes da Interface Gráfica ---
    // Campos de texto para entrada de dados
    private JTextField txtNome;
    // Agora temos os campos de nota como um vetor para facilitar o acesso
    private JTextField[] txtNotas;

    // Botões de ação
    private JButton btnCalcular, btnLimpar, btnSair;

    // Rótulos (labels) para exibir os resultados
    private JLabel lblResultadoNome;
    private JLabel lblResultadoMedia;
    private JLabel lblResultadoSituacao;

    // Número de notas a serem inseridas
    private static final int NUMERO_DE_NOTAS = 4;

    /**
     * Construtor da classe.
     * Configura a janela principal e inicializa todos os componentes.
     */
    public CalculadoraMediaApp() {
        // --- 1. Configuração da Janela Principal ---
        setTitle("Calculadora de Média Escolar");
        setSize(400, 400); // Ajuste no tamanho
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usando BorderLayout como layout principal da janela
        setLayout(new BorderLayout(10, 10)); // Espaçamento horizontal e vertical

        // Painel principal que conterá tudo, com uma borda de espaçamento
        JPanel pnlPrincipal = new JPanel(new BorderLayout(10, 10));
        pnlPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding (top, left, bottom, right)
        add(pnlPrincipal, BorderLayout.CENTER);


        // --- 2. Painel de Entradas (CENTER) ---
        JPanel pnlEntradas = new JPanel(new GridLayout(NUMERO_DE_NOTAS + 1, 2, 5, 5)); // +1 para o nome
        pnlPrincipal.add(pnlEntradas, BorderLayout.CENTER);

        // Campo Nome
        pnlEntradas.add(new JLabel("Nome do Aluno:"));
        txtNome = new JTextField();
        pnlEntradas.add(txtNome);

        // Inicializa o vetor de campos de texto
        txtNotas = new JTextField[NUMERO_DE_NOTAS];

        // Cria os campos de nota dinamicamente
        for (int i = 0; i < NUMERO_DE_NOTAS; i++) {
            pnlEntradas.add(new JLabel("Nota " + (i + 1) + ":"));
            txtNotas[i] = new JTextField();
            pnlEntradas.add(txtNotas[i]);
        }

        // --- 3. Painel de Botoes (SOUTH do pnlPrincipal) ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnCalcular = new JButton("Calcular Média");
        btnCalcular.addActionListener(this);
        pnlBotoes.add(btnCalcular);

        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(this);
        pnlBotoes.add(btnLimpar);

        btnSair = new JButton("Sair");
        btnSair.addActionListener(this);
        pnlBotoes.add(btnSair);

        pnlPrincipal.add(pnlBotoes, BorderLayout.NORTH); // Botoes ficam acima dos resultados


        // --- 4. Painel de Resultados (SOUTH do pnlPrincipal) ---
        JPanel pnlResultados = new JPanel();
        // BoxLayout organiza os componentes verticalmente (um embaixo do outro)
        pnlResultados.setLayout(new BoxLayout(pnlResultados, BoxLayout.Y_AXIS));
        pnlResultados.setBorder(BorderFactory.createTitledBorder("Resultados")); // Título para a área

        lblResultadoNome = new JLabel("Aluno: ");
        lblResultadoMedia = new JLabel("Média Final: ");
        lblResultadoSituacao = new JLabel("Situação: ");

        // Adiciona os labels ao painel de resultados
        pnlResultados.add(lblResultadoNome);
        pnlResultados.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
        pnlResultados.add(lblResultadoMedia);
        pnlResultados.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento
        pnlResultados.add(lblResultadoSituacao);

        pnlPrincipal.add(pnlResultados, BorderLayout.SOUTH);
    }

    /**
     * Este método é chamado automaticamente quando um botão é clicado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnSair) {
            System.exit(0);

        } else if (source == btnLimpar) {
            // Limpa o nome
            txtNome.setText("");
            // Limpa todos os campos de nota usando o vetor
            for (int i = 0; i < NUMERO_DE_NOTAS; i++) {
                txtNotas[i].setText("");
            }

            // Limpa os resultados
            lblResultadoNome.setText("Aluno: ");
            lblResultadoMedia.setText("Média Final: ");
            lblResultadoSituacao.setText("Situação: ");

        } else if (source == btnCalcular) {
            try {
                // 1. Obter nome
                String nome = txtNome.getText();

                // 2. Criar e preencher o vetor de notas
                double[] notas = new double[NUMERO_DE_NOTAS];
                for (int i = 0; i < NUMERO_DE_NOTAS; i++) {
                    notas[i] = Double.parseDouble(txtNotas[i].getText());
                }

                // 3. Calcular a média usando o vetor
                double media = calcularMedia(notas);

                // 4. Determinar a situação
                String situacao;
                if (media < 6.0) {
                    situacao = "Ruim";
                } else if (media <= 7.0) { // Não precisa do >= 6.0, já foi filtrado
                    situacao = "Mais ou menos";
                } else { // media > 7.0
                    situacao = "Muito bom";
                }

                // 5. Exibir os resultados
                lblResultadoNome.setText("Aluno: " + nome);
                lblResultadoMedia.setText("Média Final: " + String.format("%.2f", media));
                lblResultadoSituacao.setText("Situação: " + situacao);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Por favor, insira apenas números válidos nas notas.",
                        "Erro de Entrada",
                        JOptionPane.ERROR_MESSAGE);
                lblResultadoSituacao.setText("Situação: Erro!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Ocorreu um erro inesperado.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Método auxiliar para calcular a média de um vetor de notas.
     * @param notas O vetor de notas.
     * @return A média aritmética das notas.
     */
    private double calcularMedia(double[] notas) {
        if (notas == null || notas.length == 0) {
            return 0.0;
        }

        double soma = 0.0;
        // Loop "for-each" para somar todos os elementos do vetor
        for (double nota : notas) {
            soma += nota;
        }

        return soma / notas.length;
    }


    /**
     * Método principal (main) que inicia a aplicação.
     */
    public static void main(String[] args) {
        // Garante que a interface gráfica seja criada na thread correta
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraMediaApp().setVisible(true);
            }
        });
    }
}
