curl -X POST http://localhost:8080/graphql \
-H "Content-Type: application/json" \
-d '{
  "query": "query { getReceipt(id: \"1\") { id store date total items { id name quantity price } } }"
}'
