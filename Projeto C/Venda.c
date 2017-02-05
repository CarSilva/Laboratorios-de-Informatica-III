#include <stdlib.h>

#include "Venda.h"
#include "CatálogoClientes.h"
#include "CatálogoProdutos.h"

Venda initVenda(){
	Venda v = malloc (sizeof (struct venda));
	v->produto = initProduto();
	v->cliente = initCliente();
	return v;
}
