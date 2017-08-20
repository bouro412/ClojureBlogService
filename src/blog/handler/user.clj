(ns blog.handler.user
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [blog.view.user :as view]
            [blog.handler.util :refer [html]]
            [ring.util.response :as res]))

(defn user-home [{:as req :keys [params]}]
  (-> (view/user-home-view req)
      (res/response)
      html))

(defroutes user-routes
  (context "/user/:user-id" _
           (GET "/" _ user-home)
           ;(GET "/:article-id" _ article-show)
           ;(GET "/edit" _ edit)
           ))

