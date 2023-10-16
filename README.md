# Inventory

## Technology choices

- [Dropwizard](dropwizard.io)
- Postgres
- [JDBI](jdbi.org)
- Gradle

# Build

`gradle clean jibDockerBuild test`

We need docker image for the integration tests.

# Run locally

`docker compose up`

## Curl inventory
`curl -s 'localhost:8080/products?limit=3&offset=20' | jq .`

## Update product
`curl -s -X PUT 'localhost:8080/products/prod2281%23prod100091003095' -H "Content-Type: application/json" -d @p.json | jq .`

# Enhancements
- Add DB migration support
- Authentication on top
- Category (3) and subcategory (29) deserves its separate entity
- Order is probably to be split into order and order item
- Implement partial product updates