(ns blog.db.user
  (:require [clojure.java.jdbc :as jdbc]
            [blog.db :as db]))

(defn register-user [name mail password]
  (jdbc/insert! db/db-spec :users {:name name :mail mail :password password}))

(defn login-user [mail pass]
  (let [user (first (jdbc/query db/db-spec ["select * from users where mail = ?" mail]))]
    ;; TODO: passwordの復号処理
    (if (= (:password user) pass)
      user)))


