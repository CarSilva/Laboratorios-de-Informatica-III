import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Classe Venda implementada para conter toda a informação relativa a uma venda feita num dado mês e filial,
 * por um dado cliente. Logo apresenta informação sobre o nome do cliente, a filial onde foi vendido o produto, o código do produto,
 * o seu preço, a quantidade ;do produto deste tipo, comprado, o tipo de venda(promocional ou normal) e o mês da venda.
 * 
 * @author Grupo28
 */
public class Venda implements Serializable {
    
    
// variáveis de instância
    private String produto;
    private double preco;
    private int quantidade;
    private String tipo;
    private String cliente;
    private int mes;
    private int filial;
    
   /**
     * Gera um objeto Venda segundo os dados de um objeto dado, neste caso outra venda
     * @param v2    objeto tipo venda
     */
    
    public Venda(Venda v2){
        this.produto = v2.getProduto();
        this.preco = v2.getPreco();
        this.quantidade = v2.getQuantidade();
        this.tipo = v2.getTipo();        
        this.cliente = v2.getCliente();
        this.mes = v2.getMes();
        this.filial = v2.getFilial();
    }
    
    /**
     * Cria um objeto Venda com os dados dados como argumentos.
     * 
     * @param produto   código do produto
     * @param preco     preço do produto
     * @param quantidade    numero de produtos comprados deste tipo nesta venda
     * @param tipo     tipo de compra(normal ou promocional)
     * @param cliente   cliente que realizou a compra
     * @param mes   mês em que foi feita a venda
     * @param filial    filial em que foi feita a venda
     */
    
    public Venda(String produto,double preco,int quantidade, String tipo, String cliente, int mes, int filial){
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.cliente = cliente;
        this.mes = mes;
        this.filial = filial;
    }
    
    /**
     * Método para obter o código do produto vendido
     * 
     * @return      código do produto
     */
    
    public String getProduto(){
        return produto;
    }
    
    /**
     * Método para obter o preço do produto vendido
     * 
     * @return      preço do produto
     */
    
    public double getPreco(){
        return preco;
    }
    
    /**
     * Método que obtem o número de um tipo de produtos vendidos
     * 
     * @return     número total dos produtos
     */
    
    public int getQuantidade(){
        return quantidade;
    }
    
    /**
     * Método para obter o tipo de venda
     * 
     * @return    tipo de venda(normal ou promocional)
     */
    
    public String getTipo(){
        return tipo;
    }
    
    /**
     * Método para obter o nome do cliente que efetuou a compra
     * 
     * @return     nome do cliente
     */
    
    public String getCliente(){
        return cliente;
    }
    
    /**
     * Método que para obter o mês em que foi feita a venda
     * 
     * @return     mês da venda
     */
    
      public int getMes(){
        return mes;
    }
    
    /**
     * Método que obtem o número da filial onde foi feita a venda
     * 
     * @return     número da filial
     */  
      
    public int getFilial(){
        return filial;
    }

    
    /**
     * Gera o HashCode da classe Venda
     * 
     * @return O valor do hash
     */
    public int hashCode() {
        return Arrays.hashCode(new Object[] {this.produto, this.preco, this.quantidade, this.tipo, this.cliente, this.mes, this.filial});
    }
    
    /**
     * Método para se poder igualar objetos
     * 
     * @param o     objeto a ser igualado
     * @return      true ou false dependendo da igualdade
     */
    
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Venda v = (Venda) o;
        return this.produto.equals(v.getProduto()) && this.preco == v.getPreco() &&
               this.quantidade == v.getQuantidade() && this.tipo.equals(v.getTipo())&&
               this.cliente.equals(v.getCliente()) && this.mes == v.getMes()
               && this.filial == v.getFilial();
                
    }
    
    /**
     * Faz uma cópia de um determinado objeto
     * 
     * @return  cópia do objeto em questão
     */
    
    public Venda clone(){
        return new Venda(this);
    }
    
    /**
     * Utiliza os parâmetros do objeto Venda e apresenta-os numa string
     * 
     * @return  string com os parâmetros do objeto 
     */
    
    public String toString(){
        StringBuilder sb = new StringBuilder ();
        sb.append(produto);
        sb.append(" ");
        sb.append(preco);
        sb.append(" ");
        sb.append(quantidade);
        sb.append(" ");
        sb.append(tipo);
        sb.append(" ");        
        sb.append(cliente);
        sb.append(" ");
        sb.append(mes);
        sb.append(" ");
        sb.append(filial);
        sb.append(" ");

        return sb.toString();
    }
}   
