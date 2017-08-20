(ns blog.handler.uesr
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [blog.view.home :as view]
            [blog.handler.util :refer [html]]
            [ring.util.response :as res]))

#_(defn user-home [{:as req :keys [params]}]
  (-> (view/user-home-view)
      (res/response)
      html))

#_(defroutes user-routes
  (context "/user/:user-id" _
           (GET "/" _ user-home)
           (GET "/:article-id" _ article-show)
           (GET "/edit" _ edit)))

