package modelo;

import execucao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Campo {
    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();

    public Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public boolean adicinarVizinho(Campo vizinho) {
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            aberto = true;

            if (minado) {
                throw new ExplosaoException();
            }
            if (vizinhacaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }

    boolean vizinhacaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean minar() {
        minado = true;
        return true;
    }
    public boolean isMinado(){
        return minado;
    }
        public boolean isMarcado () {
            return marcado;
        }

     void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isAberto(){
        return !isAberto();
        }
    public int getLinha() {
        return linha;
    }
    public int getColuna() {
        return coluna;
    }
    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }
    long minasNavizinhanca(){
        return  vizinhos.stream().filter(v -> v.minado).count();
    }
    void reiniciarJogo(){
        aberto = false;
        minado = false;
        marcado = false;
    }
    public String toString(){
        if (marcado){
            return "x";
        } else if (aberto && minado) {
            return "*";
        } else if (aberto && minasNavizinhanca()>0){
            return Long.toString(minasNavizinhanca());
        } else if (aberto) {
            return " ";
        }else {
            return "?";
        }
    }
}




