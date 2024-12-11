package modelo;

import execucao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna) {
        try {
            campos.parallelStream()
             .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
             .findFirst()
             .ifPresent(c -> c.abrir());
        }catch (ExplosaoException e){
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void marcar(int linha, int coluna) {
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst()
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++) {
            for (int coluna = 0; coluna < colunas; coluna++) {
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicinarVizinho(c2);
            }
        }
    }
    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = c -> c.isMinado();
        do {
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar() {
        campos.stream().forEach(c -> c.reiniciarJogo());
        sortearMinas();
    }

    public String toString() {
        StringBuilder sd = new StringBuilder();
        sd.append("  ");
        for (int c = 0; c < colunas; c++){
            sd.append(" " );
            sd.append(c);
            sd.append(" ");
        }
        sd.append("\n");
        int i = 0;
        for (int l = 0; l < linhas; l++) {
            sd.append(l);
            sd.append(" ");
            for (int c = 0; c < colunas; c++) {
                sd.append(" ");
                sd.append(campos.get(i));
                sd.append(" ");
                i++;
            }
            sd.append("\n");
        }
        return sd.toString();
    }

}

