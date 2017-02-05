CFLAGS= -ansi
OBJS=$(wildcard *.c)

GereVendas: 
	cc $(CFLAGS) -o GereVendas AVL.c Main.c CatálogoClientes.c CatálogoProdutos.c Venda.c

do: GereVendas
	./GereVendas

clean: 
	rm GereVendas

test: $(OBJS)
	cc  $(CFLAGS) -o GereVendas $(OBJS)
	./GereVendas
	
