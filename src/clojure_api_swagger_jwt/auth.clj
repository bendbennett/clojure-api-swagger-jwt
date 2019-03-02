(ns clojure-api-swagger-jwt.auth
  (:require [buddy.core.keys :as keys]
            [buddy.hashers :as hashers]
            [buddy.sign.jwt :as jwt]
            [clj-time.core :as time]
            [clojure-api-swagger-jwt.db.users :as users]
            [environ.core :refer [env]]))

(defn auth-user [credentials]
  (let [user (users/find-user-by-username {:username (:username credentials)})]
    (if user
      (if (hashers/check (:password credentials) (:password user))
        [true {:user (dissoc user :password)}]
        [false])))
  )

(def private-key
     (keys/private-key (env :private-key) (env :passphrase-for-key)))

(def public-key
     (keys/public-key (env :public-key)))

(defn create-auth-token [credentials]
  (let [[ok? result]
        (auth-user credentials)
        exp (-> (time/plus (time/now) (time/days 1)))]
    (if ok?
      [true {:token (jwt/sign result private-key {:alg :rs256 :exp exp})}]
      [false result])))

(defn unsigned-data [signed-data]
  (println (jwt/unsign signed-data public-key {:alg :rs256}))
  (jwt/unsign signed-data public-key {:alg :rs256}))
