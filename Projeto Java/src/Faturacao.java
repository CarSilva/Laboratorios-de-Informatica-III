
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Esta classe representa toda a faturação do hipermecado. Globalmente, por meses e por filiais,
 * tem a informação sobre tudo o que é relacionada com a compra de produtos.
 * 
 * @author Grupo28
 */
public class Faturacao implements Serializable{
    //Variaveis de instância
    
    /**
     * Lista com 12 componentes, cada componente é um map que contem o codigo do produto e os dados do produto,
     * como a faturação quantidade e numero de vendas
     */
    List<Map<String, DadosProduto>> faturacaoProdutos; 
    
    /**
     * Conjunto com códigos de produto nunca comprados
     */
    Set<String> produtosNuncaComprados;
    
    /**
     * Lista com as vendas nulas
     */
    List<Venda> vendasZero;
    
    /**
     * Matriz 12*3 que contem a faturação de cada filial em cada mês do ano
     */
    double[][] faturacao;
    
    int[] numeroVendas;
    
    //Construtures
    
    /**
     * Construtor classe, inicializa todos as variáveis de instância como se pretende
     * @param numFiliais
     */
    public Faturacao(int numFiliais){
        faturacaoProdutos = new ArrayList<>(12);
        for(int i = 0; i < 12; i++){
            faturacaoProdutos.add(new HashMap<>());
        }
        produtosNuncaComprados = new TreeSet<>();
        vendasZero = new ArrayList<>();
        faturacao = new double[12][numFiliais];
        numeroVendas = new int[12];
    }
    
    /**
     * Insere um produto no conjunto produtosNuncaComprados, referente aos produtos que nunca foram comprados
     * 
     * @param produto 
     */
    public void insere (String produto){
        produtosNuncaComprados.add(produto);
    }
    
    /**
     * Insere uma venda na classe Faturacao, atualizando-a consoante as caracteristicas da venda.
     * Se um produto pertence á venda e está nos produtos que nunca foram comprados é tambem retirado de lá.
     * 
     * @param v Uma linha de venda
     */
    public void insere(Venda v){
        Map<String, DadosProduto> m = faturacaoProdutos.get(v.getMes() - 1);
        DadosProduto p = m.get(v.getProduto());
        if (p == null){
            m.put(v.getProduto(), new DadosProduto(v));
        }
        else {
            p.incQuantidade(v.getQuantidade());
            p.incFaturacao(v.getQuantidade() * v.getPreco());
            p.incNumeroVendas();
            m.put(v.getProduto(), p);
        }
        faturacaoProdutos.set(v.getMes() - 1, m);
        
        produtosNuncaComprados.remove(v.getProduto());
        
        if (v.getPreco() == 0.0){
            vendasZero.add(v.clone());
        }
        
        faturacao[v.getMes() - 1][v.getFilial() - 1] += v.getPreco() * v.getQuantidade();
        
        this.numeroVendas[v.getMes() - 1]++;
    }
    
    /**
     * Determina todos os diferentes produtos que foram comprados, globalmente e em todos os meses.
     * 
     * @return O número de produtos diferente que foram comprados 
     */
    public int produtosDiferentesComprados (){
        Set<String> s = new HashSet<>();
        for (int i = 0; i < 12; i++){
            s.addAll(faturacaoProdutos.get(i).keySet());
        }
        return s.size();
    }
    
    /**
     * Determina o número de vendas nulas
     * 
     * @return O numero de vendas nulas
     */
    public int vendasZero(){
        return vendasZero.size();
    }
    
    /**
     * Determina a faturação total de uma dada filial num dado mês.
     * 
     * @param mes O mês que queremos saber a faturação
     * @param filial A filial que queremos analizar
     * @return O valor da faturação no mês e na filial dada
     */
    public double faturacao (int mes, int filial){
        return faturacao[mes-1][filial-1];
    }
    
    /**
     * Determina a faturação de um produto, em todas as filiais, num dado mês
     * 
     * @param mes O mês a que queremos aceder
     * @param produto O produto a analizar
     * @return O valor da faturação do produto dado no mês fornecido
     */
    public double faturacao (int mes, String produto){
        try{
            return faturacaoProdutos.get(mes-1).get(produto).getFaturacao();
        }
        catch(NullPointerException e){
            return 0;
        }
    }
    
    /**
     * Devolve um conjunto com os produtos que nunca foram comprados
     * @return O conjunto dos produtos nunca comprados
     */
    public Set<String> produtosNuncaComprados(){
        Set<String> pnc = new TreeSet<>();
        pnc.addAll(produtosNuncaComprados);
        return pnc;
    }
    
    /**
     * Determina o mumero de vendas de um determinado produto num dado mês
     * @param mes O mes que queremos analizar as vendas
     * @param produto O produto a determinar o numero de vendas
     * @return O numero de vendas do dado produto, no dado mês
     */
    public int vezesComprado(int mes, String produto){
        try{
            return faturacaoProdutos.get(mes-1).get(produto).getNumeroVendas();
        }
        catch(NullPointerException e){
            return 0;
        }
    }
    
    /**
     * Determina um conjunto com ... isto eu acho que nao devia ser aqui
     * @return 
     */
    public Set<ParProdutoQuantidade> produtosMaisVendidos(){
        Map<String, Integer> m = new HashMap<>();
        for (int i = 0; i < 12; i++){
            Map<String, DadosProduto> d = faturacaoProdutos.get(i);
            for (String produto: d.keySet()){
                Integer quantidade = m.get(produto);
                if (quantidade == null){
                    m.put(produto, d.get(produto).getQuantidade());
                }
                else {
                    m.put(produto, d.get(produto).getQuantidade() + quantidade);
                }
            }
        }
        Set<ParProdutoQuantidade> s = new TreeSet<>();
        for (String produto: m.keySet()){
            s.add(new ParProdutoQuantidade(produto, m.get(produto)));
        }
        return s;
    }
    
    /**
     * Método que devolve o número de vendas global num determinado mês.
     * 
     * @param mes O mês pretendido
     * @return O numero de vendas globais no mes dado
     */
    public int numeroVendas(int mes){
        return numeroVendas[mes-1];
    }
}
