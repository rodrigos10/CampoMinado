import execucao.ExplosaoException;
import modelo.Campo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {
    private Campo campo = new Campo(3 ,3);

    @BeforeEach
    void  iniciarCampo(){
        campo = new Campo(3, 3);
    }
    @Test
    void  testeVizinhoRealDistanciaEsquerda() {
        Campo vizinho = new Campo(3, 4 );
        boolean resultado = campo.adicinarVizinho(vizinho);
        assertTrue(resultado);
    }

@Test
void  testeVizinhoDistanciaDireita(){
    Campo vizinho = new Campo(2,3);
    boolean resultado = campo.adicinarVizinho(vizinho);
    assertTrue(resultado);
   }
    @Test
    void  testeVizinhoDistanciaEmbaixo(){
        Campo vizinho = new Campo(4,3);
        boolean resultado = campo.adicinarVizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void  testeVizinhoDistancia2(){
        Campo vizinho = new Campo(2,2);
        boolean resultado = campo.adicinarVizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void  testeNaoVizinho(){
        Campo vizinho = new Campo(1,1);
        boolean resultado = campo.adicinarVizinho(vizinho);
        assertFalse(resultado);
    }
    @Test
    void testeValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }
    @Test
    void testeAlterarMarcacao(){
       campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }
    @Test
    void testeAlterarMarcacaoDuasChamadas(){
       campo.alternarMarcacao();
       campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }
    @Test
    void testeAbrirNaoMinadoNaoMarcado() {
        assertTrue(campo.abrir());
    }
    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }
    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();

        assertThrows(ExplosaoException.class,()->{
            campo.abrir();
        });
    }
    @Test
    void testeAbrirVizinhos(){
        Campo campo11 = new Campo(1,1);

        Campo campo22= new Campo(2,2);
        campo22.adicinarVizinho(campo11);

        campo.adicinarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }
}



