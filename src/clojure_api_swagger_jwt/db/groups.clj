(ns clojure-api-swagger-jwt.db.groups
  (:require [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/groups.sql")

(defn create-group [name]
  (insert-group {:id   (java.util.UUID/randomUUID)
                 :name name}))
