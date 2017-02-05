#ifndef catclientes_h

#define catclientes_h

#include <stdbool.h>

typedef struct clientes *CatClientes;
typedef struct cliente *Cliente;
typedef struct conjclientes *ConjClientes;

Cliente initCliente(void);

Cliente criaCliente (Cliente, char*);

char* getCliente (Cliente);

CatClientes initCatClientes(void);

CatClientes insereCliente (CatClientes, Cliente);

bool existeCliente (CatClientes, Cliente);

void removeCatClientes (CatClientes);

int totalClientes (CatClientes);

int totalClientesLetra (CatClientes, char);

ConjClientes initConjClientes ();

ConjClientes clientesPorLetra (ConjClientes, CatClientes, char);

#endif
