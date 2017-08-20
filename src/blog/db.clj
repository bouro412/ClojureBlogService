(ns blog.db
  (:require [environ.core :refer [env]]
            [clojure.java.jdbc :as jdbc]))

(def db-spec (:db env))

(defn make-query [table query-map]
  (vec (cons
        (str "select * from "
             (name table)
             " where "
             (->> (map key query-map)
                  (map #(str (name %) " = ?"))
                  (reduce #(str %1 " and " %2))))
        (map val query-map))))

(defn query [table column+vals]
  (jdbc/query db-spec (make-query table column+vals)))