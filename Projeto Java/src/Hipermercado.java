
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Esta classe implementa todas as caracteristicas de um Hipermecado (com ou sem filiais),
 * desde os clientes que estao registados e efetuam compras, os produtos em venda,
 * a faturação de cada filial do hipermecado e globalmente tambm, entre outros.
 * 
 * @author Grupo28
 */
public class Hipermercado implements Serializable{
    //Variáveis de instância
    
    /**
     * Catálogo de clientes
     */
    private CatalogoClientes clientes;
    
    /**
     * Catálogo de produtos
     */
    private CatalogoProdutos produtos;
    
    /**
     * Faturação
     */
    private Faturacao faturacao;
    
    /**
     * Filiais existentes
     */
    private List<Filial> filial;
    
    /**
     * Número de filiais
     */
    private int numFiliais;
    
    /**
     * Nome de um ficheiro
     */
    private String nomeFich;
    
    /**
     * Número de vendas inválidas
     */
    private int invalidas;
    
    //Construtores
    
    /**
     * Construtor vazio da classe, inicializa deviamente todos os campos da classe
     */
    public Hipermercado(){
        this.clientes = new CatalogoClientes();
        this.produtos = new CatalogoProdutos();
        this.numFiliais = 3;
        this.faturacao = new Faturacao(this.numFiliais);
        this.filial = new ArrayList<>(this.numFiliais);
        for (int i = 0; i < this.numFiliais; i++){
            this.filial.add(new Filial());
        }
        
        this.invalidas = 0;
    }
    
    /**
     * Métodos de classe
     */
    
    /**
     * Método que modifica o nome de um ficheiro
     * 
     * @param nomeFich O nome do ficheiro que vai se sobrepôr ao existente
     */
    public void setNomeFich(String nomeFich){
        this.nomeFich = nomeFich;
    }

    /**
     * Método que devolve o nome do ficheito atual
     * 
     * @return O nome do ficheiro
     */
    public String getNomeFich() {
        return this.nomeFich;
    }
    /**
     * Método que devolve o número de filiais
     * 
     * @return número de filiais 
     */
    public int getNumFiliais() {
        return numFiliais;
    }

    /**
     * Método que devolve o numero de vendas inválidas
     * 
     * @return O numero de vendas inválidas
     */
    public int getInvalidas() {
        return this.invalidas;
    }
    
    /**
     * Método que insere um cliente ao Catálogo de Clientes
     * 
     * @param cliente O cliente a adicionar
     */
    public void insereCliente (String cliente){
        this.clientes.add(cliente);
    }
    
    /**
     * Método que insere um produto ao Catálogo de Produtos
     * 
     * @param produto O produto a adicionar
     */
    public void insereProduto (String produto){
        this.produtos.add(produto);
        this.faturacao.insere(produto);
    }
    
    /**
     * Método que insere uma venda quer á parte da faturação quer á parte da filial
     * 
     * @param venda A venda a adicionar
     */
    public void insereVenda (Venda venda){
        if (validaVenda(venda)){
            this.faturacao.insere(venda);
            this.filial.get(venda.getFilial() - 1).insere(venda);
        }
        else {
            this.invalidas++;
        }
    }
    
    /**
     * Método que valida um venda
     * 
     * @param venda A venda a validar
     * @return true se a venda for valida, false caso contrário
     */
    private boolean validaVenda (Venda venda){
        if (venda == null) return false;
        if (!this.produtos.existe(venda.getProduto())) return false;
        if (venda.getPreco() < 0.0 || venda.getPreco() > 999.99) return false;
        if (venda.getQuantidade() < 1 || venda.getQuantidade() > 200) return false;
        if (!venda.getTipo().equals("N") && !venda.getTipo().equals("P")) return false;
        if (!this.clientes.existe(venda.getCliente())) return false;
        if (venda.getMes() < 1 || venda.getMes() > 12) return false;
        if (venda.getFilial() < 1 || venda.getFilial() > this.numFiliais) return false;
        
        return true;
    }
    
    /**
     * Método que testa se determinado produto existe
     * 
     * @param produto O produto a testar
     * @return true se existir, false caso contrário
     */
    public boolean existeProduto (String produto){
        return this.produtos.existe(produto);
    }
    
    /**
     * Método que testa se determinado cliente existe
     * 
     * @param cliente O cliente a testar
     * @return true se existir, false caso contrário
     */
    public boolean existeCliente (String cliente){
        return this.clientes.existe(cliente);
    }
    
    /**
     * Método que devolve o total de produtos do Catálogo de produtos
     * 
     * @return O numero de produtos existentes
     */
    public int getTotalProdutos (){
        return this.produtos.sizeTotal();
    }
    
    /**
     * Método que devolve o total de clientes do Catálogo de produtos
     * 
     * @return O numero de clientes existentes
     */
    public int getTotalClientes (){
        return this.clientes.sizeTotal();
    }

    /** 
     * Total de produtos diferentes comprados
     * 
     * @return O numero de produtos diferentes
     */
    public int produtosDiferentesComprados(){
        return this.faturacao.produtosDiferentesComprados();
    }

    /**
     * Número de produtos diferentes comprados num dado mes, por um dado cliente
     * 
     * @param mes O mes que queremos analisar
     * @param cliente O clientes sobre o qual obtemos p resultado
     * @return O numero de produtos diferentes comprados num mes e por um cliente
     */
    public int produtosDiferentes(int mes, String cliente){
        Set<String> produtosDiferentes = new HashSet<>();
        for (int i = 0; i < this.numFiliais; i++){
            try{
                produtosDiferentes.addAll(this.filial.get(i).produtosDiferentes(mes, cliente));
            }
            catch(NullPointerException e){}
        }
        return produtosDiferentes.size();
    }

    /**
     * Número total de clientes que realizaram compras
     * 
     * @return O numero de clientes que compraram, globalmente
     */
    public int clientesCompraram() {
        Set<String> clientesCompraram = new HashSet<>();
        for (int i = 0; i < this.numFiliais; i++){
            clientesCompraram.addAll(this.filial.get(i).clientesCompraram());
        }
        return clientesCompraram.size();
    }
    
    /**
     * Método que devolve o número de vendas zero
     * 
     * @return o numero de vendas zero
     */
    public int vendasZero() {
        return this.faturacao.vendasZero();
    }

    /**
     * Faturação total do hipermercado
     * 
     * @return A faturação total
     */
    public double faturacao(){
        double f = 0;
        for (int mes = 1; mes <= 12; mes++){
            for (int i = 1; i <= this.numFiliais; i++){
                f += this.faturacao.faturacao(mes, i);
            }
        }
        return f;
    }

    /**
     * Faturação total de uma dado mes
     * @param mes O mês a analisar
     * @return A faturação num mês
     */
    public double faturacao (int mes){
        double f = 0;
        for (int i = 1; i <= this.numFiliais; i++){
            f += this.faturacao.faturacao(mes, i);
        }
        return f;
    }
    
    /**
     * Faturação total num dado mes e filial
     * 
     * @param mes O mês a analisar
     * @param filial A faturação a analisar
     * @return O numero representante da faturação num mês e filial
     */
    public double faturacao(int mes, int filial){
        return this.faturacao.faturacao(mes, filial);
    }

    /**
     * Faturacao total de um dado produto num dado mes
     * 
     * @param mes O mês a analisar
     * @param produto O produto a analisar
     * @return O total da faturação de um produto num mês
     */
    public double faturacao(int mes, String produto){
        return this.faturacao.faturacao(mes, produto);
    }

    /**
     * Numero de compras/vendas de uma dados mes
     * 
     * @param mes O mês a analisar
     * @return O numero de compras no mês em questão
     */
    public int numeroCompras (int mes){
        return this.faturacao.numeroVendas(mes);
    }

    /**
     * Número de compras efetuadas por um dado cliente num dado mes
     * 
     * @param mes O mês a analisar
     * @param cliente O cliente a analisar
     * @return O numero de compras associadas a um cliente e mês
     */
    public int numeroCompras (int mes, String cliente){
        int n = 0;
        for (int i = 0; i < this.numFiliais; i++){
            n += this.filial.get(i).numeroCompras(mes, cliente);
        }
        return n;
    }
    
    /**
     * Número de distintos clientes que compraram num dado mes
     * 
     * @param mes O mês a analisar
     * @return O número de clientes diferentes
     */
    public int clientesDiferentes (int mes){
        Set<String> clientesDiferentes = new HashSet<>();
        for (int i = 0; i < this.numFiliais; i++){
            clientesDiferentes.addAll(this.filial.get(i).clientesDiferentes(mes));
        }
        return clientesDiferentes.size();
    }
    
    /**
     * Número de clientes diferentes que compraram um dado produto num dado mes
     * 
     * @param mes O mês a analisar
     * @param produto O produto a analisar
     * @return O numero de clientes diferentes que compraram um produto num mês
     */
    public int clientesDiferentes(int mes, String produto){
        Set<String> clientesDiferentes = new HashSet<>();
        for (int i = 0; i < this.numFiliais; i++){
            clientesDiferentes.addAll(this.filial.get(i).clientesDiferentes(mes, produto));
        }
        return clientesDiferentes.size();
    }
    
    /**
     * Produtos nunca comprados
     * 
     * @return O numero de produtos nunca comprados
     */
    public List<String> produtosNuncaComprados(){
        Set<String> s = this.faturacao.produtosNuncaComprados();
        List<String> l = new ArrayList<>();
        for (String produto: s){
            l.add(produto);
        }
        return l;
    }

    /**
     * Total gasto por um dados cliente num dado mes
     * 
     * @param mes O mês a analisar 
     * @param cliente O cliente a analisar
     * @return O valor total gasto por um cliente num mês
     */
    public double totalGasto (int mes, String cliente){
        double g = 0;
        for (int i = 0; i < this.numFiliais; i++){
            g += this.filial.get(i).totalGasto(mes, cliente);
        }
        return g;
    }
    
     /**
     * Número de vezes que um dado produto foi comprado num dados mes
     * 
     * @param mes O mês a analisar
     * @param produto O produto a analisar
     * @return O numero de vezes que o produto foi comprado
     */
    public int vezesComprado (int mes, String produto){
        return this.faturacao.vezesComprado(mes, produto);
    }

    /**
     * Produtos mais comprados por um dado cliente (quantidade)
     * 
     * @param cliente O cliente a analisar
     * @return Um conjunto com produtos e quantidades associadas a esse produto
     */
    public List<ParProdutoQuantidade> produtosMaisComprados(String cliente){
        Map<String, Integer> m = new HashMap<>();
        for (int i = 0; i < this.numFiliais; i++){
            Map<String, Integer> m2 = this.filial.get(i).produtosMaisComprados(cliente);
            for (String produto: m2.keySet()){
                Integer quantidade = m.get(produto);
                if (quantidade == null){
                    m.put(produto, m2.get(produto));
                }
                else {
                    m.put(produto, quantidade + m2.get(produto));
                }
            }
        }
        Set<ParProdutoQuantidade> s = new TreeSet<>();
        for (String produto: m.keySet()){
            s.add(new ParProdutoQuantidade (produto, m.get(produto)));
        }
        List<ParProdutoQuantidade> l = new ArrayList<>();
        for (ParProdutoQuantidade p: s){
            l.add(p);
        }
        return l;
    }

    /**
     * Produtos mais vendidos (quantidade)
     * 
     * @return Um conjunto com os produtos mais vendidos, contendo cada posição desse conjunto
     * o produto em questao, os clientes que o compraram e a quantidade comprada
     */
    public Set<TriploProdutoQuantidadeLista> produtosMaisVendidos(){
        Map<String, ParQuantidadeLista> m = new HashMap<>();
        for (int i = 0; i < this.numFiliais; i++){
            Map<String, ParQuantidadeLista> m2 = this.filial.get(i).produtosMaisVendidos();
            for (String produto: m2.keySet()){
                ParQuantidadeLista p = m.get(produto);
                ParQuantidadeLista p2 = m2.get(produto);
                if (p == null){
                    m.put(produto, p2);
                }
                else{
                    p.incQuantidade(p2.getQuantidade());
                    p.addLista(p2.getLista());
                }
            }
        }
        Set<TriploProdutoQuantidadeLista> s = new TreeSet<>();
        for (String produto: m.keySet()){
            ParQuantidadeLista p = m.get(produto);
            s.add(new TriploProdutoQuantidadeLista(produto, p.getQuantidade(), p.getLista()));
        }
        return s;
    }

    /**
     * Maiores compradores (faturacao) de uma dada filial
     * 
     * @param numeroFilial  A filial que queremos analisar 
     * @return Um conjunto, preenchido, cada posição com o cliente e a faturação associada a este
     */
    public Set<ParClienteFaturacao> maioresCompradores (int numeroFilial){
        return this.filial.get(numeroFilial-1).maioresCompradores();
    }
    
    /**
     * Dado o código de um produto, determinar os clientes que mais o compraram (quantidade)
     * 
     * @param produto O produto a analisar
     * @return Um conjunto preenchido, em cada posiçao, com o cliente que comprou o produto,
     * a quantidade que comprou e quanto gastou ao comprar
     */
    public Set<TriploClienteQuantidadeFaturacao> maioresCompradores(String produto){ 
        Map<String, ParQuantidadeFaturacao> m = new HashMap<>();
        for (int i = 0; i < this.numFiliais; i++){
            Map<String, ParQuantidadeFaturacao> m2 = this.filial.get(i).maioresCompradores(produto);
            for (String cliente: m2.keySet()){
                ParQuantidadeFaturacao p = m.get(cliente);
                ParQuantidadeFaturacao p2 = m2.get(cliente);
                if (p == null){
                    m.put(cliente, p2);
                }
                else {
                    p.incQuantidade(p2.getQuantidade());
                    p.incFaturacao(p2.getFaturacao());
                }
            }
        }
        Set<TriploClienteQuantidadeFaturacao> s = new TreeSet<>();
        for (String cliente: m.keySet()){
            ParQuantidadeFaturacao p = m.get(cliente);
            s.add(new TriploClienteQuantidadeFaturacao(cliente, p.getQuantidade(), p.getFaturacao()));
        }
        return s;
    }
    
    /**
     * Clientes que compraram mais produtos diferentes (numero)
     * 
     * @return Um conjunto preenchido, em cada posição, com um código de cliente
     * e o conjunto de produto diferentes comprados
     */
    public Set<ParClienteLista> maisProdutosDiferentes (){
        Map<String, Set<String>> m = new HashMap<>();
        for (int i = 0; i < this.numFiliais; i++){
            Map<String, Set<String>> m2 = this.filial.get(i).maisProdutosDiferentes();
            for (String cliente: m2.keySet()){
                Set<String> s = m.get(cliente);
                if (s == null){
                    m.put(cliente, m2.get(cliente));
                }
                else{
                    s.addAll(m2.get(cliente));
                }
            }
        }
        Set<ParClienteLista> s = new TreeSet<>();
        for (String cliente: m.keySet()){
            s.add(new ParClienteLista(cliente, m.get(cliente)));
        }
        return s;
    }
}
