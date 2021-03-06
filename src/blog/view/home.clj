(ns blog.view.home
  (:require [blog.view.layout :as layout]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.form :as hf]
            [blog.view.util :refer [error-messages]]))

(defn home-view [{:as req :keys [session]}]
  (->> [:section.card
        [:h2 "ホーム画面"]
        (if-let [user (:login-user session)]
          [:h3 (str "こんにちは" (:name user))])
        [:a {:href "/login"} "ログインする"]
        "<br>"
        [:a {:href "/register"} "登録する"]]
       (layout/common req)))

(defn login-view [req]
  (->> [:section.card
        [:h2 "ログイン画面"]
        (hf/form-to
         [:post "/login"]
         (anti-forgery-field)
         (error-messages req)
         [:input {:name :mail :value ""
                  :placeholder "メールアドレス"}]
         "<br>"
         [:input {:name :password :value ""
                  :placeholder "パスワード"
                  :type :password}]
         "<br>"
         [:button.bg-blue "ログイン"])]
       (layout/common req)))

(defn register-view [req]
  (->> [:section.card
        [:h2 "ユーザー登録"]
        (hf/form-to
         [:post "/register"]
         (anti-forgery-field)
         (error-messages req)
         [:input {:name :user-id :value ""
                  :placeholder "ユーザーID"}]
         "<br>"
         [:input {:name :name :value ""
                  :placeholder "ニックネーム"}]
         "<br>"
         [:input {:name :mail :value ""
                  :placeholder "メールアドレス"}]
         "<br>"
         [:input {:name :password :value ""
                  :placeholder "パスワード"
                  :type :password}]
         "<br>"
         [:button.bg-blue "登録"])]
       (layout/common req)))


