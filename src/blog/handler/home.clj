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

(def user-validate-map
  {:name [[v/required :message "名前を入力してください"]]
   :user-id [[v/required :message "IDを入力してください"]]
   :mail [[v/required :message "メールアドレスを入力してください"]]
   :password [[v/required :message "パスワードを入力してください"]]})

(defn register-post [{:as req :keys [params]}]
  (uv/with-fallback #(register (assoc req :errors %))
    (let [{:keys [name user-id mail password]}
          (uv/validate params  user-validate-map)]
      (user/register-user user-id name mail password)
      (res/redirect "/"))))

(defn login-post [{:as req :keys [params]}]
  (uv/with-fallback #(login (assoc req :errors %))
    (let [{:keys [mail password]}
          (uv/validate params (dissoc user-validate-map :name :user-id))]
      (if-let [user (user/search-user mail password)]
        (-> (res/redirect "/")
            (login/update-user user)
            html)
        (login (assoc req :errors {:msg ["メールアドレスとパスワードの組み合わせが間違っています。"]}))))))

(defn logout [{:as req :keys [params]}]
  (-> (res/redirect "/")
      (assoc :session nil)))

(defroutes home-routes
  (GET "/" _ home)
  (GET "/login" _ login)
  (POST "/login" _ login-post)
  (GET "/register" _ register)
  (POST "/register" _ register-post)
  (GET "/logout" _ logout))
