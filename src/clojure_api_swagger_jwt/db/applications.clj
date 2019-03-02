(ns clojure-api-swagger-jwt.db.applications
  (:require [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/applications.sql")

(defn create-application [name]
  (insert-application {:id   (java.util.UUID/randomUUID)
                       :name name}))
