(defproject clojure-api-swagger-jwt "0.1.0-SNAPSHOT"
  :description "RDBMS-backed, JWT-authorized/authenticated API written in Clojure with Swagger docs."
  :url "https://github.com/bendbennett/clojure-api-swagger-jwt"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [buddy/buddy-auth "2.1.0"]
                 [buddy/buddy-core "1.5.0"]
                 [buddy/buddy-hashers "1.3.0"]
                 [buddy/buddy-sign "3.0.0"]
                 [clj-time "0.15.0"]
                 [com.layerware/hugsql "0.4.9"]
                 [compojure "1.6.1"]
                 [environ "1.1.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [ragtime "0.8.0"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-json "0.4.0"]]
  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.12.4"]]
  :ring {:handler clojure-api-swagger-jwt.handler/api})

