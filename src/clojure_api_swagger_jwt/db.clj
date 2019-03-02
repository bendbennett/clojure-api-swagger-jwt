(ns clojure-api-swagger-jwt.db
  (:require [environ.core :refer [env]]
            [hugsql.core :as hugsql]
            [ragtime.jdbc :as ragtime]
            [ragtime.repl :as rg-repl]))

(def db-config
     "Database configuration"
  {:dbtype   (env :db-type)
   :dbname   (env :db-name)
   :host     (env :db-host)
   :port     (env :db-port)
   :user     (env :db-user)
   :password (env :db-password)})

(defn- ragtime-config []
  {:datastore  (ragtime/sql-database db-config)
   :migrations (ragtime/load-resources "migrations")})

(defn migrate []
  "Runs all migrations."
  (rg-repl/migrate (ragtime-config)))

(defn rollback-all []
  "Rollback all migrations."
  (rg-repl/rollback (ragtime-config) (Integer/MAX_VALUE)))

(defn def-db-fns [file]
  (hugsql/def-sqlvec-fns file)

  (doseq [[k v] (hugsql/map-of-db-fns file)]
    (let [fn-name (name k)]
      (intern *ns*
              (symbol fn-name)
              (fn [& args] (apply (:fn v) db-config args))))))

(defn run-query [query params]
  (apply (resolve query) [db-config params]))
