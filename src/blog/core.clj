(ns blog.core
  (:require [compojure.core :refer [routes]]
            [ring.adapter.jetty :as server]
            [blog.handler.home :as home]
            [blog.handler.user :as user]
            [blog.middleware :refer [middleware-set wrap-dev]]))

(defonce server (atom nil))

(def app
  (-> (routes
       home/home-routes
       user/user-routes
       user/edit-routes)
      wrap-dev
      middleware-set
      ))

(defn start-server [& {:keys [host port join?]
                       :or {host "localhost" port 3000 join? false}}]
  (let [port (if (string? port) (Integer/parseInt port) port)]
    (when-not @server
      (reset! server (server/run-jetty #'app {:host host :port port :join? join?})))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))

(defn -main [& args]
  (start-server))
