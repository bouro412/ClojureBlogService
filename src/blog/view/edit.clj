(ns blog.view.edit
  (:require [blog.view.layout :as layout]
            [blog.view.util :refer [error-messages]]
            [hiccup.form :as hf]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [blog.util.login :refer [login-user]]
            [blog.db.user :as user]
            [blog.db.article :as article]))

(defn edit-view [{:as req :keys [params]} article-uid user-id]
  (->> [:section.card
        [:h2 "記事編集画面"]]
       (layout/common req)))

(defn edit-new-view [req user-id])
