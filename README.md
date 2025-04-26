# springboot-APIGateway-ServiceDiscovery

**Eureka Server** - 

Register Instances of all micorservices

![image](https://github.com/user-attachments/assets/902fbcab-6e6a-48a5-b058-71a56af6b458)




**APIGATEWAY** - 

AUTHENTICATE -  REQUEST TOKEN TO AUTH-SERVER 

**Api gateway endpoint - Token fetched from Auth- server - **

![image](https://github.com/user-attachments/assets/d6c3dd50-5a80-4363-818c-b17d851d9322)

**Routing handled to Auth-Server to fetch the token from API-Gateway - **

![image](https://github.com/user-attachments/assets/d3c6bd0b-42bf-48fe-9576-44b768a85fde)






**Api gateway endpoint - to hit Order service - **


**Authorization - Bearer Token**

![image](https://github.com/user-attachments/assets/0bef2adb-d496-40dc-a230-352d5f7cade0)


**401 UnAuthorized - Token Invalid**

![image](https://github.com/user-attachments/assets/189469d1-6251-4924-bd4f-b95d6d788941)



**Api gateway endpoint - to hit Product Service - **

![image](https://github.com/user-attachments/assets/c016dce6-3586-4ff5-808e-5081513efc00)



