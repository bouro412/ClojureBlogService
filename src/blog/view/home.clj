(ns blog.view.home
  (:require [blog.view.layout :as layout]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.form :as hf]
            [blog.view.util :refer [error-messages]]))

(defn home-view [req]
  (->> [:section.card
        [:h2 "ホーム画面"]
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
         [:input {:name :address :value ""
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
         [:input {:name :name :value ""
                  :placeholder "ニックネーム"}]
         "<br>"
         [:input {:name :address :value ""
                  :placeholder "メールアドレス"}]
         "<br>"
         [:input {:name :password :value ""
                  :placeholder "パスワード"
                  :type :password}]
         "<br>"
         [:button.bg-blue "登録"])]
       (layout/common req)))


