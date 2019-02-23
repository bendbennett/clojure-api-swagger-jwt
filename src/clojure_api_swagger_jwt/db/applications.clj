(ns clojure-api-swagger-jwt.db.applications
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "sql/applications.sql")
