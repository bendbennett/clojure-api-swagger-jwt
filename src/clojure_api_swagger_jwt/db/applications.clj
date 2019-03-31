(ns clojure-api-swagger-jwt.db.applications
  (:require [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/applications.sql")

(defn create-application [name]
  (insert-application {:id   (java.util.UUID/randomUUID)
                       :name name}))

; comment to make clear what is being passed to HUGSQL i.e., [[uuid name] [uuid name]]
; is there a different form for using map instead of for
(defn create-applications [applications]
  (as-> applications a
        (for [application a]
          [(java.util.UUID/randomUUID)
           (:name application)])
        (vec a)
        (insert-applications {:applications a})))

