(ns blog.main
  (:require [blog.core :as core])
  (:gen-class))

(defn -main [& {:as args}]
  (core/start-server
   :host (get args "host") :port (get args "port")
   :join? true))
