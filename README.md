# This Repository contains code for Can-Exchange Backend

## Configuration

	`CAN_CONTRACT` Contract address for CAN.
	`CAN_INFURA` Infura key, to generate please visit infura.io
	`CAN_KEY` Your Wallet Key path.
	`CAN_PASSWORD` Your wallet key password.
	`ETHER_ADDRESS` Staging address where ETH would be accepted.
	
## ERC20 Token Listing
	
	Refer `tokenerc20` table, the column definition are as follows :-
	`address` of erc20 token
	`decimals` of erc20 token
	`name` of erc20 token
	`symbol` of erc20 token
	`tokenid` is image file name of erc20 token. Please refer front-end `assets/tokens` folder.
	
	
## Run App Engine DevServer Locally: 
	
	./mvnw appengine:run -DskipTests -Pprod,no-liquibase
	

## Deploy to App Engine:

	./mvnw appengine:deploy -DskipTests -Pprod,prod-gae,no-liquibase
	

## Development

To start your application in the dev profile, simply run:

    ./mvnw


## Building for production

To optimize the canpay application for production, run:

    ./mvnw -Pprod clean package

To ensure everything worked, run:

    java -jar target/*.war


Refer to [Using JHipster in production][] for more details.

## Testing

To launch your application's tests, run:

    ./mvnw clean test


### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

Then, run a Sonar analysis:

```
./mvnw -Pprod clean test sonar:sonar
```
