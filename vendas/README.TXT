# Executar docker com postgresql+pgadmin
cd ~/Projetos/keila/teste/vendas
sudo docker-compose up

##### REST para criar o produto
curl --location --request POST 'http://localhost:8080/api/produto' \
--header 'Content-Type: application/json' \
--data-raw '{
    "descricao":"produto 3",
    "valorUnitario": 150.0,
    "tipo": "PRODUTO",
    "status":"ATIVO"
}'

##### REST para criar o produto


