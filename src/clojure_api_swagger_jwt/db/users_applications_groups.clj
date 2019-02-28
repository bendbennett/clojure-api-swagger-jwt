(ns clojure-api-swagger-jwt.db.users-applications-groups
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "sql/users-applications-groups.sql")
