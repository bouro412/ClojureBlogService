(ns blog.handler.home
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.util.response :as res]
            [blog.view.home :as view]))

(defn html [res]
  (res/content-type res "text/html; charset=utf-8"))

(defn home [req]
  (-> (view/home-view req)
      res/response
      html))

(defn login [req]
  (-> (view/login-view req)
      res/response
      html))

(defn register [req]
  (-> (view/register-view req)
      res/response
      html))

(defroutes home-routes
  (GET "/" _ home)
  (GET "/login" _ login)
  (GET "/register" _ register))
