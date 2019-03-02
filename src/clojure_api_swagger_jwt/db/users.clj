(ns clojure-api-swagger-jwt.db.users
  (:require [buddy.hashers :as hashers]
            [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/users.sql")

(defn create-user [username password]
  (insert-user {:id       (java.util.UUID/randomUUID)
                :username username
                :password (hashers/derive password)}))
