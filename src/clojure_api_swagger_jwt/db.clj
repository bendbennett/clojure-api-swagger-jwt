(ns clojure-api-swagger-jwt.db
  (:require [buddy.hashers :as hashers]
            [clojure-api-swagger-jwt.db.users :as users]
            [environ.core :refer [env]]
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
  "Rolls back all datastore migrations."
  (rg-repl/rollback (ragtime-config) (Integer/MAX_VALUE)))

(defn auth-user [credentials]
  (let [user (users/find-by-username db-config {:username (:username credentials)})]
    (if user
      (if (hashers/check (:password credentials) (:password user))
        [true {:user (dissoc user :password)}]
        [false]))))

(defn create-user [username password]
  (users/insert-user db-config {:id       (java.util.UUID/randomUUID)
                                :username username
                                :password (hashers/derive password)}))

(defn delete-all-users []
  (users/delete-all-users db-config))
