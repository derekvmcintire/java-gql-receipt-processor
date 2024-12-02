curl -X POST http://localhost:8080/graphql \
-H "Content-Type: application/json" \
-d '{
  "query": "query { getReceipt(id: \"340dde12-33c4-452e-95f6-4b917a1bdead\") { id store date total items { id name quantity price } } }"
}'
