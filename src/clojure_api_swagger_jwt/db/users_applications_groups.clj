(ns clojure-api-swagger-jwt.db.users-applications-groups
  (:require [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/users-applications-groups.sql")
