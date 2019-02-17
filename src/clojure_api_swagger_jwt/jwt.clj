(ns clojure-api-swagger-jwt.jwt
  (:require [buddy.core.keys :as keys]
            [buddy.sign.jwt :as jwt]
            [clj-time.core :as time]
            [clojure-api-swagger-jwt.db :as db]
            [environ.core :refer [env]]))

(def private-key
     (keys/private-key (env :private-key) (env :passphrase-for-key)))

(def public-key
     (keys/public-key (env :public-key)))

(defn create-auth-token [credentials]
  (let [[ok? res]
        (db/auth-user credentials)
        exp (-> (time/plus (time/now) (time/days 1)))]
    (if ok?
      [true {:token (jwt/sign res private-key {:alg :rs256 :exp exp})}]
      [false res])))

(defn unsigned-data [signed-data]
  (println (jwt/unsign signed-data public-key {:alg :rs256}))
  (jwt/unsign signed-data public-key {:alg :rs256}))
