# clojure-api-swagger-jwt

This repo contains an implementation of a Clojure API secured 
using JWT as outlined in the series of four blog posts 
"***Securing Clojure Microservices using buddy***" by 
[Magnus Rundberget](http://rundis.github.io/blog/about.html). 
- [Part 1: Creating Auth Tokens](http://rundis.github.io/blog/2015/buddy_auth_part1.html)  
- [Part 2: WebApp authentication and authorization](http://rundis.github.io/blog/2015/buddy_auth_part2.html)
- [Part 3: Token revocation](http://rundis.github.io/blog/2015/buddy_auth_part3.html)
- [Part 4: Secure and liberate a service app](http://rundis.github.io/blog/2015/buddy_auth_part4.html) 

The tags in the repo correspond to the code required for the 
implementation of Part 1 - 4.

## Set-up

### Prerequisites
You'll need [docker](https://docs.docker.com/install/) and 
[docker-compose](https://docs.docker.com/compose/install/) 
installed on your system.

### Clone this repo

    git clone git@github.com:bendbennett/clojure-api-swagger-jwt.git 
    
### Generate private and public keys    
You will be prompted for a password when generating the keys.

Update the `passphrase-for-key` entry in the `dev` section of 
`profiles.clj` with the passphrase that you supply when generating 
the keys.
 
    cd resources/keys
    openssl genrsa -aes256 -out private-key.pem 2048
    openssl rsa -pubout -in private-key.pem -out public-key.pem

### Build the docker image

    docker build -t clojure-api-swagger-jwt .

### Bring up the stack

    docker-compose up -d

## Calling the API
The following request should return a JWT.

    curl -X "POST" "http://localhost:3000/create-auth-token" \
         -H 'Content-Type: application/json' \
         -d $'{"username": "username", "password": "password"}' 

Response should look something like this.

    HTTP/1.1 201 Created
    Connection: close
    Date: Sun, 17 Feb 2019 12:47:41 GMT
    Content-Type: application/json;charset=utf-8
    Content-Length: 500
    Server: Jetty(9.4.12.v20180830)
    
    {"token":"header.payload.signature"}

## Running Tests

    docker exec -it clojure-api-swagger-jwt_api-repl_1 lein test

## Connecting to the REPL
REPL is started on port 7888.     

If you're using [IntelliJ IDEA](https://www.jetbrains.com/idea/) 
with the [Cursive](https://cursive-ide.com/userguide/repl.html) 
plugin use the following steps to configure a connection to the 
REPL.
* Run => Edit Configurations
* Add New Configuration => Clojure REPL => Remote
* Use Leiningen REPL Port
     
    
## Inspiration
A big thanks to 
[Magnus Rundberget](http://rundis.github.io/blog/about.html])
for a great set of [blog posts](#clojure-api-swagger-jwt).