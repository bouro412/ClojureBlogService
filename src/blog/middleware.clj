(ns blog.middleware
  (:require [environ.core :refer [env]]
            [ring.middleware.defaults :as defaults]
            [ring.middleware.session.cookie :as cookie]))

(def ^:private wrap #'defaults/wrap)

(defn- try-resolve [sym]
  (try
    (require (symbol (namespace sym)))
    (resolve sym)
    (catch java.io.FileNotFoundException _)
    (catch RuntimeException _)))

(defn wrap-dev [handler]
  {:pre [(or (fn? handler) (and (var? handler) (fn? (deref handler))))]}
  (let [wrap-exception (try-resolve 'prone.middleware/wrap-exceptions)
        wrap-reload (try-resolve 'ring.middleware.reload/wrap-reload)]
    (if (and wrap-reload wrap-exception)
      (-> handler
          wrap-exception
          wrap-reload)
      (throw (RuntimeException. "Middleware requires ring/ring-devel and prone;")))))

(defn middleware-set [handler]
  (let [config (-> defaults/site-defaults
                   (assoc-in [:session :cookie-attrs :max-age] 3600)
                   (assoc-in [:session :store] (cookie/cookie-store "asduygkwpedyi3")))]
    (-> handler
        (wrap wrap-dev (:dev env))
        (defaults/wrap-defaults config))))
