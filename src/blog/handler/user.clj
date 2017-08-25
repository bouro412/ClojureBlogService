(ns blog.handler.user
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [blog.view.user :as view]
            [blog.handler.util :refer [html]]
            [blog.db.user :refer [get-users id->uid]]
            [blog.db.article :refer [get-articles article-author]]
            [blog.util.login :refer [login-user]]
            [ring.util.response :as res]
            [blog.util.validation :as uv]))

(defn user-home [{:as req :keys [params]} user-id]
  (if-let [author (first (get-users :user_id user-id))]
    (-> (view/user-home-view req author)
        (res/response)
        html)
    (res/not-found (format "user %s is not found."
                           (:user-id params)))))

(defn article-show [{:as req :keys [params]} user-id article-uid]
  (if-let [article (first (get-articles :owner_id (id->uid user-id)
                                        :uid article-uid))]
    (-> (view/article-view req article (:user-id params))
        (res/response)
        html)
    (res/not-found "article is not found")))

(defn comment-show [req user-id article-uid]
  (-> (view/comment-list-view req user-id article-uid)
      (res/response)
      html))

(defn comment-post [req user-id article-uid]
  )

(defroutes user-routes
  (context "/user/:user-id" [user-id]
           (GET "/" [:as req] (user-home req user-id))
           (GET ["/:article-id" :article-id #"[0-9]+"]
                [article-id :<< as-int :as req]
                (article-show req user-id article-id))
           (GET "/:article-id{[0-9]+}/comment"
                [article-id :<< as-int :as req]
                (comment-show req user-id article-id))
           (POST "/:article-id{[0-9]+}/comment"
                 [article-id :<< as-int :as req]
                 (comment-post req user-id article-id))))
