(ns clojure-api-swagger-jwt.db.fixtures
  (:require [clojure.test :refer :all]
            [clojure-api-swagger-jwt.db :as db]))

(def ^:const user-attributes
     {:username "username"
      :password "password"})

(defn insert-user []
  (db/create-user (:username user-attributes)
                  (:password user-attributes)))

(defn migrate-rollback [f]
  (db/migrate)
  (f)
  (db/rollback-all))

(defn clean-tables [f]
  (db/delete-all-users)
  (f))

(defn setup []
  (use-fixtures :once migrate-rollback)
  (use-fixtures :each clean-tables))
