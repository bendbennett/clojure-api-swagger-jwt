(ns clojure-api-swagger-jwt.handler
  (:require [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.auth.accessrules :refer [restrict success error]]
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
  {:status 201 :body (:identity request)})

(defn on-error
  [request value]
  {:status  403})

(defn authenticated-user
  [request]
  (if (:identity request)
    true
    (error "Only authenticated users allowed")))

(defroutes api-routes
  ;(GET "/" [] home)
  (GET "/" [] (restrict home {:handler  authenticated-user
                              :on-error on-error}))
  (POST "/create-auth-token" [] create-auth-token)
  (route/not-found ""))

(def authentication-backend
     (backends/jws {:secret  auth/public-key
                    :options {:alg :rs256}}))

(def api
     (as-> (handler/api api-routes) $
           (wrap-authentication $ authentication-backend)
           (json/wrap-json-body $)
           (json/wrap-json-response $)))

(defn start-server []
  (jetty/run-jetty api {:port (read-string (env :web-port))}))

(defn start-server-dev []
  (jetty/run-jetty (wrap-reload #'api) {:port (read-string (env :web-port))}))
