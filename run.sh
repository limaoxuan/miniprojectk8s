#!/usr/bin/env bash
DIR="${PWD}"
echo ${DIR}
cd ${DIR}/env/
echo ${PWD}
/bin/sh ${DIR}/env/deploy.sh


# cd ${DIR}/services/gateway
# /bin/sh deploy.sh


# cd ${DIR}/services/account
# /bin/sh deploy.sh

# cd ${DIR}/services/product
# /bin/sh deploy.sh


# cd ${DIR}/services/order
# /bin/sh deploy.sh

# cd ${DIR}/services/auth
# /bin/sh deploy.sh

# cd ${DIR}/services/payment
# /bin/sh deploy.sh

# cd ${DIR}/services/paypal
# /bin/sh deploy.sh

# cd ${DIR}/services/bank
# /bin/sh deploy.sh

# cd ${DIR}/services/creditcard
# /bin/sh deploy.sh

