(ns blog.db.user
  (:require [clojure.java.jdbc :as jdbc]
            [blog.db :as db]))

(def table :users)

(defn register-user [user-id name mail password]
  (jdbc/insert! db/db-spec table {:name name :mail mail :password password :user_id user-id}))

(defn get-users [& {:as columns}]
  (db/query table columns))

(defn search-user [mail pass]
  ;; TODO: passwordの復号処理
  (let [users (get-users :mail mail :password pass)]
    (if (= (count users) 1)
      (first users))))

(defn id->uid [user-id]
  (:uid (first (get-users :user_id user-id))))
  
    
