# miniprojectk8s





![image](https://github.com/limaoxuan/miniprojectk8s/blob/master/simple%20architecture%20.png)


#### env:

Google cloud or minikube





------

#### HOW TO RUN 

If you have any questions please let me know. I will fix it as soon as possible

#### Minikube

sh init_minikube.sh

sh run.sh

------

##### Google cloud

Into your cloud cluster:

example

gcloud container clusters xxxxxxxx

sh run.sh

------



#### DataBase

Mysql

------

#### Secret

Client JWT

Micservices    secrets.yaml API-KEY

------

#### how to get ip

##### minikube 

get minikube external ip

minikube service ea-gateway-service --url

using 30005 port to test.

------

##### Google Cloud

**35.222.223.145**

Google cloud external ip port

Ip: 35.222.223.145:30005



------

API:

#### Register

POST JSON

http://35.222.223.145:30005/api/account/register

{"firstName":"Li","lastName":"Maoxuan","email":"123456@qq.com","password":"123456","role":"admin"}



------

#### Login

POST JSON

http://35.222.223.145:30005/api/account/login

{"email":"563157031@qq.com","password":"123456"}



**if login success,  response data include  token**

**Below request must add this token in header**



------

#### Product

AddProduct

POST JSON

http://35.222.223.145:30005/api/product/

{"vendor":"123","name":"test","productStock":1000}



List All Product

GET

http://35.222.223.145:30005/api/product/



------

#### Order

GET

show user order

http://35.222.223.145:30005/api/order/



POST JSON

add order

{"orderProductList":[{
	"units":"5","productId":1,"price":30
}],"shipAddress":"mum","paymentMethod":"paypal"}



------

#### Payment

GET 

Paypal       1  is orderId

http://35.222.223.145:30005/api/payment/paypal/1

Bank

http://35.222.223.145:30005/api/payment/bank/1

Creditcard

http://35.222.223.145:30005/api/payment/credit/1







------

#### Internal container port

gateway : 10000 

auth : 10001

account  : 10002

product : 10003

order : 10004

payment : 10005

paypal : 10006

bank : 10007

credit : 10008





