# Inventory

This is bare minimum implementation without any bonus points.
Rest endpoints return and accept json.

## Technology choices

- Java 17
- [Dropwizard](dropwizard.io)
- Postgres
- [JDBI](jdbi.org)
- Gradle
- [slf4j](slf4j.org)

## Tests

The current implementation almost lacks business logic so no unit tests are provided at this point.
There are couple of end-to-end integration tests though based on [testcontainers](https://testcontainers.com/), which should cover the functionality existing at the moment.

# Build

`gradle clean jibDockerBuild test`

We need docker image built before the integration tests.

# Run locally

`docker compose up`

## Curl inventory
`curl -s 'localhost:8080/products?limit=10&offset=10' | jq .`

## Update product
`curl -s -X PUT 'localhost:8080/products/prod2281%23prod100091003095' -H "Content-Type: application/json" -d @p.json | jq .`

# Enhancements
- Add DB migration support
- Authentication on top
- Category (3) and subcategory (29) deserves its separate entity
- Order is probably to be split into order and order item
- Implement partial product updates
- unit tests obviously needed