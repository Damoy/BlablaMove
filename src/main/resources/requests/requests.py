export JAVA_HOME=/HOME/user/jdk1.8.191/


#Create Offer
--
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d "{"event":"create-offer","data":{"ownerID":"Machin@me.fr", "status":"AWAITING_CONFIRMATION", "price":"0", "startCity":"startA", "endCity":"endA", "capacity":"3" }}" "localhost:8080/BBM/OFFERS"
--

#TODO change weight into capacity in m³
#Consult Offers 
--
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"consult-offers","data": {"weight": "5", "volume":"6", "date":"5" },"filters": {"weight": "2","startAddress": "startA","endAddress": "endA","maxPrice": "100"}}' "localhost:8080/BBM/OFFERS"
--

#ASK OFFER
#Offerid is generated by a procedure => change it plage.
--
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event":"ask-offer" ,"data": {"offerID": "Machin@me.fr1541337161553_50","buyerID": "client@daniel.dog","weight": "5", "volume":"6", "date":"5" }}' "localhost:8080/BBM/OFFERS"
--
#CONSULTES TES OFFRES ET CROIS EN TES REVES
--
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "consult-awaiting-offers" ,"data": {"ownerID": "Machin@me.fr"}}' "localhost:8080/BBM/OFFERS"
--
#VALIDES TES OFFRES ET CROIS EN TA MORT
# hey ! le transaction ID il est pas bon change le. Ecraseur d'insecte volant.
--
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-awaiting-offers" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"
--

#
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-receipt" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "claim-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"

curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"event": "confirm-deposit" ,"data": {"transactionID": "1541337287184"}}' "localhost:8080/BBM/OFFERS"


