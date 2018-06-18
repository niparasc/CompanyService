# CompanyService

(educational project)

A RESTful web service for creating companies with beneficial owners. For data storage the system is using the in-memory H2 database. The project was developed in Spring Boot.

## Database Schema

* Company [id, name, address, city, country, email, phone, beneficialOwners]
* BeneficialOwner [id, firstName, lastName, companies]

Company is associated with the BeneficialOwner with a ManyToMany relationship. So, there is a third table associating the first two by their IDs.

## API Error Responses

The API has a standard format for error reporting, containing an app specific code and description of the error. Here is a validation error response.

```
{
	"meta": {
		"code": 1000,
		"error": "VALIDATION_ERROR",
		"info": "The system cannot process the request due to validation errors"
	},
	"response": {
		"name": [
			"The name 'Company A' is taken by another company"
		]
	}
}
```

## Authentication

Currently there is no authentication. The service is completely open.

There are various options for securing it, like HTTP Basic Authentication, API Keys, and OAuth, each one with its pros and cons with OAuth being the most advanced.

Selecting an authentication approach depends mostly on the requirements of the API and not so much to the specifics of the alternatives (besides, all three do provide authentication). Of course, some are more secure than the others (e.g. HTTP Basic Authentication puts the username/password pair at risk as it forces the end user to expose it). Ideally, I would incorporate all three of them as is the case with the biggest service providers in the industry (Twitter, Google, etc). HTTP Basic Authentication would fit in the frontend. API Keys would fit to mobile clients and OAuth would fit both.

In case requirements for security become more strict, I would also incorporate 2-factor authentication.

In the API keys approach, the service will be assigning a unique key (token) to first time users and then the users will have to provide it in all requests made to the service (as a header). This is an industry standard and widely deployed authentication solution. In addition to providing the option of purging the key from both the service operators and the end users, it can be further enhanced by making the keys expire after a certain period of time.

OAuth is an open authentication and authorization standard that involves a third entity in the authentication process (the actual authentication is delegated to a third party OAuth Provider, e.g. Twitter or a custom in-house provider).

## Redundancy

Redundancy can be achieved in various ways:

* Keeping the application and database servers separate.
* Deploying failover app and db servers. When one fails, the load is routed to the other.
* Replicate databases (ideally to different regions).
* Deploy to more than one location.
* Upgrade/maintain the platform software stack.
* Incorporate retries into the service. If the first call did not respond right away, repeat it after a while. Add a limit to the retries.
* Use caching in both ends (back/front).
* Prepare load/performance tests.
* Add enough tracing to allow quick recoveries in case of software defects.

## cURL doc

### Company

#### Create Company

Example Request

```
curl --request POST \
  --url https://mighty-caverns-62961.herokuapp.com/companies \
  --header 'content-type: application/json' \
  --data '{
	"name": "Company A",
	"address": "Address of Company A",
	"city": "City of Company A",
	"country": "Country of Company A",
	"email": "Email of Company A",
	"phone": "Phone of Company A",
	"beneficialOwners": [
		{
			"id": 2
		}
	]
}'
```

Example Response

```
No Response
```

#### Get All Companies

Example Request

```
curl --request GET \
  --url https://mighty-caverns-62961.herokuapp.com/companies
```

Example Response

```
[
	{
		"id": 1,
		"name": "Microsoft Corporation",
		"address": "Microsoft Redmon campus",
		"city": "Redmond",
		"country": "USA",
		"email": "microsoft@microsoft.com",
		"phone": "333-444-5555",
		"beneficialOwners": [
			{
				"id": 2,
				"firstName": "Bill",
				"lastName": "Gates"
			}
		]
	},
	{
		"id": 3,
		"name": "Apple Inc.",
		"address": "1 Apple Park Way",
		"city": "Cupertino",
		"country": "USA",
		"email": "apple@apple.com",
		"phone": "222-333-4444",
		"beneficialOwners": [
			{
				"id": 5,
				"firstName": "Steve",
				"lastName": "Jobs"
			},
			{
				"id": 4,
				"firstName": "Tim",
				"lastName": "Cook"
			}
		]
	}
]
```

#### Get Company By Id

Example Request

```
curl --request GET \
  --url https://mighty-caverns-62961.herokuapp.com/companies/11
```

Example Response

```
{
	"id": 1,
	"name": "Microsoft Corporation",
	"address": "Microsoft Redmon campus",
	"city": "Redmond",
	"country": "USA",
	"email": "microsoft@microsoft.com",
	"phone": "333-444-5555",
	"beneficialOwners": [
		{
			"id": 2,
			"firstName": "Bill",
			"lastName": "Gates"
		}
	]
}
```

#### Update Company

Example Request

```
curl --request PUT \
  --url https://mighty-caverns-62961.herokuapp.com/companies/3 \
  --header 'content-type: application/json' \
  --data '{
	"name": "Company B2",
	"address": "Address of Company B",
	"city": "City of Company B",
	"country": "Country of Company B",
	"email": "Email of Company B",
	"phone": "Phone of Company B",
	"beneficialOwners": [
		{
			"id": 2
		}
	]
}'
```

Example Response

```
No Response
```

#### Add Beneficial Owners to Company

Example Request

```
curl --request POST \
  --url https://mighty-caverns-62961.herokuapp.com/companies/1/beneficialOwners \
  --header 'content-type: application/json' \
  --data '{
	"beneficialOwnerIds": [
			"5", "4"
	]
}'
```

Example Response

```
No Response
```

### Beneficial Owner

#### Create Beneficial Owner

Example Request

```
curl --request POST \
  --url https://mighty-caverns-62961.herokuapp.com/beneficialOwners \
  --header 'content-type: application/json' \
  --data '{
	"firstName": "Thorin",
	"lastName": "Oakenshield"
}'
```

Example Response

```
No Response
```
#### Get All Beneficial Owners

Example Request

```
curl --request GET \
  --url https://mighty-caverns-62961.herokuapp.com/beneficialOwners
```

Example Response

```
[
	{
		"id": 2,
		"firstName": "Bill",
		"lastName": "Gates"
	},
	{
		"id": 4,
		"firstName": "Tim",
		"lastName": "Cook"
	},
	{
		"id": 5,
		"firstName": "Steve",
		"lastName": "Jobs"
	}
]
```
