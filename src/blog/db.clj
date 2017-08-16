(ns blog.db
  (:require [environ.core :refer [env]]))

(def db-spec (:db env))
