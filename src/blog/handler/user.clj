(ns blog.handler.user
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [blog.view.user :as view]
            [blog.handler.util :refer [html]]
            [blog.db.user :refer [get-users id->uid]]
            [blog.db.article :refer [get-articles]]
            [ring.util.response :as res]))

(defn user-home [{:as req :keys [params]}]
  (if-let [author (first (get-users :user_id (:user-id params)))]
    (-> (view/user-home-view req author)
        (res/response)
        html)
    (res/not-found (format "user %s is not found."
                           (:user-id params)))))

(defn article-show [{:as req :keys [params]}]
  (if-let [article (first (get-articles :owner_id (id->uid (:user-id params))
                                        :uid (Integer/parseInt(:article-id params))))]
    (-> (view/article-view req article (:user-id params))
        (res/response)
        html)
    (res/not-found "article is not found")))

(defroutes user-routes
  (context "/user/:user-id" _
           (GET "/" _ user-home)
           (GET "/:article-id" _ article-show)
           ;(GET "/edit" _ edit)
           ))

