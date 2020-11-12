Written as a Maven project

Requests for getting and posting are as follows

Get request to make list of IPs - localhost:8080/makelist?cidr=<cidr notation>

Get request to retrieve list of IPs - localhost:8080/getlist

Post request to acquire a list (in curl) - curl -d "address=<desired address> -X POST localhost:8080/acquireip

Post request to release a list (in curl) - curl -d "address=<desired address> -X POST localhost:8080/releaseip
