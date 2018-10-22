# This Repository contains code for Can-Exchange Backend

## Configuration

	To change the config parameters of this application please refer `config` table.

		`CAN_CONTRACT` Contract address for CAN.
		`CAN_INFURA` Infura key, to generate please visit infura.io
		`CAN_PASSWORD` Your wallet key password.
		`ETHER_ADDRESS` Staging address where ETH would be accepted.
		`GCLOUD_KEY` Your Google AUTH Key JSON.
		`PROJECT_ID` Your Google project ID.
		`BUCKET_NAME` Your bucket name to be used.
		`OBJECT_NAME` Your Wallet Key file name and path in the bucket.
		
## ERC20 Token Listing
	
	Refer `tokenerc20` table, the column definition are as follows :-
	`address` of erc20 token
	`decimals` of erc20 token
	`name` of erc20 token
	`symbol` of erc20 token
	`tokenid` is image file name of erc20 token. Please refer front-end `assets/tokens` folder.
	`status` is as follows :-
	
		There are 3 statues for the tokens
			1. STATUS `0` - Token is disabled
			2. STATUS `1` - Token is enabled
			3. STATUS `2` - Token is enabled but disabled for Metamask
	
## Database

	SQL script is stored in `src/main/resources/database`
	
	
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
