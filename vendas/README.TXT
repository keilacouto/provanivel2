# Executar docker com postgresql+pgadmin
cd ~/Projetos/keila/teste/vendas
sudo docker-compose up

##### REST para criar o Produto
curl --location --request POST 'http://localhost:8080/api/produto' \
--header 'Content-Type: application/json' \
--data-raw '{
    "descricao":"produto 1",
    "valorUnitario": 150.0,
    "tipo": "PRODUTO",
    "status":"ATIVO"
}'

##### REST para atualizar o Produto
curl --location --request PUT 'http://localhost:8080/api/produto/eb2df0db-fb07-4cda-894f-bd22d6ca3a14' \
--header 'Content-Type: application/json' \
--data-raw '{
    "descricao":"produto 2",
    "valorUnitario": 200.0,
    "tipo": "PRODUTO",
    "status":"ATIVO"
}'

##### REST para buscar o Produto pelo id
curl --location --request GET 'http://localhost:8080/api/produto/eb2df0db-fb07-4cda-894f-bd22d6ca3a14'

##### REST para listar os Produtos
curl --location --request GET 'http://localhost:8080/api/produto'

##### REST para listar os Produtos por página
curl --location --request GET 'http://localhost:8080/api/produto/page?page=0&size=5&sort=id'

##### REST para criar o Pedido
curl --location --request POST 'http://localhost:8080/api/pedido' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dataPedido": "2022-11-20T16:18:47.148+00:00",
    "status": "ABERTO",
    "itensPedido": []
}'

##### REST para atualizar o Pedido
curl --location --request PUT 'http://localhost:8080/api/pedido/7cd1b00f-6a70-48fc-bfd5-38e5f1e0db38' \
--header 'Content-Type: application/json' \
--data-raw '{
    "dataPedido": "2022-11-20T16:18:47.148+00:00",
    "status": "ABERTO",
    "itensPedido": []
}'

##### REST para buscar o Pedido pelo id
curl --location --request GET 'http://localhost:8080/api/pedido/7cd1b00f-6a70-48fc-bfd5-38e5f1e0db38'

##### REST para listar os Pedidos
curl --location --request GET 'http://localhost:8080/api/pedido'

##### REST para listar os Pedidos por página
curl --location --request GET 'http://localhost:8080/api/pedido/page?page=0&size=5&sort=id'

##### REST para criar o ItemPedido
curl --location --request POST 'http://localhost:8080/api/itempedido' \
--header 'Content-Type: application/json' \
--data-raw '{
    "pedido": {
        "id": "7cd1b00f-6a70-48fc-bfd5-38e5f1e0db38"
    },
    "produto": {
        "id": "bd3c3e92-6d25-4cdc-ab9e-9688a54e1adc"
    },
    "quantidade": 1,
    "percentualDesconto": 10.00  
}'

##### REST para atualizar o ItemPedido
curl --location --request PUT 'http://localhost:8080/api/itempedido/0b7a499e-0888-4670-b15d-ec1af6fe6883' \
--header 'Content-Type: application/json' \
--data-raw '{
    "pedido": {
        "id": "7cd1b00f-6a70-48fc-bfd5-38e5f1e0db38"
    },
    "produto": {
        "id": "bd3c3e92-6d25-4cdc-ab9e-9688a54e1adc"
    },
    "quantidade": 2,
    "percentualDesconto": 15.00  
}'

##### REST para buscar o ItemPedido pelo id
curl --location --request GET 'http://localhost:8080/api/itempedido/0b7a499e-0888-4670-b15d-ec1af6fe6883'

##### REST para listar os Itens do Pedido
curl --location --request GET 'http://localhost:8080/api/itempedido'

##### REST para listar os Itens do Pedido por página
curl --location --request GET 'http://localhost:8080/api/itempedido/page?page=0&size=5&sort=id'
