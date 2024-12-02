curl -X POST http://localhost:8080/graphql \
-H "Content-Type: application/json" \
-d '{
  "query": "mutation { addReceipt(input: { store: \"SuperMart\", date: \"2024-12-01\", total: 150.0, items: [{ name: \"Milk\", quantity: 2, price: 2.5 }, { name: \"Bread\", quantity: 1, price: 3.0 }, { name: \"Eggs\", quantity: 12, price: 12.0 }] }) { id store date total items { id name quantity price } } }"
}'
