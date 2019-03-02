(ns clojure-api-swagger-jwt.db.fixtures
  (:require [clojure.test :refer :all]
            [clojure-api-swagger-jwt.db :as db]
            [clojure-api-swagger-jwt.db.applications :as applications]
            [clojure-api-swagger-jwt.db.groups :as groups]
            [clojure-api-swagger-jwt.db.users :as users]
            [clojure-api-swagger-jwt.db.users-applications-groups :as u-a-g]))

(def ^:const user-attributes
     {:username "username"
      :password "password"})

(defn insert-user []
  (users/create-user (:username user-attributes)
                     (:password user-attributes)))

(def ^:const application-attributes
     {:name "application-name"})

(defn insert-application []
  (applications/create-application (:name application-attributes)))

(def ^:const group-attributes
     {:name "group-name"})

(defn insert-group []
  (groups/create-group (:name group-attributes)))

(defn insert-user-application-group []
  (u-a-g/associate-user-application-group (:id (users/find-user-by-username (:username user-attributes)))
                                          (:id (applications/find-application-by-name (:name application-attributes)))
                                          (:id (groups/find-group-by-name (:name group-attributes)))))

(defn migrate-rollback [f]
  (db/migrate)
  (f)
  (db/rollback-all))

(defn clean-tables [f]
  (users/delete-all-users)
  (f))

(defn setup []
  (use-fixtures :once migrate-rollback)
  (use-fixtures :each clean-tables))
