(ns blog.db.comment
  (:require [clojure.java.jdbc :as jdbc]
            [blog.db :as db]
            [blog.db.user :as user]
            [clj-time.local :refer [local-now]]
            [clj-time.coerce :refer [to-sql-time]]))

(def table :comments)

(defn time-stamp []
  (to-sql-time (local-now)))

(defn get-id [article-id]
  (inc (count (db/query table {:article_id article-id}))))

(defn add-comment [article-id title name content]
  (jdbc/insert! db/db-spec table
                {:article_id article-id
                 :id (get-id article-id)
                 :title title
                 :name name
                 :content content
                 :date (time-stamp)}))

(defn get-comments [article-id]
  (db/query table {:article_id article-id}))


