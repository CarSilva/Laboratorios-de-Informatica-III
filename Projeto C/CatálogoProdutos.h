#ifndef catprods_h

#define catprods_h

#include <stdbool.h>

typedef struct produtos *CatProdutos;
typedef struct produto *Produto;
typedef struct conjprodutos *ConjProdutos;

Produto initProduto (void);

Produto criaProduto (Produto, char*);

char* getProduto (Produto);

CatProdutos initCatProdutos (void);

CatProdutos insereProduto (CatProdutos, Produto);

bool existeProduto (CatProdutos, Produto);

void removeCatProdutos (CatProdutos);

int totalProdutos (CatProdutos);

int totalProdutosLetra (CatProdutos, char);

ConjProdutos initConjProdutos ();

ConjProdutos produtosPorLetra (ConjProdutos, CatProdutos, char);

#endif
