(ns clojure-api-swagger-jwt.handler
  (:require [clojure-api-swagger-jwt.auth :as auth]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.json :as json]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn create-auth-token [request]
  (let [[ok? result] (auth/create-auth-token {:username (get-in request [:body "username"])
                                              :password (get-in request [:body "password"])})]
    (if ok?
      {:status 201 :body result}
      {:status 401 :body result})))

(defroutes api-routes
  (POST "/create-auth-token" [] create-auth-token)
  (route/not-found ""))

(def api
     (-> (handler/api api-routes)
         (json/wrap-json-body)
         (json/wrap-json-response)))

(defn start-server []
  (jetty/run-jetty api {:port (read-string (env :web-port))}))

(defn start-server-dev []
  (jetty/run-jetty (wrap-reload #'api) {:port (read-string (env :web-port))}))
