#ifndef venda_h

#define venda_h

#include "CatálogoClientes.h"
#include "CatálogoProdutos.h"

typedef struct venda{
        Produto produto;
        double preco;
        int quantidade;
        char tipo;
        Cliente cliente;
        int mes;
        int filial;
} *Venda;

Venda initVenda (void);

#endif
