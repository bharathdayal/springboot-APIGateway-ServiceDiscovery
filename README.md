# springboot-APIGateway-ServiceDiscovery

**Eureka Server** - 

Register Instances of all micorservices,
**Product, Order and RxProduct services has multiple Instances**

![image](https://github.com/user-attachments/assets/720b49ee-47b1-4a31-989c-8d6bef1b8a0c)





**APIGATEWAY** - 

AUTHENTICATE -  REQUEST TOKEN TO AUTH-SERVER 

**Api gateway endpoint - Token fetched from Auth- server**

![image](https://github.com/user-attachments/assets/d6c3dd50-5a80-4363-818c-b17d851d9322)

**Routing handled to Auth-Server to fetch the token from API-Gateway**

![image](https://github.com/user-attachments/assets/d3c6bd0b-42bf-48fe-9576-44b768a85fde)






**Api gateway endpoint - to hit Order service**

Load Balancer routes traffice to instances registered using Service Discovery


**Authorization - Bearer Token**

![image](https://github.com/user-attachments/assets/0bef2adb-d496-40dc-a230-352d5f7cade0)


**401 UnAuthorized - Token Invalid**

![image](https://github.com/user-attachments/assets/189469d1-6251-4924-bd4f-b95d6d788941)



**Api gateway endpoint - to hit Product Service**

Load Balancer routes traffice to instances registered using Service Discovery

![image](https://github.com/user-attachments/assets/c016dce6-3586-4ff5-808e-5081513efc00)


**WebClient Router config**

// Order Service Route
                .route("order-service",

                        route -> route
                                .path("/api/order/**")
                                .filters(f -> f.filter(jwtFilter)
                                        .rewritePath("/api/order/v1", "/api/order"))
                                .uri("lb://order-service")

                )

                // Product Service Route
                .route("product-service",
                        route -> route
                                .path("/api/product/**")
                                .filters(f -> f.filter(jwtFilter).rewritePath("/api/product/v1", "/api/product"))
                                .uri("lb://product-service")
                )



