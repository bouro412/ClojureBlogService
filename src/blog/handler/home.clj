(ns blog.handler.home
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.util.response :as res]
            [blog.view.home :as view]
            [blog.util.validation :as uv]
            [blog.db.user :as user]
            [bouncer.validators :as v]))

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

(defn login-post [{:as req :keys [params]}]
  (uv/with-fallback #(login (assoc req :errors %))
    (let [params (uv/validate params {:mail [[v/required :message "メールアドレスを入力してください"]]
                                      :password [[v/required :message "パスワードを入力してください"]]})]
      ;; TODO: login失敗後の処理
      (if-let [user (user/login-user (:mail params) (:password params))]
        (let [cookie "cookie"] ;; TODO: cookieを生成する処理の実装
          ;; TODO: cookieをdbに登録する処理
          (-> (res/redirect "/") ;; TODO: login後に飛ぶページの作成
              (assoc-in [:cookie "session_id"] {:value cookie :secure true :maxage 3600})
              html))))))

(defroutes home-routes
  (GET "/" _ home)
  (GET "/login" _ login)
  (POST "/login" _ login-post)
  (GET "/register" _ register))
