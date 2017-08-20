(ns blog.db.article
  (:require [clojure.java.jdbc :as jdbc]
            [blog.db :as db]))

(def table :articles)

(defn add-article [owner-id title article]
  (jdbc/insert! db/db-spec table
                {:owner_id owner-id
                 :title title
                 :article article
                 :date 1}));; TODO: 現在時刻の取得

(defn get-articles [& {:as column+vals}]
  (db/query table column+vals))


