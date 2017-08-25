(ns blog.db.article
  (:require [clojure.java.jdbc :as jdbc]
            [blog.db :as db]
            [blog.db.user :as user]
            [clj-time.local :refer [local-now]]
            [clj-time.coerce :refer [to-sql-time]]))

(def table :articles)

(defn time-stamp []
  (to-sql-time (local-now)))

(defn add-article [owner-id title article]
  (jdbc/insert! db/db-spec table
                {:owner_id owner-id
                 :title title
                 :article article
                 :date (time-stamp)}))

(defn update-article [uid title article]
  (jdbc/update! db/db-spec
                table
                {:title title
                 :article article
                 :date (time-stamp)}
                ["uid = ?" uid]))

(defn delete-article [uid]
  (jdbc/delete! db/db-spec
                table
                ["uid = ?" uid]))

(defn get-articles [& {:as column+vals}]
  (db/query table column+vals))

(defn article-author [& {:as column+vals}]
  (first
   (user/get-users :uid
                   (-> (db/query table column+vals)
                       first
                       :owner_id))))
