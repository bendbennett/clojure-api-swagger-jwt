(ns clojure-api-swagger-jwt.core
  (:gen-class)
  (:require [clojure-api-swagger-jwt.db :as db]
            [clojure-api-swagger-jwt.db.fixtures :as fixtures]
            [clojure-api-swagger-jwt.handler :as handler]))

(defn -main [& args]
  (let [task (first args)]
    (case task
      "migrate" (db/migrate)
      "web" (handler/start-server)
      (println "unknown task:" task)
      )
    ))

(defn -dev-main []
  (db/rollback-all)
  (db/migrate)
  ;(fixtures/insert-user)
  (fixtures/insert-applications)
  ;(fixtures/insert-group)
  ;(fixtures/insert-user-application-group)
  (handler/start-server-dev))
