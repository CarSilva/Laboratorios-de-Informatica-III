#ifndef test_h

#define test_h

#include "CatálogoProdutos.h"
#include "CatálogoClientes.h"

#define MAX_BUF_VEN 128

typedef struct dados{
        int erroCliente;
        int erroProduto;
        int erroComum;
        int totalInvalido;
        int totalValido;
        int total;
} *DATA;

typedef struct teste{
        double faturacao;
        int unidades;
        int filial[3];
        int zero;
} *TESTE;

typedef struct cliente{
	int registos;
	int quantidade;
	double pagamento;
	int compras[12];
} *CLIENT;

typedef struct produto{
	int vendasN[12];
	int vendasP[12];
	double faturacaoN[12];
	double faturacaoP[12];
} *PRODUCT;

void testGeralUM (CatClientes, CatProdutos);

void testGeralDOIS (void);

void testClient(void);

void testProduct(void);

#endif
