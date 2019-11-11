PATH="${PWD}"
echo "${PATH}"
cd ${PATH}/env/
sh deploy.sh



cd ${PATH}/services/gateway
sh deploy.sh


cd ${PATH}/services/account
sh deploy.sh

cd ${PATH}/services/product
sh deploy.sh


cd ${PATH}/services/order
sh deploy.sh

cd ${PATH}/services/auth
sh deploy.sh

cd ${PATH}/services/payment
sh deploy.sh

cd ${PATH}/services/paypal
sh deploy.sh

cd ${PATH}/services/bank
sh deploy.sh

cd ${PATH}/services/creditcard
sh deploy.sh

