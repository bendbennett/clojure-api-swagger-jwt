(ns clojure-api-swagger-jwt.db.applications
  (:require [clojure-api-swagger-jwt.db :as db]))

(db/def-db-fns "sql/applications.sql")

(defn create-application [name]
  (insert-application {:id   (java.util.UUID/randomUUID)
                       :name name}))

;should be able to use threading macro here
(defn create-applications [applications]
  (insert-applications {:applications (vec (for [application applications]
                                             [(java.util.UUID/randomUUID)
                                              (:name application)]))})



  ;[
  ; [(java.util.UUID/randomUUID) "stuff 4"]
  ; [(java.util.UUID/randomUUID) "stuff 5"]
  ; [(java.util.UUID/randomUUID) "stuff 6"]
  ; ]
  ;(insert-applications {:applications applications})

  )

