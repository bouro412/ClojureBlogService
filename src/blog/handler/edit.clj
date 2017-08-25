(ns blog.handler.edit
  (:require [compojure.core :refer [defroutes context GET POST]]
            [compojure.route :as route]
            [compojure.coercions :refer [as-int]]
            [blog.view.edit :as view]
            [blog.handler.util :refer [html]]
            [blog.db.user :refer [get-users id->uid]]
            [blog.db.article :refer [get-articles article-author update-article]]
            [blog.util.login :refer [login-user login?]]
            [ring.util.response :as res]))


(defn edit [{:as req :keys [params]} user-id article-uid]
  (let [author-id (:user_id (article-author :uid article-uid))
        logined-user-id (:user_id (login-user req))]
    (if (= user-id logined-user-id author-id)
      (-> (view/edit-view req article-uid user-id)
          (res/response)
          html)
      (res/not-found "article is not found"))))

(defn edit-post [{:as req :keys [params]} user-id article-uid]
  (update-article article-uid (:title params) (:article params))
  (res/redirect (str "/user/" user-id "/" article-uid)))

(defn edit-new [req user-id]
  (if (login? req :user_id user-id)
    (-> (view/edit-new-view req user-id)
        (res/response)
        html)
    (res/not-found "page not found")))

(defroutes edit-routes
  (GET "/edit/:user-id" [user-id :as req] (edit-new req user-id))
  (GET "/edit/:user-id/:article-id{[0-9]+}"
       [user-id article-id :<< as-int :as req]
       (edit req user-id article-id))
  (POST "/edit/:user-id/:article-id{[0-9]+}"
        [user-id article-id :<< as-int :as req]
        (edit-post req user-id article-id)))
