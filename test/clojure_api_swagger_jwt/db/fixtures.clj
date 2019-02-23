(ns clojure-api-swagger-jwt.db.fixtures
  (:require [clojure.test :refer :all]
            [clojure-api-swagger-jwt.db :as db]))

(def ^:const user-attributes
     {:username "username"
      :password "password"})

(defn insert-user []
  (db/create-user (:username user-attributes)
                  (:password user-attributes)))

(def ^:const application-attributes
     {:name "application-name"})

(defn insert-application []
  (db/create-application (:name application-attributes)))

(def ^:const group-attributes
     {:name "group-name"})

(defn insert-group []
  (db/create-group (:name group-attributes)))

; want to associate user with application and group
;(defn insert-application []
;  (db/create-application (:name application-attributes)))


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
