(ns blog.view.user
  (:require [blog.view.layout :as layout]
            [blog.view.util :refer [error-messages]]
            [hiccup.form :as hf]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [blog.util.login :as login]
            [blog.db.user :as user]
            [blog.db.article :as article]))

(defn user-home-view [{:as req :keys [params]} author]
  (->> [:section.card
        [:h2 (str (:name author) "の日記")]
        ]
       (layout/common req)))


