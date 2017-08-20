(ns blog.handler.home
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.util.response :as res]
            [blog.view.home :as view]
            [blog.util.validation :as uv]
            [blog.handler.util :refer [html]]
            [blog.db.user :as user]
            [blog.util.login :refer [login? login-user]]
            [bouncer.validators :as v]
            [blog.util.login :as login]))

(defn home [req]
  (if (login? req)
    (-> (res/redirect (str "/user/" (:user_id (login-user req))))
        html)
    (-> (view/home-view req)
        res/response
        html)))

(defn login [req]
  (-> (view/login-view req)
      res/response
      html))

(defn register [req]
  (-> (view/register-view req)
      res/response
      html))

(defn login-post [{:as req :keys [params]}]
  (uv/with-fallback #(login (assoc req :errors %))
    (let [params (uv/validate params {:mail [[v/required :message "メールアドレスを入力してください"]]
                                      :password [[v/required :message "パスワードを入力してください"]]})]
      (if-let [user (user/search-user (:mail params) (:password params))]
        (-> (res/redirect "/") ;; TODO: login後に飛ぶページの作成
            (login/update-user user)
            html)
        (login (assoc req :errors {:msg ["メールアドレスとパスワードの組み合わせが間違っています。"]}))))))

(defroutes home-routes
  (GET "/" _ home)
  (GET "/login" _ login)
  (POST "/login" _ login-post)
  (GET "/register" _ register))
