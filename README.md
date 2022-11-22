
# Automated Ticketing System

This is an idea to create an `Automated Ticketing System` which will go scan the system and create the tickets so hired agent will be able to resolve the ticket even before the customer reports.


## API Reference

#### Get all tickets sorted as per the priority

`Base URL: {protocol}://{host}:{port}`

```http
  GET /v1/api/tickets
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `none`    |          |                            |



## Documentation

`Open API Specs: {Base URL}/api-docs`

`H2 Storage Console: {Base URL}/h2-console/login.do`

| username | password |
| :--------| :------- |
| `majid`  | `empty`  |

For the implementation of an idea to create an `Automated Ticketing System` in which we have preloaded deliveries in the database.

- Deliveries are stored in `delivery_infos` table.
- System to monitor the deliveries with a regular interval time e.g. 30 seconds.
- System to calculate the priority of each delivery by taking into consideration the delivery details.
- If the system determined that the delivery speedup is required then a ticket will be opened in CS queue.

To achieve the objective for determining the priority, an `rule based engine (RBE*)` is introduced,

RBE works with following entities

- operator codes
- data types
- evaluation attributes
- rules
- and use cases

| operator codes             | value    |
| :--------                  | :------- |
| `Equals To`                | `=`      |
| `Not Equals To`            | `!=`     |
| `Greater Than`             | `>`      |
| `Greater Than or Equals To`| `>=`     |
| `Less Than`                | `<`      |
| `Less Than or Equals To`   | `<=`     |

##

| data types | value    |
| :--------  | :------- |
| `Numeric`  | `N`      |
| `String`   | `S`      |
| `Decimal`  | `D`      |
| `Time`     | `T`      |

##

| evaluation attributes | example value |
| :--------             | :-------      |
| `Customer Type`       | `VIP`         |

##

- If system found any delivery pending, then system will calculate the priority of each delivery using RBE.
- RBE takes the delivery info as an evaluation data.
- Checks that if any use cases are configured by the admin/ manager to calculate the priority.
- Every usecase will be configured with the rule, based on qualification criteria, e.g. if the use case to qualify the priority criteria is that the customer is VIP then delivery priority is high.
    - So use case is, customer is VIP. Priority is high.     
- In the above example, rule definition will be done using the evaluation attribute i.e. Customer Type along with operator code (Equals To), having data type (String) for the customer type value.
- Evaluation can be done with the fixed value like 'VIP' (comes as part evaluation data from delivery info), and with an another attribute as well.
- For the usecase, estimation time is greater than the expected time. Rule is defined with attribute type for comparison.
    - Here, expected time comes as part of evaluation data from delivery info and estimation time is calculated using the given formula and made part of evaluation data, so that can be compared with attribute.
- By this way, system can be configured in a way to evaluate other factors that need to be considered for the prioritization of the tickets.
- Like, if the distance from destination is low then rule can be configured to make the delivery with medium priority.
- And, if the distance from destination is high, then consider it as low priority but before the expected time.
- Usecases executed based on execution order defined with the usecase, ticket is opened in CS queue once the any of the first rule is qualified.

##
## Environment Setup

For setting context path for this project, you will need to update the following variable to application-{active profile}.yaml file

`server.contextPath`

Currently active profile is `prod` in pom.xml file
## 
## Tech Stack

**Backend:** Java, Spring Boot

**Storage:** h2
