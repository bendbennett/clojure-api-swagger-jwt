{:dev  {:dependencies [[ring/ring-devel "1.6.3"]]
        :env          {:db-type            "postgresql"
                       :db-name            "clojure-api-swagger-jwt-dev"
                       :db-host            "postgresql"
                       :db-port            "5432"
                       :db-user            "postgres"
                       :db-password        ""
                       :passphrase-for-key "passphrase123"
                       :private-key        "resources/keys/private-key.pem"
                       :public-key         "resources/keys/public-key.pem"
                       :web-port           "3000"}
        :main         clojure-api-swagger-jwt.core/-dev-main}

 :test {:dependencies [[ring/ring-mock "0.3.2"]
                       [org.clojure/data.json "0.2.6"]]
        :env          {:db-name            "clojure-api-swagger-jwt-test"
                       :passphrase-for-key "passphrase123"
                       :private-key        "resources/keys/private-key.pem"
                       :public-key         "resources/keys/public-key.pem"}}

 :prod {:env  {:web-port "3000"}
        :main clojure-api-swagger-jwt.core/-main}}
