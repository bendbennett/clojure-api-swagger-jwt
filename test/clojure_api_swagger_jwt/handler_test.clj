(ns clojure-api-swagger-jwt.handler-test
  (:require [clojure.data.json :as json]
            [clojure.test :refer :all]
            [clojure-api-swagger-jwt.db.fixtures :as fixtures]
            [clojure-api-swagger-jwt.handler :refer :all]
            [ring.mock.request :as mock]))

(fixtures/setup)

(deftest test-routes
  (fixtures/insert-user)
  (testing "create-auth-token route"
    (let [attrs fixtures/user-attributes
          response (api (-> (mock/request :post "/create-auth-token")
                            (mock/json-body {:username (:username attrs)
                                             :password (:password attrs)}))
                        )
          json-body (json/read-str (:body response)
                                   :key-fn keyword)]
      (is (= 201 (:status response)))
      (is (= "application/json; charset=utf-8" (get-in response [:headers "Content-Type"])))
      (is (contains? json-body :token))
      (is (re-matches #"^[A-Za-z0-9-_=]+\.[A-Za-z0-9-_=]+\.[A-Za-z0-9-_.+/=]+$"
                      (get-in json-body [:token])))))
  (testing "not-found route"
    (let [response (api (mock/request :get "/invalid"))]
      (is (= 404 (:status response))))))
