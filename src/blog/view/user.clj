(ns blog.view.user
  (:require [blog.view.layout :as layout]
            [blog.view.util :refer [error-messages]]
            [hiccup.form :as hf]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [blog.util.login :refer [login-user login?]]
            [blog.db.user :as user]
            [blog.db.article :as article]
            [blog.db.comment :as comment]))

(defn article-select-view [req author article]
  [:li
   [:a {:href (format "/user/%s/%s"
                      (:user_id author)
                      (:uid article))}
    (:title article)]
   (let [user (login-user req)]
     (when (= (:uid author) (:uid user))
       (list [:a.wide-link {:href (format "/edit/%s/%s"
                                (:user_id author)
                                (:uid article))}
              "編集する"]
             [:a.wide-link {:href (format "/edit/delete/%s/%s"
                                          (:user_id author)
                                          (:uid article))}
              "削除する"])))])

(defn user-home-view [{:as req :keys [params]} author]
  (->> [:section.card
        [:h2 (str (:name author) "の日記")]
        [:ul
         (->> (article/get-articles :owner_id (:uid author))
              (sort-by :uid >)
              (take 20) ;; TODO: 取得件数の管理
              (map #(article-select-view req author %)))]        
        (when (login? req :uid (:uid author))
          [:a.wide-link {:href (str "/edit/" (:user_id author))} "新規記事の作成"])]
       (layout/common req)))

(defn article-view [req article author-id]
  (->> [:section.card
        [:h2 (:title article)]
        [:div (:article article)]
        (when (= (:user_id (login-user req)) author-id)
          [:div
           "<br>"
           [:a.wide-link {:href (format "/edit/%s/%s"
                                        author-id
                                        (:uid article))}
            "編集する"]
           
           [:a.wide-link {:href (format "/edit/delete/%s/%s"
                              author-id
                              (:uid article))}
            "削除する"]])
        [:a {:href (str (:uid article) "/comment")}
         "コメントを見る"]
        "<br>"
        [:a {:href (str "/user/" author-id)}
         "戻る"]]
       (layout/common req)))

(defn comment-view [{:as comment :keys [id title name content]}]
  [:div id ": " title " " name "<br>" content])

(defn comment-list-view [req user-id article-id]
  (->> [:section.card
        [:h2 "コメント:"]
        [:ul
         (->> (comment/get-comments article-id)
              (sort-by :id)
              (map comment-view))]]
       (layout/common req)))
