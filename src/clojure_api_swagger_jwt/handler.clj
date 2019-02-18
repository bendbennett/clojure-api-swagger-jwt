(ns clojure-api-swagger-jwt.handler
  (:require [buddy.auth :refer [authenticated?]]
            [buddy.auth.backends :as backends]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [clojure-api-swagger-jwt.auth :as auth]
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

(defn home
  [request]
  (if-not (authenticated? request)
    {:status 401}
    {:status 201 :body (:identity request)}))

(defroutes api-routes
  (GET "/" [] home)
  (POST "/create-auth-token" [] create-auth-token)
  (route/not-found ""))

(def auth-backend
     (backends/jws {:secret  auth/public-key
                    :options {:alg :rs256}}))

(def api
     (as-> (handler/api api-routes) $
           (wrap-authentication $ auth-backend)
           (json/wrap-json-body $)
           (json/wrap-json-response $)))

(defn start-server []
  (jetty/run-jetty api {:port (read-string (env :web-port))}))

(defn start-server-dev []
  (jetty/run-jetty (wrap-reload #'api) {:port (read-string (env :web-port))}))
