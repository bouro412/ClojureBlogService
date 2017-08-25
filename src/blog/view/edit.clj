(ns blog.view.edit
  (:require [blog.view.layout :as layout]
            [blog.view.util :refer [error-messages]]
            [hiccup.form :as hf]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [blog.util.login :refer [login-user]]
            [blog.db.user :as user]
            [blog.db.article :refer [get-articles]]))

(defn edit-view [{:as req :keys [params]} article-uid user-id]
  (let [article (first (get-articles :uid article-uid))]
    (->> [:section.card
          [:h2 "記事編集"]
          (hf/form-to
           [:post (format "/edit/%s/%s" user-id article-uid)]
           (anti-forgery-field)
           (error-messages req)
           [:input {:type :text :name :title :value (:title article) :size 60}]
           "<br>"
           [:textarea {:name :article :rows 10 :cols 60}
            (:article article)]
           "<br>"
           [:button.bg-blue "更新する"])]
         (layout/common req))))

(defn edit-new-view [req user-id])
